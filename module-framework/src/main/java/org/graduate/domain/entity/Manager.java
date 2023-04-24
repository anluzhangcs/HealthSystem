package org.graduate.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * (Manager)
 *
 * @author Zhanghao
 * @since 2023-04-24 21:31:15
 */
@Data
@NoArgsConstructor
public class Manager {
    //员工工号
    private Long id;
    //姓名
    private String name;
    //年龄
    private Integer age;
    //性别
    private String gender;
    //手机号
    private String phoneNumber;
    //入职时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDate enterDate;
    //逻辑删除字段(0-未删除,1-已删除)
    private Integer delFlag;


}

