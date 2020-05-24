package com.shop.service.impl;

import com.shop.dao.LogMapper;
import com.shop.pojo.Log;
import com.shop.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;
    @Override
    public int LogInsert(Log log) {
        return logMapper.LogInsert(log);
    }

    @Override
    public int UpdateLog() {
        return logMapper.UpdateLog();
    }

    @Override
    public Log SelectLog(Integer log_orderid) {
        return logMapper.SelectLog(log_orderid);
    }
}
