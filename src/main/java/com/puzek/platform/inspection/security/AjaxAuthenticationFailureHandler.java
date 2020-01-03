package com.puzek.platform.inspection.security;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.util.JsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 用户登录失败时返回给前端的数据
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        httpServletResponse.getWriter().write(JsonUtil.toJson(Msg.failed(e.getMessage())));
    }
}
