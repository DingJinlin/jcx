package com.djl.jcx.data.dao;

import com.djl.jcx.data.model.FactoryModel;

import java.util.List;

/**
 * User: Administrator
 * Date: 13-3-18
 * Time: 下午1:08
 */
public interface IFactoryDao extends IBaseDao<FactoryModel> {

    /**
     * 按供应商名查询
     * @param name 供应商名
     * @return
     */
    List<FactoryModel> queryByName(String name);

    /**
     * 按供应商联系人名查询
     * @param contactName 联系人名
     * @return
     */
    List<FactoryModel> queryByContact(String contactName);
}
