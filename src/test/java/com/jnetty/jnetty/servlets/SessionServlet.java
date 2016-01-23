package com.jnetty.jnetty.servlets;

import com.jnetty.util.log.JNettyLogger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wanghaiyang on 16/1/21.
 */
public class SessionServlet extends HttpServlet {

    private static int count = 1;

    @Override
    public void init() {
        JNettyLogger.logI("INIT SessionServlet.");
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("force") != null) {
            request.getSession(true);
        }
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        if (session == null) {
            out.print("<h3>Session is Null</h3>");
        } else {
            String username = (String)session.getAttribute("username");
            if (username == null) {
                out.print("<h3>Create Session</h3>");
                session.setAttribute("username", "WHY" + count++);
            } else {
                out.print("<h3>Username: " + username +"</h3>");
            }
        }

        out.close();
    }
}
