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
}
