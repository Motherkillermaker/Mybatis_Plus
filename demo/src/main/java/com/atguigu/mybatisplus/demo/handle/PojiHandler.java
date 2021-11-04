package com.atguigu.mybatisplus.demo.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author CJYong
 * @create 2021-10-20 13:51
 */
@Slf4j
@Component  // 不要忘记将处理组件加入到IOC容器中
public class PojiHandler implements MetaObjectHandler {
    @Override
    //插入时的填充策略
    public void insertFill(MetaObject metaObject) {
      log.info("start insert fill");
      this.setFieldValByName("createTime",new Date(),metaObject);
      this.setFieldValByName("updateTime",new Date(),metaObject);
    }

    @Override
    //更新时的填充策略
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill");
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}
