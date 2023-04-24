package org.graduate.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * (Elder)
 *
 * @author Zhanghao
 * @since 2023-04-18 16:06:44
 */
@Data
@NoArgsConstructor
public class Elder {
    //老人id
    private Long id;
    //老人姓名
    private String name;
    //老人年龄
    private Integer age;

    //老人性别
    private String gender;

    //身份证号
    private String idNumber;
    //手机号
    private String phoneNumber;
    //入院时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDate enterDate;
    //监护人
    private String guardian;
    //监护人联系方式
    private String guardianNumber;
    //监管护工
    private Long nurseId;
    //监管医生
    private Long doctorId;

    //逻辑删除
    private Integer delFlag;

}

