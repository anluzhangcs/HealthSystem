package org.graduate.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @NotBlank(message = "姓名不能为空")
    private String name;
    //老人年龄
    @NotNull
    private Integer age;

    //老人性别
    @NotBlank
    private String gender;

    //身份证号
    @Pattern(regexp = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$")
    private String idNumber;
    //手机号
    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$")
    private String phoneNumber;
    //入院时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDate enterDate;
    //监护人
    @NotBlank
    private String guardian;
    //监护人联系方式
    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$")
    private String guardianNumber;
    //监管护工
    private Long nurseId;
    //监管医生
    private Long doctorId;

    //逻辑删除
    private Integer delFlag;

}

