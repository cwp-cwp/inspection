package com.puzek.platform.inspection.security;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.util.JsonUtil;
import org.apache.http.HttpStatus;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAuthenticationTimeOut implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        httpServletResponse.getWriter().write(JsonUtil.toJson(Msg.success("登录超时")));
    }
}
