package cn.qianlaofei.dto;

import cn.qianlaofei.entity.SuccessKilled;
import cn.qianlaofei.enums.SeckillStatusEnum;

/**
 * 封装秒杀执行后的结果
 * Created by Peter on 9/7/16.
 */
public class SeckillExecution {

    private long seckillId;

    //秒杀结果状态
    private int state;

    //状态的标示
    private String stateInfo;

    private SuccessKilled successKilled;

    public SeckillExecution(long seckillId, SeckillStatusEnum status, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = status.getState();
        this.stateInfo = status.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExecution(long seckillId, SeckillStatusEnum status) {
        this.seckillId = seckillId;
        this.state = status.getState();
        this.stateInfo = status.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
