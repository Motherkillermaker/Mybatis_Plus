package com.atguigu.mybatisplus.demo.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author CJYong
 * @create 2021-10-20 10:45
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    // 对应数据库中的主键（uuid,自增id,雪花算法，redis，zookeeper）
    @TableId(type = IdType.AUTO)
    private long id;
    private String name;
    private Integer age;
    private String email;

    @Version //乐观锁注解
    private Integer version;

    @TableLogic //逻辑删除注解
    private Integer deleted;

    //字段添加填充内容
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
