package com.woody.fine.vo;

import java.io.Serializable;

public class DataTestVo implements Serializable {

    private static final long serialVersionUID = 5846785059792895580L;

    private Integer id;
    private String data;
    private String createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
