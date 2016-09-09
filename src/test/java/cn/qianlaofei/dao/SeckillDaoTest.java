package cn.qianlaofei.dao;

import cn.qianlaofei.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Peter on 9/7/16.
 */
// 配置Spring 和 JUnit 整合 为了JUnit 启动时加载SpringIoC容器
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉 JUnit Spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    //注入依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testReduceNumber() throws Exception {
        long id = 10000;
        Date now = new Date();
        int num = seckillDao.reduceNumber(id,now);
        System.out.println("num:" + num);
    }

    @Test
    public void testQueryById() throws Exception {
        long id = 10000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void testQueryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
    }

}
