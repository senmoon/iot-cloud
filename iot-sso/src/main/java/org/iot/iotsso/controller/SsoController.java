package org.iot.iotsso.controller;

import org.iot.iotcommon.config.BaseResult;
import org.iot.iotcommon.model.User;
import org.iot.iotsso.service.LoginService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/sso")
@RestController
public class SsoController {
    @Resource
    private LoginService loginService;

    @RequestMapping(value = "getPublicKey", method = RequestMethod.POST)
    public BaseResult<String> getPublicKey(@RequestBody User user) {
        BaseResult<String> fail = BaseResult.failWithStatusAndMsg(false, "获取数据失败");
        if (user == null) return fail;
        String account = user.getAccount();
        if (account == null || "".equals(account)) return fail;
        String publicKey = null;
        try {
            publicKey = loginService.getPublicKey(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (publicKey == null) return fail;
        return BaseResult.successWithData(publicKey);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseResult<User> login(@RequestBody User user, HttpServletRequest request) {
        User u = loginService.login(user, request);
        if (u == null) return BaseResult.failWithStatusAndMsg(false, "登录失败");
        return BaseResult.successWithData(u);
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public BaseResult<Boolean> logout(HttpServletRequest request) {
        boolean bool = loginService.logout(request);
        return bool ? BaseResult.successWithData(bool) : BaseResult.failWithStatusAndMsg(false, "退出失败");
    }

    @RequestMapping("jump")
    public BaseResult<User> jump(String token) {
        User user = loginService.jump(token);
        if (user == null) return BaseResult.failWithStatusAndMsg(false, "跳转失败");
        return BaseResult.successWithData(user);
    }
}
