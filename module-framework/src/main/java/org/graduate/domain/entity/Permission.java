package org.graduate.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * (Permission)
 *
 * @author Zhanghao
 * @since 2023-04-14 23:01:02
 */
@Data
@NoArgsConstructor
public class Permission {
    //权限编号
    private Long id;
    //权限名称
    private String label;
    //父权限ID
    private Long parentId;
    //授权标识符
    private String code;
    //组件路径
    private String url;
    //权限类型(0-一级菜单 1-二级菜单 2-按钮)
    private Integer type;
    //图标
    private String icon;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //路由地址
    private String routerPath;
    //路由名称
    private String routerName;
    //是否删除(0-未删除，1-已删除)
    private Integer delLag;

    @TableField(exist = false)
    private List<Permission> children;
}

