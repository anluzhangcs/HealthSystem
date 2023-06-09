package org.graduate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Doctor;
import org.graduate.domain.entity.User;
import org.graduate.domain.vo.DoctorVo;
import org.graduate.mapper.DoctorMapper;
import org.graduate.mapper.UserMapper;
import org.graduate.service.DoctorService;
import org.graduate.utils.BeanCopyUtil;
import org.graduate.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * (Doctor)
 *
 * @author Zhanghao
 * @since 2023-04-24 21:31:15
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 查找
     *
     * @param model
     * @return
     */
    @Override
    public ResponseResult getDoctorList(SearchModel model) {
        //获取查找条件
        long currentPage = model.getCurrentPage();
        long pageSize = model.getPageSize();
        String name = model.getName();

        //封装Page和QueryMapper
        IPage<Doctor> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Doctor> queryWrapper = new LambdaQueryWrapper<>();
        if (name != "" && Objects.nonNull(name)) {
            queryWrapper.like(Doctor::getName, name);
        }
        queryWrapper.orderByAsc(Doctor::getId);

        //查找
        doctorMapper.selectPage(page, queryWrapper);

        return ResponseResult.ok(page);
    }

    /**
     * 增加
     *
     * @param doctor
     * @return
     */
    @Override
    public ResponseResult addDoctor(Doctor doctor) {
        doctorMapper.insert(doctor);
        User user = new User();
        user.setUsername(doctor.getId().toString());
        user.setNickName(doctor.getName());
        user.setPhoneNumber(doctor.getPhoneNumber());
        user.setRoleId(3L);
        userMapper.insert(user);
        return ResponseResult.ok().setMessage("添加成功");
    }

    /**
     * 更新
     *
     * @param doctor
     * @return
     */
    @Override
    public ResponseResult editDoctor(Doctor doctor) {
        doctorMapper.updateById(doctor);
        return ResponseResult.ok().setMessage("更新成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteDoctor(Long id) {
        doctorMapper.deleteById(id);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, id.toString());
        userMapper.delete(queryWrapper);
        return ResponseResult.ok().setMessage("删除成功");
    }

    /**
     * 获取所有医生
     *
     * @return
     */
    @Override
    public ResponseResult getAllDoctors() throws Exception {
        List<Doctor> doctors = doctorMapper.selectList(null);
        Collection<DoctorVo> doctorVos = BeanCopyUtil.copyBeanList(doctors, DoctorVo.class);
        return ResponseResult.ok(doctorVos);
    }

}

