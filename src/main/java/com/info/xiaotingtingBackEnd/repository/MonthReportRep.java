package com.info.xiaotingtingBackEnd.repository;

import com.info.xiaotingtingBackEnd.model.DayReport;
import com.info.xiaotingtingBackEnd.model.MonthReport;
import com.info.xiaotingtingBackEnd.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Copyright (c) 2018, Chestnut All rights reserved
 * Author: Chestnut
 * CreateTime：at 2018/4/3 09:28:29
 * Description：月报Repository
 * Email: xiaoting233zhang@126.com
 */
@Repository
public interface MonthReportRep extends BaseRepository<MonthReport, String> {

}
