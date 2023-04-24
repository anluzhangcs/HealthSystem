package org.graduate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Manager;
import org.graduate.utils.ResponseResult;

/**
 * (Manager)
 *
 * @author Zhanghao
 * @since 2023-04-24 21:31:15
 */
public interface ManagerService extends IService<Manager> {

    ResponseResult getManagerList(SearchModel model);

    ResponseResult addManager(Manager manager);

    ResponseResult editManager(Manager manager);

    ResponseResult deleteManager(Long id);
}

