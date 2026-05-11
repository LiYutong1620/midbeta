package com.example.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.UserBrowseMapper;
import com.example.domain.UserBrowse;
import com.example.service.IUserBrowseService;
import com.ruoyi.common.core.redis.RedisCache;

/**
 * 浏览历史Service业务层处理
 *
 * @author tong
 * @date 2026-05-08
 */
@Service
public class UserBrowseServiceImpl implements IUserBrowseService
{
    @Autowired
    private UserBrowseMapper userBrowseMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询浏览历史
     *
     * @param id 浏览历史主键
     * @return 浏览历史
     */
    @Override
    public UserBrowse selectUserBrowseById(Long id)
    {
        return userBrowseMapper.selectUserBrowseById(id);
    }

    /**
     * 查询浏览历史列表
     *
     * @param userBrowse 浏览历史
     * @return 浏览历史
     */
    @Override
    public List<UserBrowse> selectUserBrowseList(UserBrowse userBrowse)
    {
        return userBrowseMapper.selectUserBrowseList(userBrowse);
    }

    /**
     * 新增浏览历史
     *
     * @param userBrowse 浏览历史
     * @return 结果
     */
    @Override
    public int insertUserBrowse(UserBrowse userBrowse)
    {
        int row = userBrowseMapper.insertUserBrowse(userBrowse);
        if (row > 0 && userBrowse.getUserId() != null && userBrowse.getNewsId() != null) {
            String key = "user:history:" + userBrowse.getUserId();
            // 使用当前时间戳作为分数
            redisCache.redisTemplate.opsForZSet().add(key, userBrowse.getNewsId().toString(), System.currentTimeMillis());
            // 保持最近的100条记录
            redisCache.redisTemplate.opsForZSet().removeRange(key, 0, -101);
        }
        return row;
    }

    /**
     * 修改浏览历史
     *
     * @param userBrowse 浏览历史
     * @return 结果
     */
    @Override
    public int updateUserBrowse(UserBrowse userBrowse)
    {
        return userBrowseMapper.updateUserBrowse(userBrowse);
    }

    /**
     * 批量删除浏览历史
     *
     * @param ids 需要删除的浏览历史主键
     * @return 结果
     */
    @Override
    public int deleteUserBrowseByIds(Long[] ids)
    {
        return userBrowseMapper.deleteUserBrowseByIds(ids);
    }

    /**
     * 删除浏览历史信息
     *
     * @param id 浏览历史主键
     * @return 结果
     */
    @Override
    public int deleteUserBrowseById(Long id)
    {
        return userBrowseMapper.deleteUserBrowseById(id);
    }
}
