package mio.tllogin.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter(urlPatterns= {"/login/*","/registration/*"}, description="ParamFilter")
public class XSSFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.info("[■ XSSFilter ] : " + Thread.currentThread().getStackTrace()[1].getClassName());

        chain.doFilter(new mio.tllogin.filter.XSSRequestWrapper((HttpServletRequest) request),response);
    }
}
