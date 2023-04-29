package org.graduate.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

/**
 * (Demand)
 *
 * @author Zhanghao
 * @since 2023-04-28 20:17:33
 */
@Data
@NoArgsConstructor
public class Demand {
    //服务id
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    //服务类别
    private Integer type;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //服务日期
    @Future(message = "必须为未来的时间")
    private LocalDateTime datetime;
    //服务对象类别(1表示护工,2表示医生
    private Integer target;
    //状态(1-创建 2-处理 3-完成)
    private Integer status;
    //老人姓名
    private String elderName;
    //老人id
    private Long elderId;
    //护工/医生id
    private Long staffId;
    //护工/医生姓名
    private String staffName;
    //详细描述
    private String detail;
    //逻辑删除
    @TableLogic
    private Integer delFlag;

}

