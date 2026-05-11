package com.example.mapper;

import java.util.List;
import com.example.domain.UserBrowse;

/**
 * 浏览历史Mapper接口
 * 
 * @author tong
 * @date 2026-05-08
 */
public interface UserBrowseMapper 
{
    /**
     * 查询浏览历史
     * 
     * @param id 浏览历史主键
     * @return 浏览历史
     */
    public UserBrowse selectUserBrowseById(Long id);

    /**
     * 查询浏览历史列表
     * 
     * @param userBrowse 浏览历史
     * @return 浏览历史集合
     */
    public List<UserBrowse> selectUserBrowseList(UserBrowse userBrowse);

    /**
     * 新增浏览历史
     * 
     * @param userBrowse 浏览历史
     * @return 结果
     */
    public int insertUserBrowse(UserBrowse userBrowse);

    /**
     * 修改浏览历史
     * 
     * @param userBrowse 浏览历史
     * @return 结果
     */
    public int updateUserBrowse(UserBrowse userBrowse);

    /**
     * 删除浏览历史
     * 
     * @param id 浏览历史主键
     * @return 结果
     */
    public int deleteUserBrowseById(Long id);

    /**
     * 批量删除浏览历史
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserBrowseByIds(Long[] ids);
}
