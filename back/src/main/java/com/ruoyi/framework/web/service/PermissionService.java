package com.ruoyi.framework.web.service;

import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * RuoYi首创 自定义权限实现，ss取自SecurityService首字母
 *
 * @author ruoyi
 */
@Service("ss")
public class PermissionService
{
    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission)
    {
        return SecurityUtils.hasPermi(permission);
    }

    /**
     * 判断用户是否拥有某个角色
     *
     * @param role 角色字符串
     * @return 用户是否具备某角色
     */
    public boolean hasRole(String role)
    {
        return SecurityUtils.hasRole(role);
    }
}
