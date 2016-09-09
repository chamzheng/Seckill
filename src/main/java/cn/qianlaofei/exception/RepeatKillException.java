package cn.qianlaofei.exception;

/**
 * 重复秒杀异常
 * Created by Peter on 9/7/16.
 */
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
