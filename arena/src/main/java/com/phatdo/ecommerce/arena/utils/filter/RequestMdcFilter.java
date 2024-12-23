package com.phatdo.ecommerce.arena.utils.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestMdcFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest httpRequest) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (username.isEmpty())
                username = "anonymous";
            MDC.put("uuid", UUID.randomUUID().toString());
            MDC.put("method", httpRequest.getMethod());
            MDC.put("api", httpRequest.getRequestURI());
            MDC.put("user", username);
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }
    }
}
