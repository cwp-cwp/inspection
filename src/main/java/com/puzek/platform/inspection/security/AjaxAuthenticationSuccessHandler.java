package com.puzek.platform.inspection.security;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.util.JsonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String wsUrl = getWebSocketUrl(httpServletRequest);
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        httpServletResponse.getWriter().write(JsonUtil.toJson(Msg.success("登录成功").add("websocket", wsUrl)));
    }

    // 获取服务器的ip端口以及项目名，拼接成ws的url
    private String getWebSocketUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        url = url.substring(url.indexOf("//") + 2);
        url = url.substring(0, url.indexOf("/"));
        return "ws://" + url + request.getServletContext().getContextPath() + "/" + "pushAlarmData";
    }
}
