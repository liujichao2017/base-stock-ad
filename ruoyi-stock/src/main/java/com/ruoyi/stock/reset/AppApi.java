package com.ruoyi.stock.reset;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.stock.member.vo.SendSmsVo;
import com.ruoyi.stock.stock.domain.Stock;
import org.springframework.core.env.Environment;


public class AppApi {

    /**
     * 查询股票最新价格
     *
     * @param id
     * @return
     */
    public static Stock getStockByLast(Long id) {
        Environment environment = SpringUtils.getBean(Environment.class);
        String appAddr = environment.getProperty("app.addr");
        String uri = "/api/stock/last/" + id;
        String res = HttpUtils.sendGet(appAddr + uri);
        JSONObject json = JSONObject.parseObject(res);
        if (json.getInteger("code") == 200) {
            return JSONObject.parseObject(json.getString("data"), Stock.class);
        }
        return new Stock();
    }

    public static boolean changeMember(Long id) {
        Environment environment = SpringUtils.getBean(Environment.class);
        String appAddr = environment.getProperty("app.addr");
        String uri = "/api/member/change/" + id;
        String res = HttpUtils.sendGet(appAddr + uri);
        JSONObject json = JSONObject.parseObject(res);
        if (json.getInteger("code") == 200) {
            return true;
        }
        return false;
    }

    public static String sendSms(SendSmsVo req) {
        Environment environment = SpringUtils.getBean(Environment.class);
        String appAddr = environment.getProperty("app.addr");
        String uri = "/api/sms/code";

        StringBuilder sb = new StringBuilder();
        if (LogicUtils.isNotNull(req.getMemberId())) {
            sb.append("memberId=").append(req.getMemberId());
        }

        sb.append("&imitation=").append(req.getImitation());
        sb.append("&type=").append(req.getType());
        if (LogicUtils.isNotNull(req.getSmsId())) {
            sb.append("&smsId=").append(req.getSmsId());
        }
        if (LogicUtils.isNotNull(req.getPhone())) {
            sb.append("&phone=").append(req.getPhone());
        }



        String res = HttpUtils.sendPost(appAddr + uri, sb.toString());
        JSONObject json = JSONObject.parseObject(res);
        if (json.getInteger("code") == 200) {
            return json.getString("data");
        }
        return "";
    }


}
