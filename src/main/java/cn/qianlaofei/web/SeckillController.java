package cn.qianlaofei.web;

import cn.qianlaofei.dto.Exposer;
import cn.qianlaofei.dto.SeckillExecution;
import cn.qianlaofei.dto.SeckillResult;
import cn.qianlaofei.entity.Seckill;
import cn.qianlaofei.enums.SeckillStatusEnum;
import cn.qianlaofei.exception.RepeatKillException;
import cn.qianlaofei.exception.SeckillClosedException;
import cn.qianlaofei.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Peter on 9/7/16.
 */
@Controller
@RequestMapping("/seckill") //模块
public class SeckillController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        //获取列表页
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("list", seckillList);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    //ajax
    @RequestMapping(value = "/{seckillId}/exposer",
            method = {RequestMethod.GET, RequestMethod.POST},
            produces = {"application/json;charset=utf8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
            return result;
        }
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=utf8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long phone) {
        if (phone == null) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        SeckillResult<SeckillExecution> result;
        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
            result = new SeckillResult<SeckillExecution>(true, execution);
            return result;
        } catch (RepeatKillException e) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatusEnum.REPEAT_KILL);
            result = new SeckillResult<SeckillExecution>(false, execution);
            return result;
        } catch (SeckillClosedException e) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatusEnum.END);
            result = new SeckillResult<SeckillExecution>(false, execution);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatusEnum.INNER_ERROR);
            result = new SeckillResult<SeckillExecution>(false, execution);
            return result;
        }
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.POST)
    @ResponseBody
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult<Long>(true, now.getTime());
    }
}
