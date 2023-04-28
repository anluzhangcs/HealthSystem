package org.graduate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Nurse;
import org.graduate.utils.ResponseResult;

/**
 * (Nurse)
 *
 * @author Zhanghao
 * @since 2023-04-24 21:31:15
 */
public interface NurseService extends IService<Nurse> {

    ResponseResult getNurseList(SearchModel model);

    ResponseResult addNurse(Nurse nurse);

    ResponseResult editNurse(Nurse nurse);

    ResponseResult deleteNurse(Long id);

    ResponseResult getAllNurses() throws Exception;
}

