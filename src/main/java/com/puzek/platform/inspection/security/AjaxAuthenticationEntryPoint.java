package com.puzek.platform.inspection.security;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.util.JsonUtil;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 用户没有登录时返回给前端的数据
@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JsonUtil.toJson(Msg.failed("没有登录")));
    }
}
