package org.graduate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Demand;
import org.graduate.domain.entity.Elder;
import org.graduate.mapper.DemandMapper;
import org.graduate.mapper.ElderMapper;
import org.graduate.service.DemandService;
import org.graduate.utils.ResponseResult;
import org.graduate.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * (Demand)
 *
 * @author Zhanghao
 * @since 2023-04-25 22:53:26
 */
@Service
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand> implements DemandService {


    @Autowired
    private DemandMapper demandMapper;

    @Autowired
    private ElderMapper elderMapper;

    /**
     * 提交请求
     *
     * @param demand
     * @return
     */
    @Override
    public ResponseResult submitDemand(Demand demand) {
        //1.获取当前老人id并查找老人
        String username = SecurityUtils.getLoginUser().getUsername();
        Elder elder = elderMapper.selectById(Long.valueOf(username));

        //2.注入数据
        demand.setStatus(0);
        if (elder != null) {
            demand.setElderId(elder.getId());
            demand.setElderName(elder.getName());
        }
        demandMapper.insert(demand);
        return ResponseResult.ok().setMessage("提交成功");
    }

    /**
     * 获取ToDo列表
     *
     * @param model
     * @return
     */
    @Override
    public ResponseResult getToDoList(SearchModel model) {
        //1.获取医生/护工id
        String username = SecurityUtils.getLoginUser().getUsername();

        //2.解析model
        long currentPage = model.getCurrentPage();
        long pageSize = model.getPageSize();
        String name = model.getName();

        //3.封装Page和querywrapper
        IPage<Demand> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Demand> queryWrapper = new LambdaQueryWrapper<>();
        if (name != "" && Objects.nonNull(name)) {
            queryWrapper.like(Demand::getDetail, name);
        }
        if (!username.equals("0000000")) { //管理员展示所有
            queryWrapper.eq(Demand::getValue, Long.valueOf(username));
        }

        //4.查询
        demandMapper.selectPage(page, queryWrapper);
        return ResponseResult.ok(page);
    }

    /**
     * 更新状态
     *
     * @param demand
     * @return
     */
    @Override
    public ResponseResult processDemand(Demand demand) {
        LambdaQueryWrapper<Demand> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Demand::getId, demand.getId());
        demand.setStatus(1);
        demandMapper.update(demand, wrapper);
        return ResponseResult.ok().setMessage("更新状态成功");
    }

    /**
     * 完成请求
     *
     * @param demand
     * @return
     */
    @Override
    public ResponseResult finishDemand(Demand demand) {
        LambdaQueryWrapper<Demand> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Demand::getId, demand.getId());
        demand.setStatus(1);
        demandMapper.update(demand, wrapper);
        return ResponseResult.ok().setMessage("完成");
    }

    /**
     * 根据老人id获取demand
     *
     * @param
     * @return
     */
    @Override
    public ResponseResult getDemandLimit() {
        String username = SecurityUtils.getLoginUser().getUsername();
        LambdaQueryWrapper<Demand> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Demand::getElderId, Long.valueOf(username));
        queryWrapper.orderByDesc(Demand::getCreateTime);
        queryWrapper.last("limit 2");
        List<Demand> demands = demandMapper.selectList(queryWrapper);
        return ResponseResult.ok(demands);
    }

    /**
     * 获取提交记录
     *
     * @param model
     * @return
     */
    @Override
    public ResponseResult getDemandList(SearchModel model) {
        //1.获取老人id
        String username = SecurityUtils.getLoginUser().getUsername();

        //2.解析model
        long currentPage = model.getCurrentPage();
        long pageSize = model.getPageSize();
        String name = model.getName();

        //3.封装Page和querywrapper
        IPage<Demand> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Demand> queryWrapper = new LambdaQueryWrapper<>();
        if (name != "" && Objects.nonNull(name)) {
            queryWrapper.like(Demand::getDetail, name);
        }
        if (!username.equals("0000000")) { //管理员展示所有
            queryWrapper.eq(Demand::getElderId, Long.valueOf(username));
        }

        //4.查询
        demandMapper.selectPage(page, queryWrapper);
        return ResponseResult.ok(page);
    }
}

