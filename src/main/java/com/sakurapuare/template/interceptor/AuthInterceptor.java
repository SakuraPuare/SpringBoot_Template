package com.sakurapuare.template.interceptor;

import com.sakurapuare.template.constant.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    // private final AccountsService accountService;

    private void writeResponse(HttpServletResponse response, int code, String message) {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write("{\"code\":" + code + ",\"message\":\"" + message + "\",\"data\":null}");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        // 获取token
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            writeResponse(response, 401, "未登录");
            return false;
        }

        // 验证token并获取用户信息
        // Accounts account;
        try {
            token = token.replace("Bearer ", "");
            // account = accountService.getAccountByToken(token);
            // UserContext.setAccount(account);
        } catch (Exception e) {
            writeResponse(response, 401, "token无效");
            return false;
        }


        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                @NonNull Object handler, @SuppressWarnings("null") Exception ex) {
        // 清理ThreadLocal
        // UserContext.clear();
    }
}