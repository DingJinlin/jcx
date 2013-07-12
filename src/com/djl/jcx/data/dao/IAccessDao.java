package com.djl.jcx.data.dao;

import com.djl.jcx.data.model.*;

import java.util.Date;
import java.util.List;

/**
 * User: Administrator
 * Date: 13-3-18
 * Time: 下午1:08
 */
public interface IAccessDao extends IBaseDao<AccessModel> {
    /** 根据管理员查询进存销记录*/
    public List<AccessModel> listAllByAdmin(AdminModel admin);

    /** 根据出入数量查询进存销记录*/
    public List<AccessModel> listAllBySuit(SuitModel suit);

    /** 根据动作查询进存销记录 */
    public List<AccessModel> listAllByAction(AccessModel.EAction action);

    /** 根据组合条件查询进存销记录 */
    public List<AccessModel> listAllByProperty(AccessModel.EAction action, AdminModel admin, int count, Date date,
                                               SuitModel suit);
}
