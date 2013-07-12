package com.djl.jcx.data.dao;

import com.djl.jcx.data.model.AdminModel;
import com.djl.jcx.data.model.SellingModel;
import com.djl.jcx.data.model.SuitModel;

import java.util.Date;
import java.util.List;

/**
 * User: Administrator
 * Date: 13-3-18
 * Time: 下午1:08
 */
public interface ISellingDao extends IBaseDao<SellingModel> {
    /** 根据管理员查询进存销记录*/
    public List<SellingModel> listAllByAdmin(AdminModel admin);

    /** 根据出入数量查询进存销记录*/
    public List<SellingModel> listAllBySuit(SuitModel suit);

    /** 根据动作查询进存销记录 */
    public List<SellingModel> listAllByAction(SellingModel.EAction action);

    /** 根据组合条件查询进存销记录 */
    public List<SellingModel> listAllByProperty(SellingModel.EAction action, AdminModel admin, int count, Date date,
                                               SuitModel suit);
}
