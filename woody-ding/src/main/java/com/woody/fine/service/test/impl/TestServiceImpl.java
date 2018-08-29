package com.woody.fine.service.test.impl;

import com.woody.fine.dao.test.TestDao;
import com.woody.fine.service.test.TestService;
import com.woody.fine.vo.DataTestVo;
import com.woody.framework.dynamicdatabase.DatabaseSourceEumn;
import com.woody.framework.dynamicdatabase.DynamicDataSourceHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao tDao;

    public String queryBookpriceByName(String name) {
        return tDao.queryPriceByName(name);
    }

    @Override
    public String createTable(String tableName) {

        int count = 0;
        try {
           count = tDao.selectTable(tableName);
        } catch (Exception e) {
            tDao.createTable(tableName);
        }


//        try {
//            tDao.createTable(tableName);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "fault";
//        }
        return "success";
    }

    @Override
    public String insertTestData(String data) {
        DynamicDataSourceHolder.setRouteKey(DatabaseSourceEumn.mysql_2.getVal());
        tDao.insertTestData(data);
        return "success";
    }

    @Override
    public List<DataTestVo> selectAllTestData() {
        DynamicDataSourceHolder.setRouteKey(DatabaseSourceEumn.mysql_2.getVal());
        return tDao.selectAllDataInfo();
    }
}
