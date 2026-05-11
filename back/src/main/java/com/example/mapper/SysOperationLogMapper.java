package com.example.mapper;

import java.util.List;
import com.example.domain.SysOperationLog;

/**
 * 操作日志Mapper接口
 * 
 * @author tong
 * @date 2026-05-08
 */
public interface SysOperationLogMapper 
{
    /**
     * 查询操作日志
     * 
     * @param id 操作日志主键
     * @return 操作日志
     */
    public SysOperationLog selectSysOperationLogById(Long id);

    /**
     * 查询操作日志列表
     * 
     * @param sysOperationLog 操作日志
     * @return 操作日志集合
     */
    public List<SysOperationLog> selectSysOperationLogList(SysOperationLog sysOperationLog);

    /**
     * 新增操作日志
     * 
     * @param sysOperationLog 操作日志
     * @return 结果
     */
    public int insertSysOperationLog(SysOperationLog sysOperationLog);

    /**
     * 修改操作日志
     * 
     * @param sysOperationLog 操作日志
     * @return 结果
     */
    public int updateSysOperationLog(SysOperationLog sysOperationLog);

    /**
     * 删除操作日志
     * 
     * @param id 操作日志主键
     * @return 结果
     */
    public int deleteSysOperationLogById(Long id);

    /**
     * 批量删除操作日志
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysOperationLogByIds(Long[] ids);
}
