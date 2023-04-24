package org.graduate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Elder;
import org.graduate.utils.ResponseResult;

/**
 * (Elder)
 *
 * @author Zhanghao
 * @since 2023-04-18 16:06:44
 */
public interface ElderService extends IService<Elder> {

    ResponseResult getElderList(SearchModel model) throws Exception;

    ResponseResult addElder(Elder elder);

    ResponseResult editElder(Elder elder);

    ResponseResult deleteElder(Long id);
}

