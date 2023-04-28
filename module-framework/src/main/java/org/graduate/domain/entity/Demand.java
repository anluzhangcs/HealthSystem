package org.graduate.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * (Demand)
 *
 * @author Zhanghao
 * @since 2023-04-26 12:54:55
 */
@Data
@NoArgsConstructor
public class Demand {
    //服务id
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    //老人姓名
    private String elderName;
    //老人id
    private Long elderId;
    //服务类别
    private Integer type;
    //护工/医生id
    private Long value;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //服务日期
    private LocalDateTime datetime;
    //服务对象类别(0表示护工,1表示医生
    private Integer target;
    //状态(0-创建 1-处理 2-完成)
    private Integer status;

    //详情
    private String detail;


}

