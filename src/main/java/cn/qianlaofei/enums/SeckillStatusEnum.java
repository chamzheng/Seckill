package cn.qianlaofei.enums;

/**
 * 使用枚举表示常量数据
 * Created by Peter on 9/7/16.
 */
public enum SeckillStatusEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATARE_WRITE(-3,"数据篡改");

    private int State;
    private String StateInfo;

    SeckillStatusEnum(int state, String stateInfo) {
        State = state;
        StateInfo = stateInfo;
    }

    public int getState() {
        return State;
    }

    public String getStateInfo() {
        return StateInfo;
    }

    public static SeckillStatusEnum stateOf(int index) {
        for (SeckillStatusEnum state : values()) {
            if(state.getState() == index) {
                return state;
            }
        }

        return null;
    }
}
