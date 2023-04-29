package org.graduate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Demand;
import org.graduate.utils.ResponseResult;

/**
 * (Demand)
 *
 * @author Zhanghao
 * @since 2023-04-25 22:53:26
 */
public interface DemandService extends IService<Demand> {

    ResponseResult submitDemand(Demand demand);

    ResponseResult getToDoList(SearchModel model);

    ResponseResult processDemand(Demand demand);

    ResponseResult finishDemand(Demand demand);

    ResponseResult getDemandLimit();

    ResponseResult getDemandList(SearchModel model);

    ResponseResult deleteDemand(String id);

    ResponseResult editDemand(Demand demand);
}

