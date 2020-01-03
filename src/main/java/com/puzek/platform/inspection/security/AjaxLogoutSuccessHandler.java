package com.puzek.platform.inspection.security;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.util.JsonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        httpServletResponse.getWriter().write(JsonUtil.toJson(Msg.success("退出登录成功")));
    }
}
