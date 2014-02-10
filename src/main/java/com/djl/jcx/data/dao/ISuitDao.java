package com.djl.jcx.data.dao;

import com.djl.jcx.data.model.SuitModel;

import java.util.List;

/**
 * User: Administrator
 * Date: 13-3-18
 * Time: 下午1:08
 */
public interface ISuitDao extends IBaseDao<SuitModel> {
    List<SuitModel> queryByName(String name);
    List<SuitModel> queryByTypeId(int typeId);
    List<SuitModel> queryByFactoryId(int factoryId);
    SuitModel queryByMaxId();

    /**
     * 按条码查询商品
     * @param Code
     * @return
     */
    List<SuitModel> queryByCode(String Code);

    /**
     *
     * @return 返回所有包含存量的商品
     */
    public List<SuitModel> listAllAmount();
}
