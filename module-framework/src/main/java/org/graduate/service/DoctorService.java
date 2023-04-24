package org.graduate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Doctor;
import org.graduate.utils.ResponseResult;

/**
 * (Doctor)
 *
 * @author Zhanghao
 * @since 2023-04-24 21:31:15
 */
public interface DoctorService extends IService<Doctor> {

    ResponseResult getDoctorList(SearchModel model);

    ResponseResult addDoctor(Doctor doctor);

    ResponseResult editDoctor(Doctor doctor);

    ResponseResult deleteDoctor(Long id);
}

