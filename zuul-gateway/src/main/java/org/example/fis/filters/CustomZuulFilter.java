package org.example.fis.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomZuulFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(CustomZuulFilter.class);

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader("Zuul", "ZuulCustomHeader");

        HttpServletRequest request = ctx.getRequest();

        log.info(String.format("Zuul> %s request to %s", request.getMethod(), request.getRequestURL().toString()));

        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 1110;
    }

    @Override
    public String filterType() {
        return "pre";
    }

}