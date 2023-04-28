package org.graduate.controller;

import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Demand;
import org.graduate.service.DemandService;
import org.graduate.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseResult submitDemand(@RequestBody Demand demand) {
        return demandService.submitDemand(demand);
    }


    @GetMapping("/todo")
    public ResponseResult getToDoList(SearchModel model) {
        return demandService.getToDoList(model);
    }

    @PutMapping("/process")
    public ResponseResult processDemand(@RequestBody Demand demand) {
        return demandService.processDemand(demand);
    }

    @PutMapping("/finish")
    public ResponseResult finishDemand(@RequestBody Demand demand) {
        return demandService.finishDemand(demand);
    }

    @GetMapping("/limit")
    public ResponseResult getDemandLimit() {
        return demandService.getDemandLimit();
    }

    @GetMapping("/list")
    public ResponseResult getDemandList(SearchModel model) {
        return demandService.getDemandList(model);
    }
}
