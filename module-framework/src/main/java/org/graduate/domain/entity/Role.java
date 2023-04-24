package org.graduate.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * (Role)
 *
 * @author Zhanghao
 * @since 2023-04-14 23:01:02
 */
@Data
@NoArgsConstructor
public class Role {
    //角色编号
    private Long id;
    //角色名称
    private String roleName;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //角色编码
    private String roleCode;
    //是否删除(0-未删除，1-已删除)
    private Integer delFlag;


}

