package com.example.mapper;

import java.util.List;
import com.example.domain.SysConfig;

/**
 * 参数配置Mapper接口
 *
 * @author tong
 * @date 2026-05-08
 */
public interface SysConfigMapper
{
    /**
     * 查询参数配置
     *
     * @param id 参数配置主键
     * @return 参数配置
     */
    public SysConfig selectSysConfigById(Long id);

    /**
     * 根据键名查询参数配置
     */
    public SysConfig selectSysConfigByKey(String configKey);

    /**
     * 查询参数配置列表
     *
     * @param sysConfig 参数配置
     * @return 参数配置集合
     */
    public List<SysConfig> selectSysConfigList(SysConfig sysConfig);

    /**
     * 新增参数配置
     *
     * @param sysConfig 参数配置
     * @return 结果
     */
    public int insertSysConfig(SysConfig sysConfig);

    /**
     * 修改参数配置
     *
     * @param sysConfig 参数配置
     * @return 结果
     */
    public int updateSysConfig(SysConfig sysConfig);

    /**
     * 删除参数配置
     *
     * @param id 参数配置主键
     * @return 结果
     */
    public int deleteSysConfigById(Long id);

    /**
     * 批量删除参数配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysConfigByIds(Long[] ids);
}
