package cn.qianlaofei.service;

import cn.qianlaofei.dto.Exposer;
import cn.qianlaofei.dto.SeckillExecution;
import cn.qianlaofei.entity.Seckill;
import cn.qianlaofei.exception.RepeatKillException;
import cn.qianlaofei.exception.SeckillClosedException;
import cn.qianlaofei.exception.SeckillException;

import java.util.List;

/**
 * 业务接口
 * 在"使用者"的角度
 * 三个方面:    方法定义粒度,参数,返回类型(类型/异常)
 * Created by Peter on 9/7/16.
 */
public interface SeckillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    Seckill getById(long seckillId);

    /**
     * 输出秒杀接口的地址
     * 秒杀开启时,否者输出系统时间和秒杀时间,
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀
     * @param seckillId 用户id
     * @param userPhone 用户手机
     * @param md5 加密
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillClosedException,RepeatKillException,SeckillException;
}
