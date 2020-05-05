package org.iot.iotsso.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.iot.iotcommon.model.User;
import org.iot.iotsso.mapper.UserMapper;
import org.iot.iotsso.model.UserLogin;
import org.iot.iotsso.service.LoginService;
import org.iot.iotsso.service.consumer.SsoRedisService;
import org.iot.iotcommon.utils.RsaUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private SsoRedisService ssoRedisService;

    private final static String TOKEN_PREFIX = "token-";

    private final static long DURATION = 60 * 30;

    @Override
    public String getPublicKey(String account) {
        Map<String, String> keyMap = RsaUtil.getRsaMap(1024, "RSA", account);
        return keyMap.get(RsaUtil.PublicKeyPrefix + account);
    }

    private String getPassword(String account, String password) {
        Map<String, String> keyMap = RsaUtil.getRsaMap(1024, "RSA", account);
        String privateKey = keyMap.get(RsaUtil.PrivateKeyPrefix + account);
        String decode = null;
        try {
            decode = RsaUtil.decrypt(password, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("还原后的字符串为:" + decode);
        return decode;
    }

    private String getPasswordFromRedis(String account, String password) {
        String privateKey = ssoRedisService.get(RsaUtil.PrivateKeyPrefix + account);
        if (privateKey == null) return null;
        try {
            String decrypt = RsaUtil.decrypt(password, privateKey);
            System.out.println("getPasswordFromRedis：" + decrypt);
            return decrypt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getToken(String passwordFromRedis, String account) {
        String token = null;
        if (passwordFromRedis == null || "".equals(passwordFromRedis)) {
            // token加密后写入缓存
            token = UUID.randomUUID().toString().replace("-", "");
            String name = account + "-" + token;
            Map<String, String> map = RsaUtil.getRsaMap(1024, "RSA", name);
            String privateKey = RsaUtil.PrivateKeyPrefix + name;
            token = map.get(privateKey);
            ssoRedisService.put(TOKEN_PREFIX + account, token, DURATION);
        }
        return token;
    }

    @Override
    public UserLogin login(User user, HttpServletRequest request) {
        String account = user.getAccount();
        String password = user.getPassword();
        UserLogin userLogin;
        User data;
        // 获取缓存
        String json = ssoRedisService.get(TOKEN_PREFIX + account);
        if (json == null) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("account", account);
            data = userMapper.selectOne(wrapper);
            if (data == null) return null;
            // 比对账户和密码
            if (!account.equals(data.getAccount())) return null;
            String userPwd = getPassword(account, password);
            String passwordFromRedis = getPasswordFromRedis(account, data.getPassword());
            String token = getToken(passwordFromRedis, account);
            if (token == null) return null;
            userLogin = new UserLogin(token);
        } else {
            //
            data = getUserByToken(json);
            userLogin = new UserLogin(json);
        }
        userLogin.setAccount(data.getAccount());
        userLogin.setCreateTime(data.getCreateTime());
        userLogin.setEmail(data.getEmail());
        userLogin.setEnabled(data.getEnabled());
        userLogin.setId(data.getId());
        userLogin.setName(data.getName());
        userLogin.setNumber(data.getNumber());
        userLogin.setOrgId(data.getOrgId());
        userLogin.setPassword(data.getPassword());
        userLogin.setUpdateTime(data.getUpdateTime());
        userLogin.setPrefix(data.getPrefix());
        return userLogin;
    }

    @Override
    public User jump(String token) {
//        String s = ssoRedisService.get(TOKEN_PREFIX + account);
//        if (s == null || "".equals(s)) {
//            return null;
//        }
//        return getUser(account);
        return null;
    }

    @Override
    public boolean logout(HttpServletRequest request) {
        String token = request.getHeaders("token").nextElement();
        System.out.println("token：" + token);
        User user = getUserByToken(token);
        if (user == null) return false;
        ssoRedisService.put(TOKEN_PREFIX + user.getAccount(), "", 0);
        return true;
    }

    private boolean hasToken(String token) {
        return ssoRedisService.hasToken(token);
    }

    private User getUserByToken(String token) {
        return ssoRedisService.getUserByToken(token);
    }
}
