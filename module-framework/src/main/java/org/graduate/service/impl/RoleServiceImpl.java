package org.graduate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.graduate.domain.entity.Role;
import org.graduate.mapper.RoleMapper;
import org.graduate.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * (Role)
 *
 * @author Zhanghao
 * @since 2023-04-08 18:01:31
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}

