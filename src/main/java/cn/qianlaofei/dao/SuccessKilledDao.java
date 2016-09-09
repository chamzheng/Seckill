package cn.qianlaofei.dao;

import cn.qianlaofei.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Peter on 9/6/16.
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细,可过滤重复
     * @param seckillId
     * @param userPhone
     * @return 如果影响行数>=1,表示更新的行数.
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 查询SuccessKilled并携带Seckill
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
