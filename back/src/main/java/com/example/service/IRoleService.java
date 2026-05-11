package com.example.service;

import java.util.List;
import com.example.domain.Role;

/**
 * 角色信息Service接口
 * 
 * @author tong
 * @date 2026-05-08
 */
public interface IRoleService 
{
    /**
     * 查询角色信息
     * 
     * @param id 角色信息主键
     * @return 角色信息
     */
    public Role selectRoleById(Long id);

    /**
     * 查询角色信息列表
     * 
     * @param role 角色信息
     * @return 角色信息集合
     */
    public List<Role> selectRoleList(Role role);

    /**
     * 新增角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    public int insertRole(Role role);

    /**
     * 修改角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    public int updateRole(Role role);

    /**
     * 批量删除角色信息
     * 
     * @param ids 需要删除的角色信息主键集合
     * @return 结果
     */
    public int deleteRoleByIds(Long[] ids);

    /**
     * 删除角色信息信息
     * 
     * @param id 角色信息主键
     * @return 结果
     */
    public int deleteRoleById(Long id);
}
