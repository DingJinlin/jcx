package com.djl.jcx.data.dao;

import com.djl.jcx.data.model.DepositoryModel;
import com.djl.jcx.data.model.FactoryModel;
import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.data.model.TypeModel;

import java.util.List;

/**
 * User: Administrator
 * Date: 13-3-18
 * Time: 下午1:08
 */
public interface IDepositoryDao extends IBaseDao<DepositoryModel> {

    /** 根据商品厂家查询该商品库存*/
    public List<DepositoryModel> listAllByFactory(FactoryModel factory);

    /** 根据商品分类查询该商品库存*/
    public List<DepositoryModel> listAllByType(TypeModel type);

    /** 根据商品花色查询该商品库存*/
    public DepositoryModel queryOneBySuit(SuitModel suit);

    /** 根据商品存量查询该商品库存*/
    public List<DepositoryModel> listAllByAmount(int amount);

    /** 根据商品存量查询大于该存量的商品*/
    public List<DepositoryModel> listAllByAmountGreater(int amount);

    /** 根据商品存量查询小于该存量的商品*/
    public List<DepositoryModel> listAllByAmountLess(int amount);

    /** 增加存量 */
    public DepositoryModel add(SuitModel suit, int amount);

    /** 减去存量 */
    public DepositoryModel subtract(SuitModel suit, int amount);
}
