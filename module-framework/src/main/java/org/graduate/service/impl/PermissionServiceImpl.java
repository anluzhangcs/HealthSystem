package org.graduate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.graduate.domain.entity.Permission;
import org.graduate.mapper.PermissionMapper;
import org.graduate.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * (Permission)
 *
 * @author Zhanghao
 * @since 2023-04-08 18:01:31
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}

