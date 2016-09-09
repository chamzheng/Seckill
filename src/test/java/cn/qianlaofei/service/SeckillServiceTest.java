package cn.qianlaofei.service;

import cn.qianlaofei.dto.Exposer;
import cn.qianlaofei.dto.SeckillExecution;
import cn.qianlaofei.entity.Seckill;
import cn.qianlaofei.exception.RepeatKillException;
import cn.qianlaofei.exception.SeckillClosedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Peter on 9/7/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                       "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void seckillService() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list = {} ",list);
    }

    @Test
    public void getById() throws Exception {
        long id = 10000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill = {} ",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 10001;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer = {}",exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 10001;
        long phone = 15651015117L;
        String md5 = "d672af8566db51a56af911b50f4c0600";
        try{
            SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
            logger.info("seckillExecution = {}",seckillExecution);
        }catch (RepeatKillException e){
            logger.error(e.getMessage(),e);
        }catch (SeckillClosedException e) {
            logger.error(e.getMessage(),e);
        }
    }
    /*
        19:42:12.846 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Creating a new SqlSession
        19:42:12.853 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4397ad89]
        19:42:12.863 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@79dc5318] will be managed by Spring
        19:42:12.869 [main] DEBUG c.q.dao.SeckillDao.reduceNumber - ==>  Preparing: UPDATE seckill SET number = number - 1 WHERE seckill_id = ? AND start_time <= ? AND end_time >= ? AND number > 0;
        19:42:12.901 [main] DEBUG c.q.dao.SeckillDao.reduceNumber - ==> Parameters: 10001(Long), 2016-09-07 19:42:12.833(Timestamp), 2016-09-07 19:42:12.833(Timestamp)
        19:42:12.902 [main] DEBUG c.q.dao.SeckillDao.reduceNumber - <==    Updates: 1
        19:42:12.902 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4397ad89]
        19:42:12.903 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4397ad89] from current transaction
        19:42:12.903 [main] DEBUG c.q.d.S.insertSuccessKilled - ==>  Preparing: INSERT IGNORE INTO success_killed(seckill_id,user_phone,state) VALUES (?,?,0)
        19:42:12.904 [main] DEBUG c.q.d.S.insertSuccessKilled - ==> Parameters: 10001(Long), 15651015117(Long)
        19:42:12.905 [main] DEBUG c.q.d.S.insertSuccessKilled - <==    Updates: 1
        19:42:12.911 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4397ad89]
        19:42:12.913 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4397ad89] from current transaction
        19:42:12.914 [main] DEBUG c.q.d.S.queryByIdWithSeckill - ==>  Preparing: SELECT sk.seckill_id, sk.user_phone, sk.state, sk.create_time, s.seckill_id "seckill.seckill_id", s.name "seckill.name", s.number "seckill.number", s.start_time "seckill.start_time", s.end_time "seckill.end_time", s.create_time "seckill.create_time" FROM success_killed sk INNER JOIN seckill s ON sk.seckill_id = s.seckill_id WHERE sk.seckill_id = ? AND sk.user_phone = ?
        19:42:12.915 [main] DEBUG c.q.d.S.queryByIdWithSeckill - ==> Parameters: 10001(Long), 15651015117(Long)
        19:42:12.943 [main] DEBUG c.q.d.S.queryByIdWithSeckill - <==      Total: 1
        19:42:12.953 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4397ad89]
        19:42:12.954 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization committing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4397ad89]
        19:42:12.954 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4397ad89]
        19:42:12.954 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4397ad89]
        19:42:12.959 [main] INFO  c.q.service.SeckillServiceTest - seckillExecution = SeckillExecution{seckillId=10001, state=1, stateInfo='秒杀成功', successKilled=SuccessKilled{seckillId=10001, userPhone=15651015117, state=0, createTime=Wed Sep 07 19:42:12 CST 2016, seckill=Seckill{seckillId=10001, name='500元秒杀iPad mini3', number=198, startTime=Wed Sep 07 00:00:00 CST 2016, endTime=Sat Sep 10 00:00:00 CST 2016, createTime=Tue Sep 06 17:10:18 CST 2016}}}

     */

}