package org.graduate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Doctor;
import org.graduate.mapper.DoctorMapper;
import org.graduate.service.DoctorService;
import org.graduate.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return ResponseResult.ok().setMessage("删除成功");
    }
}

