package com.jnetty.jnetty.servlets;

import com.jnetty.util.log.JNettyLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by wanghaiyang on 16/1/19.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String index(@RequestParam("url") String url) throws IOException {
        JNettyLogger.logI("[HomeControler] All Right.");
        return "<h3>All Right</h3><h3>" + url + "</h3>";
    }
}
