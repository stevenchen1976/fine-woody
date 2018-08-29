package com.woody.fine.dao.test;

import com.woody.fine.vo.DataTestVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDao {

    String queryPriceByName(@Param("name") String name);

    void createTable(@Param("tableName") String tableName);

    int selectTable(@Param("tableName")String tableName);

    String insertTestData(@Param("data") String data);

    List<DataTestVo> selectAllDataInfo();
}
