package com.woody.fine.dao.test;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDao {

    String queryPriceByName(@Param("name") String name);

    void createTable(@Param("tableName") String tableName);

    int selectTable(@Param("tableName")String tableName);
}
