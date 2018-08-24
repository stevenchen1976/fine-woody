package com.woody.fine.dao.test;

import org.apache.ibatis.annotations.Param;

public interface TestDao {

    String queryPriceByName(@Param("name") String name);
}
