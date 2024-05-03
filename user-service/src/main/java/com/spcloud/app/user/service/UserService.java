package com.spcloud.app.user.service;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.jntoo.db.utils.InfoUtil;
import com.jntoo.db.utils.StringUtil;
import com.spcloud.app.user.pojo.Admins;
import com.spcloud.app.user.pojo.PasswordPojo;
import com.spcloud.app.user.pojo.Stuff;
import com.wosys.base.utils.JwtTokenUtils;
import com.wosys.base.utils.R;
import com.wosys.base.utils.Session;
import com.wosys.base.utils.SessionFactory;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserService {

    @Resource
    private AdminsService adminsService;

    @Resource
    private StuffService stuffService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtTokenUtils jwtTokenUtils;

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param pwd      密码
     * @param cx       角色
     * @return
     */
    public R<Object> login(String username, String pwd, String cx) {
        // 判断是否填写用户,没有则提示请填写用户
        if (StringUtil.isNullOrEmpty(username)) {
            return R.error("请填写用户");
        }
        // 判断是否填写密码,没有则提示请填写密码
        if (StringUtil.isNullOrEmpty(pwd)) {
            return R.error("请填写密码");
        }
        // 判断是否选择某角色登录,没有则提示请选择角色
        if (StringUtil.isNullOrEmpty(cx)) {
            return R.error("请选择角色");
        }

        // 使用的表,将保存在session中
        String table = "";
        String roles = cx;
        // 创建session会话变量
        Session session = null;
        pwd = passwordEncoder.encode(pwd);

        // 判断是否为"管理员"角色登录时则使用这个进行登录
        if (cx.equals("管理员")) {
            // 通过AdminsService.login 方法进行登录

            Admins user = adminsService.login(username, pwd);
            // user 为null 则提示用户账号或密码错误
            if (user == null) {
                return R.error("账号或密码错误");
            }

            // 设置table 变量
            // 将bean 实体类转化为session
            session = adminsService.toSession(user);
        }
        // 判断是否为"员工"角色登录时则使用这个进行登录
        if (cx.equals("员工")) {
            // 通过StuffService.login 方法进行登录

            Stuff user = stuffService.login(username, pwd);
            // user 为null 则提示用户账号或密码错误
            if (user == null) {
                return R.error("账号或密码错误");
            }

            // 设置table 变量
            // 将bean 实体类转化为session
            session = stuffService.toSession(user);
        }

        // session 为null 则认为登录失败
        if (session == null) {
            return R.error("没有找到相关登录信息");
        }

        // 设置jwtToken 登录令牌
        Map<String, Object> m = BeanUtil.beanToMap(session);
        m.remove("object");
        m.put("exp", InfoUtil.time() + 7 * 86400);
        // 生成 jwtToken 登录令牌
        String token = jwtTokenUtils.generateToken(session.getId(), m);

        return R.success(R.result().set("session", session) // 将session 返回给前端
                .set("token", token) // 返回登录令牌给用户
        );
    }

    /**
     * token 令牌进行刷新、session 用户信息重新获取
     *
     * @param token 保存在客户端的token令牌
     * @return
     */
    public R<Object> tokenLogin(String token) {
        try {
            /**
             * 原子对象操作
             */
            AtomicReference<Session> user = new AtomicReference();
            // token 数据解析
            jwtTokenUtils.getClaimFromToken(token, map -> {
                // 将解析出来的数据
                user.set(JSONObject.parseObject(JSONObject.toJSONString(map), Session.class));
                return null;
            });

            Session session = user.get();
            if (session == null) {
                return R.error("token解析错误");
            }
            Map<String, Object> m = BeanUtil.beanToMap(session);
            m.remove("object");
            m.put("exp", InfoUtil.time() + 7 * 86400);
            session.refresh();
            String token1 = jwtTokenUtils.generateToken(session.getId(), m);
            return R.success(R.result().set("session", session).set("token", token1));
        } catch (ExpiredJwtException e) {
            return R.error("已超时");
        } catch (UnsupportedJwtException e) {
            return R.error("空数据");
        } catch (MalformedJwtException e) {
            return R.error("数据出错");
        } catch (Exception e) {
            return R.error("解析token 错误");
        }
    }

    public R<Object> editPassword(PasswordPojo passwordPojo) {
        if (StringUtil.isNullOrEmpty(passwordPojo.getOldPassword())) {
            return R.error("请填写原密码");
        }
        if (StringUtil.isNullOrEmpty(passwordPojo.getNewPassword())) {
            return R.error("请填写新密码");
        }
        if (!passwordPojo.getNewPassword().equals(passwordPojo.getConfirmPassword())) {
            return R.error("新密码与原密码不一致");
        }
        String roles = SessionFactory.getRoles();
        if (roles.equals("管理员")) {
            return adminsService.editPassword(SessionFactory.getId(), passwordPojo.getOldPassword(), passwordPojo.getNewPassword());
        }

        if (roles.equals("员工")) {
            return stuffService.editPassword(SessionFactory.getId(), passwordPojo.getOldPassword(), passwordPojo.getNewPassword());
        }

        return R.error("没找到相关信息");
    }
}
