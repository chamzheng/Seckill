package cn.qianlaofei.dao;

import cn.qianlaofei.entity.SuccessKilled;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by Peter on 9/7/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() throws Exception {
        long id = 10001L;
        long userPhone = 15651015113L;
        int num = successKilledDao.insertSuccessKilled(id,userPhone);
        System.out.println("Insert Number:" + num);
    }

    @Test
    public void testQueryByIdWithSeckill() throws Exception {
        long id = 10000L;
        long userPhone = 15651015113L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, userPhone);
        System.out.println(successKilled);
        /*
        SuccessKilled{seckillId=10000, userPhone=15651015113, state=-1, createTime=Wed Sep 07 15:06:15 CST 2016,
        seckill=Seckill{seckillId=10000, name='1000元秒杀iPhone6', number=100, startTime=Fri Sep 09 00:00:00 CST 2016,
                                                                              endTime=Sat Sep 10 00:00:00 CST 2016,
                                                                              createTime=Tue Sep 06 17:10:18 CST 2016}}

         */
    }

}