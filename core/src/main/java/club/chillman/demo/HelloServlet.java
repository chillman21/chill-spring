package club.chillman.demo;



import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author NIU
 * @createTime 2020/8/1 20:49
 * 复习：重定向和转发的区别
 * 区别   	    转发forward() 	    重定向sendRedirect()
 * 根目录 	    包含项目访问地址 	    没有项目访问地址
 * 地址栏 	    不会发生变化 	        会发生变化
 * 哪里跳转 	    服务器端进行的跳转 	浏览器端进行的跳转
 * 请求域中数据 	不会丢失 	            会丢失
 * 请求次数       1次                 2次
 */
@WebServlet("/hello")
@Slf4j
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = "chillman的简易框架";
        log.debug("name is " + name);
        req.setAttribute("name", name);
        req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req, resp);
    }
}
