package com.woody.fine.service.test.impl;

import com.woody.fine.dao.test.TestDao;
import com.woody.fine.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
