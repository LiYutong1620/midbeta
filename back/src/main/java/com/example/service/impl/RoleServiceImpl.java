package com.example.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.RoleMapper;
import com.example.domain.Role;
import com.example.service.IRoleService;

/**
 * 角色信息Service业务层处理
 * 
 * @author tong
 * @date 2026-05-08
 */
@Service
public class RoleServiceImpl implements IRoleService 
{
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询角色信息
     * 
     * @param id 角色信息主键
     * @return 角色信息
     */
    @Override
    public Role selectRoleById(Long id)
    {
        return roleMapper.selectRoleById(id);
    }

    /**
     * 查询角色信息列表
     * 
     * @param role 角色信息
     * @return 角色信息
     */
    @Override
    public List<Role> selectRoleList(Role role)
    {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 新增角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int insertRole(Role role)
    {
        return roleMapper.insertRole(role);
    }

    /**
     * 修改角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int updateRole(Role role)
    {
        return roleMapper.updateRole(role);
    }

    /**
     * 批量删除角色信息
     * 
     * @param ids 需要删除的角色信息主键
     * @return 结果
     */
    @Override
    public int deleteRoleByIds(Long[] ids)
    {
        return roleMapper.deleteRoleByIds(ids);
    }

    /**
     * 删除角色信息信息
     * 
     * @param id 角色信息主键
     * @return 结果
     */
    @Override
    public int deleteRoleById(Long id)
    {
        return roleMapper.deleteRoleById(id);
    }
}
