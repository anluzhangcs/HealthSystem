package org.graduate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Elder;
import org.graduate.domain.entity.User;
import org.graduate.mapper.ElderMapper;
import org.graduate.mapper.UserMapper;
import org.graduate.service.ElderService;
import org.graduate.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * (Elder)
 *
 * @author Zhanghao
 * @since 2023-04-18 16:06:44
 */
@Service
public class ElderServiceImpl extends ServiceImpl<ElderMapper, Elder> implements ElderService {


    @Autowired
    private ElderMapper elderMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据id查找老人
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult getElderById(Long id) {
        Elder elder = elderMapper.selectById(id);
        return ResponseResult.ok(elder);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteElder(Long id) {
        elderMapper.deleteById(id);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, id.toString());
        userMapper.delete(queryWrapper);
        return ResponseResult.ok().setMessage("删除成功");
    }

    /**
     * 修改
     *
     * @param elder
     * @return
     */
    @Override
    public ResponseResult editElder(Elder elder) {
        elderMapper.updateById(elder);
        return ResponseResult.ok().setMessage("更新成功");
    }

    /**
     * 新增
     *
     * @param elder
     * @return
     */
    @Override
    public ResponseResult addElder(Elder elder) {
        elderMapper.insert(elder);
        //注册到user表中
        User user = new User();
        user.setUsername(elder.getId().toString());
        user.setNickName(elder.getName());
        user.setPhoneNumber(elder.getPhoneNumber());
        user.setRoleId(5L);
        userMapper.insert(user);

        return ResponseResult.ok().setMessage("添加成功");
    }

    /**
     * 查找
     *
     * @param model
     * @return
     */
    @Override
    public ResponseResult getElderList(SearchModel model) throws Exception {
        //获取查找条件
        long currentPage = model.getCurrentPage();
        long pageSize = model.getPageSize();
        String name = model.getName();

        //封装Page和queryWrapper
        Page<Elder> page = new Page(currentPage, pageSize);
        LambdaQueryWrapper<Elder> queryWrapper = new LambdaQueryWrapper();
        if (name != "" && Objects.nonNull(name)) {
            queryWrapper.like(Elder::getName, name);
        }
        queryWrapper.orderByAsc(Elder::getId);

        elderMapper.selectPage(page, queryWrapper);
        return ResponseResult.ok(page);
    }


}

