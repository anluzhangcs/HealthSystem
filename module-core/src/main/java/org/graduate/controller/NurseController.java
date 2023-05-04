package org.graduate.controller;

import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Nurse;
import org.graduate.service.NurseService;
import org.graduate.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Zhanghao
 * @date: 2023/4/18-16:02
 * @Description
 */

@RestController
@RequestMapping("/happyhome/nurse")
public class NurseController {

    @Autowired
    private NurseService nurseService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('nurse:query')")
    public ResponseResult getNurseList(SearchModel model) throws Exception {
        return nurseService.getNurseList(model);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('nurse:add')")
    public ResponseResult addNurse(@RequestBody Nurse nurse) {
        return nurseService.addNurse(nurse);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('nurse:edit')")
    public ResponseResult editNurse(@RequestBody Nurse nurse) {
        return nurseService.editNurse(nurse);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('nurse:delete')")
    public ResponseResult deleteNurse(@PathVariable Long id) {
        return nurseService.deleteNurse(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('elder:demand','elder:edit','elder:add')")
    public ResponseResult getAllNurses() throws Exception {
        return nurseService.getAllNurses();
    }
}
