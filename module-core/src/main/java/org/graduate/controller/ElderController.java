package org.graduate.controller;

import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Elder;
import org.graduate.service.ElderService;
import org.graduate.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Zhanghao
 * @date: 2023/4/18-16:02
 * @Description
 */

@RestController
@RequestMapping("/happyhome/elder")
public class ElderController {

    @Autowired
    private ElderService elderService;

    @GetMapping("/list")
    public ResponseResult getElderList(SearchModel model) throws Exception {
        return elderService.getElderList(model);
    }

    @PostMapping("/add")
    public ResponseResult addElder(@RequestBody Elder elder) {
        return elderService.addElder(elder);
    }

    @PutMapping("/edit")
    public ResponseResult editElder(@RequestBody Elder elder) {
        return elderService.editElder(elder);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteElder(@PathVariable Long id) {
        return elderService.deleteElder(id);
    }
}
