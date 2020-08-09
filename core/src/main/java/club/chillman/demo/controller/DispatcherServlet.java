package club.chillman.demo.controller;


import club.chillman.demo.controller.frontend.MainPageController;
import club.chillman.demo.controller.superadmin.HeadLineOperationController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author NIU
 * @createTime 2020/8/1 22:55
 */
@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(" request path is :" + req.getServletPath());
        System.out.println(" request method is :" + req.getMethod());
        if (req.getServletPath() == "/frontend/getmainpageinfo" &&
        req.getMethod() == "GET") {
            new MainPageController().getMainPageInfo(req,resp);
        } else if (req.getServletPath() == "/superadmin/addheadline" &&
                req.getMethod() == "POST") {
            new HeadLineOperationController().addHeadLine(req,resp);
        }
    }
}
