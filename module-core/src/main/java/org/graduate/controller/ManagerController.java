package org.graduate.controller;

import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Manager;
import org.graduate.service.ManagerService;
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
@RequestMapping("/happyhome/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('manager:query')")
    public ResponseResult getManagerList(SearchModel model) throws Exception {
        return managerService.getManagerList(model);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('manager:add')")
    public ResponseResult addManager(@RequestBody Manager manager) {
        return managerService.addManager(manager);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('manager:edit')")
    public ResponseResult editManager(@RequestBody Manager manager) {
        return managerService.editManager(manager);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('manager:delete')")
    public ResponseResult deleteManager(@PathVariable Long id) {
        return managerService.deleteManager(id);
    }
}
