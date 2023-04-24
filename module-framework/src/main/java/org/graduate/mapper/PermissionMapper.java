package org.graduate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.graduate.domain.entity.Permission;

import java.util.List;

/**
 * (Permission)
 *
 * @author Zhanghao
 * @since 2023-04-08 18:01:31
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    public List<Permission> getPermissionList(Long roleId);


}

