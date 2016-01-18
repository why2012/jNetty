package com.jnetty.jnetty.servlets;

import com.jnetty.util.log.JNettyLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wanghaiyang on 16/1/19.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    @ResponseBody
    public String index() throws IOException {
        JNettyLogger.logI("[HomeControler] All Right.");
        return "<h3>All Right</h3>";
    }
}
