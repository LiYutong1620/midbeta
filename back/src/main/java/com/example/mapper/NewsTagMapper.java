package com.example.mapper;

import java.util.List;
import com.example.domain.NewsTag;

/**
 * 标签管理Mapper接口
 * 
 * @author tong
 * @date 2026-05-09
 */
public interface NewsTagMapper 
{
    /**
     * 查询标签管理
     * 
     * @param id 标签管理主键
     * @return 标签管理
     */
    public NewsTag selectNewsTagById(Long id);

    /**
     * 查询标签管理列表
     * 
     * @param newsTag 标签管理
     * @return 标签管理集合
     */
    public List<NewsTag> selectNewsTagList(NewsTag newsTag);

    /**
     * 新增标签管理
     * 
     * @param newsTag 标签管理
     * @return 结果
     */
    public int insertNewsTag(NewsTag newsTag);

    /**
     * 修改标签管理
     * 
     * @param newsTag 标签管理
     * @return 结果
     */
    public int updateNewsTag(NewsTag newsTag);

    /**
     * 删除标签管理
     * 
     * @param id 标签管理主键
     * @return 结果
     */
    public int deleteNewsTagById(Long id);

    /**
     * 批量删除标签管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNewsTagByIds(Long[] ids);
}
