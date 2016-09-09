package cn.qianlaofei.exception;

/**
 * 秒杀关闭异常
 * Created by Peter on 9/7/16.
 */
public class SeckillClosedException extends SeckillException {

    public SeckillClosedException(String message) {
        super(message);
    }

    public SeckillClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
