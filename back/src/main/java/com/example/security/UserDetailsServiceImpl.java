package com.example.security;

import com.example.domain.User;
import com.example.service.IUserService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 身份验证 业务层处理
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new DisabledException("对不起，您的账号已被停用");
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(User user) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(user.getId());
        sysUser.setUserName(user.getUsername());
        sysUser.setNickName(user.getNickname());
        sysUser.setPassword(user.getPasswordHash());
        sysUser.setStatus(String.valueOf(user.getStatus()));
        sysUser.setRoleId(user.getRoleId());

        Set<String> permissions = new HashSet<>();
        if (user.getRoleId() != null && user.getRoleId() == 1L) {
            permissions.add("ROLE_ADMIN");          // 匹配 hasRole('ADMIN')
            permissions.add("admin");               // 兼容其它地方直接判断字符串
            permissions.add("*:*:*");               // 匹配 @ss.hasPermi
        } else {
            permissions.add("ROLE_USER");           // 普通用户角色
            permissions.add("common");
        }

        return new LoginUser(user.getId(), null, sysUser, permissions);
    }

}
