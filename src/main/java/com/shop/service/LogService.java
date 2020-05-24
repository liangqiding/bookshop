package com.shop.service;

import com.shop.pojo.Log;

public interface LogService {
    int LogInsert(Log log);
    int UpdateLog();
    Log SelectLog(Integer log_orderid);

}
