package com.example.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.UserMapper;
import com.example.domain.User;
import com.example.service.IUserService;

/**
 * 用户信息Service业务层处理
 *
 * @author tong
 * @date 2026-05-08
 */
@Service
public class UserServiceImpl implements IUserService
{
    @Autowired
    private UserMapper userMapper;

    /**
     * 查询用户信息
     *
     * @param id 用户信息主键
     * @return 用户信息
     */
    @Override
    public User selectUserById(Long id)
    {
        return userMapper.selectUserById(id);
    }

    /**
     * 查询用户信息列表
     *
     * @param user 用户信息
     * @return 用户信息
     */
    @Override
    public List<User> selectUserList(User user)
    {
        return userMapper.selectUserList(user);
    }

    @Override
    public User selectUserByUserName(String username)
    {
        return userMapper.selectUserByUserName(username);
    }

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int insertUser(User user)
    {
        if (user.getPasswordHash() != null) {
            user.setPasswordHash(com.ruoyi.common.utils.SecurityUtils.encryptPassword(user.getPasswordHash()));
        }
        user.setCreatedAt(new java.util.Date());
        return userMapper.insertUser(user);
    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(User user)
    {
        if (user.getPasswordHash() != null && !user.getPasswordHash().isEmpty()) {
            user.setPasswordHash(com.ruoyi.common.utils.SecurityUtils.encryptPassword(user.getPasswordHash()));
        }
        user.setUpdatedAt(new java.util.Date());
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUserByIds(Long[] ids)
    {
        return userMapper.deleteUserByIds(ids);
    }

    /**
     * 删除用户信息信息
     *
     * @param id 用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUserById(Long id)
    {
        return userMapper.deleteUserById(id);
    }
}
