package cn.smbms.filter;

import cn.smbms.pojo.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class implementsFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userSession");
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        //放行资源
        if (requestURI.contains("/login.jsp")||
                requestURI.contains("/calendar/")||
                requestURI.contains("/css/")||
                requestURI.contains("/images/")||
                requestURI.contains("/js/")||
                requestURI.contains("/login")||
                requestURI.equals("/SMBMS/")
        ){
            System.out.println("放行资源");
            chain.doFilter(req, resp);
        }else {
            if(user != null){
                System.out.println(user+"已登录");
                chain.doFilter(req, resp);
            }else {
                // 用户未登录
                System.out.println("请先登录");
                req.getRequestDispatcher("/error.jsp").forward(req,resp);
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {
    }

}
