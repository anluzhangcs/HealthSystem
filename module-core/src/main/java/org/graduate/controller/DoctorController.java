package org.graduate.controller;

import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Doctor;
import org.graduate.service.DoctorService;
import org.graduate.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Zhanghao
 * @date: 2023/4/18-16:02
 * @Description
 */

@RestController
@RequestMapping("/happyhome/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/list")
    public ResponseResult getDoctorList(SearchModel model) throws Exception {
        return doctorService.getDoctorList(model);
    }

    @PostMapping("/add")
    public ResponseResult addDoctor(@RequestBody Doctor doctor) {
        return doctorService.addDoctor(doctor);
    }

    @PutMapping("/edit")
    public ResponseResult editDoctor(@RequestBody Doctor doctor) {
        return doctorService.editDoctor(doctor);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteDoctor(@PathVariable Long id) {
        return doctorService.deleteDoctor(id);
    }

    @GetMapping("/all")
    public ResponseResult getAllDoctors() throws Exception {
        return doctorService.getAllDoctors();
    }
}
