package com.atguigu.mybatisplus.demo;

import com.atguigu.mybatisplus.demo.mapper.UserMapper;
import com.atguigu.mybatisplus.demo.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

    //继承了basemapper的所有方法
    @Autowired
    private UserMapper userMapper;


    //测试插入
    @Test
    public void testInsert(){
        User user = new User();
        user.setName("赵四");
        user.setAge(23);
        user.setEmail("624597867@qq.com");

        int insert = userMapper.insert(user);  //自动生成ID
        System.out.println(insert);
        System.out.println(user);
    }

    //测试更新
    @Test
    public void testUpdate(){
        User user = new User();
        //通过设置的条件自动拼接动态sql
        user.setId(7L);
        user.setName("李四");
        user.setAge(19);
        // updateById 方法的参数是一个对象
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    //测试乐观锁（成功案例）
    @Test
    public void testOptimisticLocker(){
        //查询用户信息
        User user = userMapper.selectById(1L);
        //修改用户信息
        user.setName("xiugai");
        user.setEmail("3131313@163.com");
        //执行更新操作
        userMapper.updateById(user);
    }

    //测试乐观锁（失败案例）
    @Test
    public void testOptimisticLocker2(){
        //线程1
        User user = userMapper.selectById(1L);
        user.setName("xiugai111");
        user.setEmail("3131313@163.com");

        //模拟线程二的插队操作
        User user2 = userMapper.selectById(1L);
        user2.setName("xiugai222");
        user2.setEmail("3131313@163.com");
        userMapper.updateById(user2);

        //使用自旋锁来多次尝试提交
        userMapper.updateById(user); // 没有乐观锁情况下会覆盖插队线程的值
    }

    //测试查询操作
    @Test
    //批量查询
    public void testSelectById(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1,2,3));
        System.out.println(users);
        users.forEach(System.out::println);
    }
    //条件查询一： 使用map
    @Test
    public void testSelect2(){
        HashMap<String, Object> hashMap = new HashMap<>();
        //自定义查询条件(name=张三 且 gae = 19 )
        hashMap.put("name","zhangsan");
        hashMap.put("age",19);
        List<User> users = userMapper.selectByMap(hashMap);
        users.forEach(System.out::println);

    }

    //测试分页查询
    @Test
    public void testPage(){
        // 参数一：当前页    参数二：页面大小
        Page<User> page = new Page<>(1,5);
        userMapper.selectPage(page,null);
        page.getRecords().forEach(System.out::println);

        System.out.println(page.getTotal());
    }

    //测试删除
    @Test
    public void testDelete(){
        userMapper.deleteById(11L);
    }

    //批量删除
    @Test
    public void testDeleteBatchId(){
//        userMapper.deleteBatchIds(Arrays.asList(2L,3L));
    }

    //通过Map删除
    @Test
    public void testDeleteByMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","李四");
        userMapper.deleteByMap(map);
    }

    //wrapperTest
    @Test
    void contextLoads() {
        // 查询name不为空的用户，并且邮箱不为空的用户，年龄大于等于12
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .isNotNull("name")
                .isNotNull("email")
                .ge("age",12);
        userMapper.selectList(wrapper).forEach(System.out::println); // 和我们刚才学习的map对比一下
    }

    @Test
    void test2(){
        // 查询名字狂神说
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","狂神说");
        User user = userMapper.selectOne(wrapper); // 查询一个数据，出现多个结果使用List或者 Map
        System.out.println(user);
    }

    @Test
    void test3(){
        // 查询年龄在 20 ~ 30 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",20,30); // 区间
        Integer count = userMapper.selectCount(wrapper);// 查询结果数
        System.out.println(count);
    }

    // 模糊查询
    @Test
    void test4(){
        // 查询年龄在 20 ~ 30 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 左和右 t%
        wrapper
                .notLike("name","e")
                .likeRight("email","t");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    @Test
    void test5(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // id 在子查询中查出来
        wrapper.inSql("id","select id from user where id<3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    @Test
    void test6(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 通过id进行排序
        wrapper.orderByAsc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }



}
