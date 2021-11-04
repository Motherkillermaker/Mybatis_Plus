package com.atguigu.mybatisplus.demo.mapper;

import com.atguigu.mybatisplus.demo.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author CJYong
 * @create 2021-10-20 10:48
 */

//在对应的Mapper上面实现基本的接口
@Repository
public interface UserMapper extends BaseMapper<User> {
    //所有的CRUD已经编写完成
    //不需要像以前一样去生成Mapper接口和完成配置文件
}
