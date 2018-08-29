package com.woody.fine.service.test;

import com.woody.fine.vo.DataTestVo;

import java.util.List;

public interface TestService {

    String queryBookpriceByName(String name);

    String createTable(String tableName);

    String insertTestData(String data);

    List<DataTestVo> selectAllTestData();
}
