USE seckill;

CREATE TABLE seckill(
  `seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` VARCHAR(120) NOT NULL COMMENT '商品名称',
  `number` INT NOT NULL COMMENT '库存数量',
  `start_time` TIMESTAMP NOT NULL DEFAULT '20000101000000' COMMENT '秒杀开启时间',
  `end_time` TIMESTAMP NOT NULL  DEFAULT '20000101000000' COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT = 10000 DEFAULT CHARSET=utf8 COMMENT '秒杀库存表';

INSERT INTO seckill(name, number, start_time, end_time)
VALUES ('1000元秒杀iPhone6',100,'2016-09-09 00:00:00','2016-09-10 00:00:00'),
       ('500元秒杀iPad mini3',200,'2016-09-09 00:00:00','2016-09-10 00:00:00'),
       ('300元秒杀红米',300,'2016-09-09 00:00:00','2016-09-10 00:00:00'),
       ('100元秒杀红米Note',400,'2016-09-09 00:00:00','2016-09-10 00:00:00');

CREATE TABLE success_killed(
  `seckill_id` BIGINT NOT NULL COMMENT '商品库存id',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态表示:-1:无效,0:成功,1:已付款,2:已发货',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone), /* 联合主键 */
  KEY idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '秒杀明细表';




