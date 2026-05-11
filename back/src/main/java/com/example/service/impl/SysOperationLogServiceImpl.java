package com.example.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.SysOperationLogMapper;
import com.example.domain.SysOperationLog;
import com.example.service.ISysOperationLogService;

/**
 * 操作日志Service业务层处理
 * 
 * @author tong
 * @date 2026-05-08
 */
@Service
public class SysOperationLogServiceImpl implements ISysOperationLogService 
{
    @Autowired
    private SysOperationLogMapper sysOperationLogMapper;

    /**
     * 查询操作日志
     * 
     * @param id 操作日志主键
     * @return 操作日志
     */
    @Override
    public SysOperationLog selectSysOperationLogById(Long id)
    {
        return sysOperationLogMapper.selectSysOperationLogById(id);
    }

    /**
     * 查询操作日志列表
     * 
     * @param sysOperationLog 操作日志
     * @return 操作日志
     */
    @Override
    public List<SysOperationLog> selectSysOperationLogList(SysOperationLog sysOperationLog)
    {
        return sysOperationLogMapper.selectSysOperationLogList(sysOperationLog);
    }

    /**
     * 新增操作日志
     * 
     * @param sysOperationLog 操作日志
     * @return 结果
     */
    @Override
    public int insertSysOperationLog(SysOperationLog sysOperationLog)
    {
        return sysOperationLogMapper.insertSysOperationLog(sysOperationLog);
    }

    /**
     * 修改操作日志
     * 
     * @param sysOperationLog 操作日志
     * @return 结果
     */
    @Override
    public int updateSysOperationLog(SysOperationLog sysOperationLog)
    {
        return sysOperationLogMapper.updateSysOperationLog(sysOperationLog);
    }

    /**
     * 批量删除操作日志
     * 
     * @param ids 需要删除的操作日志主键
     * @return 结果
     */
    @Override
    public int deleteSysOperationLogByIds(Long[] ids)
    {
        return sysOperationLogMapper.deleteSysOperationLogByIds(ids);
    }

    /**
     * 删除操作日志信息
     * 
     * @param id 操作日志主键
     * @return 结果
     */
    @Override
    public int deleteSysOperationLogById(Long id)
    {
        return sysOperationLogMapper.deleteSysOperationLogById(id);
    }
}
