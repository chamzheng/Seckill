//存放交互的脚本
//javaScript 模块化
var seckill = {
    //秒杀相关ajax
    URL: {
        now: function () {
            return "/seckill/time/now";
        },
        exposer: function (seckillId) {
            return "/seckill/" + seckillId + "/exposer";
        },
        execution: function (seckillId, md5) {
            return "/seckill/" + seckillId + "/" + md5 + "/execution";
        }
    },
    //处理秒杀逻辑
    handlerSeckillkill: function (seckillId, node) {
        node.hide().html("<button class='btn btn-primary btn-lg' id='killBtn'>开始秒杀</button>");
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            //在回调函数中执行
            if (result && result["success"]) {
                var exposer = result["data"];
                if (exposer["exposed"]) {
                    //开启
                    var killUrl = seckill.URL.execution(seckillId, exposer["md5"]);
                    $("#killBtn").one("click", function () {
                        //绑定执行秒杀请求操作
                        $(this).addClass("disabled");//1.禁用按钮
                        $.post(killUrl, {}, function (result) {
                            if (result) {
                                var killResult = result["data"];
                                var state = killResult["state"];
                                var stateInfo = killResult["stateInfo"];
                                node.html("<span class='label label-success'>" + stateInfo + " </span>");//显示秒杀结果
                            }
                        });//发送请求
                    });//只绑定一次click
                    node.show();
                } else {
                    //未开启
                    var now = exposer["now"];
                    var start = exposer["start"];
                    var end = exposer["end"];
                    seckill.countdown(seckillId, now, start, end);
                }
            } else {
                console.log(result);
            }
        });
    },
    //验证手机号
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    countdown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $("#seckill-box");
        //时间判断
        if (nowTime > endTime) { //结束
            seckillBox.html("秒杀结束!");
        } else if (nowTime < startTime) { //秒杀未开始
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                var foramt = event.strftime("秒杀计时: %D天 %H时 %M分 %S秒");
                seckillBox.html(foramt);
            }).on("finish.countdown", function () { //时间完成后回调时间
                //获取秒杀地址,
                seckill.handlerSeckillkill(seckillId, seckillBox);
            });
        } else {//秒杀开始
            seckill.handlerSeckillkill(seckillId, seckillBox);
        }
    },
    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            //用户手机验证,计时交互
            //规划交互流程
            var id = params['seckillId'];
            var killPhone = $.cookie("killPhone");
            //验证手机号
            if (!seckill.validatePhone(killPhone)) {
                //绑定phone
                //控制输出
                var killPhoneModal = $("#killPhoneModal");
                killPhoneModal.modal({
                    show: true,//显示弹出层
                    backdrop: 'static',//禁止位置关闭
                    keyboard: false
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $("#killPhoneKey").val();
                    if (seckill.validatePhone(inputPhone)) {
                        //写入cookie
                        $.cookie("killPhone", inputPhone, {expire: 7, path: "/seckill"});
                        //刷新页面
                        window.location.reload();
                    } else {
                        $("#killPhoneMessage").hide().html("<label class='label label-danger'>手机号错误!</label>").show(300);
                    }
                });
            }
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            //计时
            $.post(seckill.URL.now(), {}, function (result) {
                if (result && result["success"]) {
                    var nowTime = result['data'];
                    //时间判断
                    seckill.countdown(id, nowTime, startTime, endTime);
                }
                else {
                    console.log("result:" + result);
                }
            });
        }
    }
}