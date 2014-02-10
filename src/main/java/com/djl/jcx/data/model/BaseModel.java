package com.djl.jcx.data.model;

import java.io.Serializable;

/**
 * User: Administrator
 * Date: 13-3-18
 * Time: 上午9:33
 * 商品类别信息
 */
public abstract class BaseModel<M> implements Serializable {
    /** 主键 */
    protected Integer id = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 复制值
     */
    public abstract void copyValue(M model);
}
