package com.wosys.base.config;

import com.alibaba.fastjson.JSONObject;
import com.jntoo.db.utils.StringUtil;
import com.wosys.base.utils.JwtTokenUtils;
import com.wosys.base.utils.Session;
import com.wosys.base.utils.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class SessionFilter implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(SessionFilter.class);

    @Resource
    private JwtTokenUtils jwtTokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (!StringUtil.isNullOrEmpty(token)) {
            /**
             * 原子对象操作
             */
            AtomicReference<Session> user = new AtomicReference();
            // token 数据解析
            try {
                jwtTokenUtils.getClaimFromToken(
                        token,
                        map -> {
                            // 将解析出来的数据
                            user.set(JSONObject.parseObject(JSONObject.toJSONString(map), Session.class));
                            return null;
                        }
                );
                Session session = user.get();
                session.refresh();
                SessionFactory.set(session);
            } catch (Exception e) {
                SessionFactory.set(null);
            }
        } else {
            SessionFactory.set(null);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
