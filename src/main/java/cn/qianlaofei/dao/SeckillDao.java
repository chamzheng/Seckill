package cn.qianlaofei.dao;

import cn.qianlaofei.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Peter on 9/6/16.
 */
public interface SeckillDao {
    /**
     * 减库存
     * @param seckillId 减库存的id
     * @param killTime 减库存时间
     * @return 表示插入的行数
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据id获取秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 获取秒杀对象列表
     * @param offset 偏移量
     * @param limit 多少条几率
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
