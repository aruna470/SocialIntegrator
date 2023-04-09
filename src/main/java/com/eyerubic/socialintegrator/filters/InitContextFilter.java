package com.eyerubic.socialintegrator.filters;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.eyerubic.socialintegrator.helpers.AppLogger;
import com.eyerubic.socialintegrator.helpers.ContextData;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * This filter class used set attributes comes in http request to appLogger object.
 * Which will be used in latter section of the request. This is the first filter to be
 * executed after the authentication.
 */
@Component
@Order(1)
public class InitContextFilter implements Filter {

    @Resource(name = "requestScopedBeanAppLogger")
    AppLogger appLogger;
    
    @Resource(name = "requestScopedBeanContextData")
    ContextData contextData;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
        FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        HashMap<String, String> userInfo = (HashMap<String, String>) httpRequest.getAttribute("userInfo");
        
        if (userInfo != null) {
            contextData.setEmail(userInfo.get("email"));
            contextData.setUserId(userInfo.get("userId"));
        }

        appLogger.setHostIp(httpRequest.getLocalAddr());
        appLogger.setUserIdentifier(contextData.getEmail());
        appLogger.setUniqueId(UUID.randomUUID().toString());
        appLogger.setEndpoint(httpRequest.getRequestURI());
        appLogger.setHttpMethod(httpRequest.getMethod());
        appLogger.setRequestIp(httpRequest.getRemoteAddr());

        appLogger.info("Request authentication success.");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
