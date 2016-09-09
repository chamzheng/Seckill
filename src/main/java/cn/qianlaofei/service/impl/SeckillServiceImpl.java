package cn.qianlaofei.service.impl;

import cn.qianlaofei.dao.SeckillDao;
import cn.qianlaofei.dao.SuccessKilledDao;
import cn.qianlaofei.dao.cache.RedisDao;
import cn.qianlaofei.dto.Exposer;
import cn.qianlaofei.dto.SeckillExecution;
import cn.qianlaofei.entity.Seckill;
import cn.qianlaofei.entity.SuccessKilled;
import cn.qianlaofei.enums.SeckillStatusEnum;
import cn.qianlaofei.exception.RepeatKillException;
import cn.qianlaofei.exception.SeckillClosedException;
import cn.qianlaofei.exception.SeckillException;
import cn.qianlaofei.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Peter on 9/7/16.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    private final String salt = "*#JNDwfal1+1`DFv14_=23`/.";//用户混淆

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {

        //Redis缓存
        /*
            get from cache
            if null {
                get form DB
                put cache
                return
            }else {
                return
            }
         */
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null) {
                return new Exposer(false, seckillId);
            } else {
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date currentTime = new Date();
        if (currentTime.getTime() < startTime.getTime() || currentTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, currentTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        //转化一个任意字符串
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillClosedException, RepeatKillException, SeckillException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //秒杀逻辑:减库存 + 购买行为
        try {
            int updateCount = seckillDao.reduceNumber(seckillId, new Date());
            if (updateCount <= 0) {
                //没有减库存
                throw new SeckillClosedException("Seckill is Closed");
            } else {
                //记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    throw new RepeatKillException("Seckill repeated");
                } else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatusEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillClosedException e) {
            throw e;
        } catch (RepeatKillException e) {
            throw e;
        } catch (Exception e) {
            throw new SeckillException("Seckill inner Error:" + e.getMessage());
        }
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
