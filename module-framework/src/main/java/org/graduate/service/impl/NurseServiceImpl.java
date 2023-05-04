package org.graduate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Nurse;
import org.graduate.domain.entity.User;
import org.graduate.domain.vo.NurseVo;
import org.graduate.mapper.NurseMapper;
import org.graduate.mapper.UserMapper;
import org.graduate.service.NurseService;
import org.graduate.utils.BeanCopyUtil;
import org.graduate.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * (Nurse)
 *
 * @author Zhanghao
 * @since 2023-04-24 21:31:15
 */
@Service
public class NurseServiceImpl extends ServiceImpl<NurseMapper, Nurse> implements NurseService {

    @Autowired
    private NurseMapper nurseMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 查找
     *
     * @param model
     * @return
     */
    @Override
    public ResponseResult getNurseList(SearchModel model) {
        //获取查找条件
        long currentPage = model.getCurrentPage();
        long pageSize = model.getPageSize();
        String name = model.getName();

        //封装Page和QueryMapper
        IPage<Nurse> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Nurse> queryWrapper = new LambdaQueryWrapper<>();
        if (name != "" && Objects.nonNull(name)) {
            queryWrapper.like(Nurse::getName, name);
        }
        queryWrapper.orderByAsc(Nurse::getId);

        //查找
        nurseMapper.selectPage(page, queryWrapper);

        return ResponseResult.ok(page);
    }

    /**
     * 添加
     *
     * @param nurse
     * @return
     */
    @Override
    public ResponseResult addNurse(Nurse nurse) {
        nurseMapper.insert(nurse);
        User user = new User();
        user.setUsername(nurse.getId().toString());
        user.setNickName(nurse.getName());
        user.setPhoneNumber(nurse.getPhoneNumber());
        user.setRoleId(4L);
        userMapper.insert(user);
        return ResponseResult.ok().setMessage("添加成功");
    }

    /**
     * 更新
     *
     * @param nurse
     * @return
     */
    @Override
    public ResponseResult editNurse(Nurse nurse) {
        nurseMapper.updateById(nurse);
        return ResponseResult.ok().setMessage("更新成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteNurse(Long id) {
        nurseMapper.deleteById(id);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, id.toString());
        userMapper.delete(queryWrapper);
        return ResponseResult.ok().setMessage("删除成功");
    }

    /**
     * 获取所有护工
     *
     * @return
     */
    @Override
    public ResponseResult getAllNurses() throws Exception {
        List<Nurse> nurses = nurseMapper.selectList(null);
        Collection<NurseVo> nurseVos = BeanCopyUtil.copyBeanList(nurses, NurseVo.class);
        return ResponseResult.ok(nurseVos);
    }
}

