package com.example.controller;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.domain.User;
import com.example.service.IUserService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录验证
 *
 * @author tong
 */
@Tag(name = "登录注册", description = "用户登录、注册、注销等操作")
@RestController
public class LoginController
{
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private com.example.security.TokenService tokenService;

    @Operation(summary = "未登录提示", description = "Spring Security 未分配权限时的默认跳转")
    @GetMapping("/login")
    public AjaxResult loginPage() {
        return AjaxResult.error("请登录");
    }

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        String username = loginBody.getUsername();
        String password = loginBody.getPassword();

        // 1. 判断是否已锁定
        if (redisCache.hasKey(CacheConstants.PWD_LOCK_KEY + username)) {
            log.warn("用户 [{}] 处于锁定状态，拒绝登录尝试", username);
            return AjaxResult.error("账号已锁定，请10分钟后重试或联系管理员解锁");
        }

        try {
            // 2. 使用 AuthenticationManager 进行校验
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 3. 登录成功，清除锁定计数
            log.info("用户 [{}] 登录成功，清除锁定计数", username);
            redisCache.deleteObject(CacheConstants.PWD_ERR_CNT_KEY + username);
            redisCache.deleteObject(CacheConstants.PWD_LOCK_KEY + username);

            // 4. 获取登录用户
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();

            // 5. 生成 Token
            String token = tokenService.createToken(loginUser);

            // 6. 返回结果
            Map<String, Object> ajax = new HashMap<>();
            ajax.put("token", token);
            ajax.put("user", loginUser.getUser());
            return AjaxResult.success(ajax);
        } catch (org.springframework.security.core.AuthenticationException e) {
            // 8. 登录失败，增加重试计数 (仅针对密码错误等认证异常)
            if (e instanceof BadCredentialsException) {
                Integer retries = redisCache.getCacheObject(CacheConstants.PWD_ERR_CNT_KEY + username);
                if (retries == null) {
                    retries = 0;
                }
                retries++;
                log.warn("用户 [{}] 密码错误，当前重试次数: {}", username, retries);
                redisCache.setCacheObject(CacheConstants.PWD_ERR_CNT_KEY + username, retries, 10, TimeUnit.MINUTES);

                if (retries >= 5) {
                    // 锁定账号10分钟
                    log.error("用户 [{}] 连续失败5次，执行锁定 10 分钟", username);
                    redisCache.setCacheObject(CacheConstants.PWD_LOCK_KEY + username, "lock", 10, TimeUnit.MINUTES);
                    return AjaxResult.error("账号检测到多次登录失败已锁定，请10分钟后重试或联系管理员解锁");
                }
                String msg = "账号或密码错误，您还有 " + (5 - retries) + " 次尝试机会";
                return AjaxResult.error(msg);
            }

            log.error("用户 [{}] 登录异常: ", username, e);
            return AjaxResult.error("登录失败: " + e.getMessage());
        } catch (Exception e) {
            // 系统级别异常
            log.error("用户 [{}] 系统登录异常: ", username, e);
            return AjaxResult.error("系统故障: " + e.getMessage());
        }
    }

    /**
     * 注册方法
     */
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public AjaxResult register(@RequestBody User user)
    {
        if (user.getUsername() == null || user.getPasswordHash() == null) {
            return AjaxResult.error("用户名和密码不能为空");
        }

        // 校验用户名是否存在
        User existUser = userService.selectUserByUserName(user.getUsername());
        if (existUser != null) {
            return AjaxResult.error("用户名已存在");
        }

        // 设置默认角色 (假设 2 是普通用户)
        user.setRoleId(2L);
        user.setStatus(1); // 正常 (1:正常, 0:停用)
        user.setIsDeleted(0);

        int row = userService.insertUser(user);
        return row > 0 ? AjaxResult.success("注册成功") : AjaxResult.error("注册失败");
    }
}
