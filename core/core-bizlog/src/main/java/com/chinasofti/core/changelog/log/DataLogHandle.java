package com.chinasofti.core.changelog.log;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;

import com.chinasofti.core.changelog.annotation.DataChangeLog;
import com.chinasofti.core.changelog.handle.BaseDataLog;
import com.chinasofti.core.changelog.handle.DataUpdateInterceptor;
import com.chinasofti.core.changelog.handle.LogData;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 数据日志处理
 * </p>
 */
@Slf4j
@Component
public class DataLogHandle extends BaseDataLog {

    @Override
    public void setting() {

    }

    @Override
    public void change(DataChangeLog dataLog, LogData data) {
        if (CollUtil.isEmpty(data.getDataChanges())) {
            return;
        }
        // 存库
        log.debug("存库成功：" + data.getLogStr());
    }

}
