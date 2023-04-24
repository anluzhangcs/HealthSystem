package org.graduate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.graduate.domain.SearchModel;
import org.graduate.domain.entity.Manager;
import org.graduate.mapper.ManagerMapper;
import org.graduate.service.ManagerService;
import org.graduate.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * (Manager)
 *
 * @author Zhanghao
 * @since 2023-04-24 21:31:15
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    /**
     * 查找
     *
     * @param model
     * @return
     */
    @Override
    public ResponseResult getManagerList(SearchModel model) {
        //获取查找条件
        long currentPage = model.getCurrentPage();
        long pageSize = model.getPageSize();
        String name = model.getName();

        //封装Page和QueryMapper
        IPage<Manager> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Manager> queryWrapper = new LambdaQueryWrapper<>();
        if (name != "" && Objects.nonNull(name)) {
            queryWrapper.like(Manager::getName, name);
        }
        queryWrapper.orderByAsc(Manager::getId);

        //查找
        managerMapper.selectPage(page, queryWrapper);

        return ResponseResult.ok(page);
    }

    /**
     * 添加
     *
     * @param manager
     * @return
     */
    @Override
    public ResponseResult addManager(Manager manager) {
        managerMapper.insert(manager);
        return ResponseResult.ok().setMessage("添加成功");
    }

    /**
     * 更新
     *
     * @param manager
     * @return
     */
    @Override
    public ResponseResult editManager(Manager manager) {
        managerMapper.updateById(manager);
        return ResponseResult.ok().setMessage("更新成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteManager(Long id) {
        managerMapper.deleteById(id);
        return ResponseResult.ok().setMessage("删除成功");
    }
}

