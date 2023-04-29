package org.graduate.controller;

import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Demand;
import org.graduate.service.DemandService;
import org.graduate.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: Zhanghao
 * @date: 2023/4/25-22:53
 * @Description
 */
@RestController
@RequestMapping("/happyhome/demand")
public class DemandController {

    @Autowired
    private DemandService demandService;

    @PostMapping("/submit")
    @PreAuthorize("hasAuthority('elder:demand')")
    public ResponseResult submitDemand(@RequestBody @Valid Demand demand) {
        return demandService.submitDemand(demand);
    }

    @GetMapping("/limit")
    @PreAuthorize("hasAuthority('elder:demand')")
    public ResponseResult getDemandLimit() {
        return demandService.getDemandLimit();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('elder:demand')")
    public ResponseResult deleteDemand(String id) {
        return demandService.deleteDemand(id);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('elder:demand')")
    public ResponseResult getDemandList(SearchModel model) {
        return demandService.getDemandList(model);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('elder:demand')")
    public ResponseResult editDemand(@RequestBody @Valid Demand demand) {
        return demandService.editDemand(demand);
    }

    @GetMapping("/todo")
    @PreAuthorize("hasAnyAuthority('doctor:doctorToDo','nurse:nurseToDo')")
    public ResponseResult getToDoList(SearchModel model) {
        return demandService.getToDoList(model);
    }

    @PutMapping("/process")
    @PreAuthorize("hasAnyAuthority('doctor:doctorToDo','nurse:nurseToDo')")
    public ResponseResult processDemand(@RequestBody Demand demand) {
        return demandService.processDemand(demand);
    }

    @PutMapping("/finish")
    @PreAuthorize("hasAnyAuthority('doctor:doctorToDo','nurse:nurseToDo')")
    public ResponseResult finishDemand(@RequestBody Demand demand) {
        return demandService.finishDemand(demand);
    }
}
