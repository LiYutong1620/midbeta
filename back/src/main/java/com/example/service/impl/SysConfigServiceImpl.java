package com.example.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.SysConfigMapper;
import com.example.domain.SysConfig;
import com.example.service.ISysConfigService;

/**
 * 参数配置Service业务层处理
 *
 * @author tong
 * @date 2026-05-08
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService
{
    @Autowired
    private SysConfigMapper sysConfigMapper;

    /**
     * 查询参数配置
     *
     * @param id 参数配置主键
     * @return 参数配置
     */
    @Override
    public SysConfig selectSysConfigById(Long id)
    {
        return sysConfigMapper.selectSysConfigById(id);
    }

    @Override
    public String selectConfigByKey(String configKey)
    {
        SysConfig config = sysConfigMapper.selectSysConfigByKey(configKey);
        return config != null ? config.getConfigValue() : "";
    }

    /**
     * 查询参数配置列表
     *
     * @param sysConfig 参数配置
     * @return 参数配置
     */
    @Override
    public List<SysConfig> selectSysConfigList(SysConfig sysConfig)
    {
        return sysConfigMapper.selectSysConfigList(sysConfig);
    }

    /**
     * 新增参数配置
     *
     * @param sysConfig 参数配置
     * @return 结果
     */
    @Override
    public int insertSysConfig(SysConfig sysConfig)
    {
        return sysConfigMapper.insertSysConfig(sysConfig);
    }

    /**
     * 修改参数配置
     *
     * @param sysConfig 参数配置
     * @return 结果
     */
    @Override
    public int updateSysConfig(SysConfig sysConfig)
    {
        return sysConfigMapper.updateSysConfig(sysConfig);
    }

    /**
     * 批量删除参数配置
     *
     * @param ids 需要删除的参数配置主键
     * @return 结果
     */
    @Override
    public int deleteSysConfigByIds(Long[] ids)
    {
        return sysConfigMapper.deleteSysConfigByIds(ids);
    }

    /**
     * 删除参数配置信息
     *
     * @param id 参数配置主键
     * @return 结果
     */
    @Override
    public int deleteSysConfigById(Long id)
    {
        return sysConfigMapper.deleteSysConfigById(id);
    }
}
