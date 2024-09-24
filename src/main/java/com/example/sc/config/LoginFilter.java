package com.example.sc.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@WebFilter(filterName = "LoginFilter",urlPatterns = "/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //System.out.println("只因你太美");

        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;


        String uri = request1.getRequestURI();
        System.out.println(uri);

        //需要拦截的网页
        String[] admin_urls = new String[]{
                "/admin/count",
                "/admin/search",
                "/admin/change",
                "/admin/add"
        };




        List<String> admin_list = Arrays.asList(admin_urls);


        System.out.println(request1.getSession().getAttribute("admin"));


        //管理员拦截器
        if(admin_list.contains(uri) && request1.getSession().getAttribute("admin") != null){
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("请登录后获取数据");
        }
        else{
            //放行
            chain.doFilter(request1,response1);
        }



    }

    @Override
    public void destroy() {

    }
}
