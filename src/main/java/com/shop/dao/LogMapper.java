package com.shop.dao;

import com.shop.pojo.Log;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public  interface LogMapper {
    int LogInsert(Log log);
    int UpdateLog();
    Log SelectLog(Integer log_orderid);

}
