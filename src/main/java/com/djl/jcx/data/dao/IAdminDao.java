package com.djl.jcx.data.dao;

import com.djl.jcx.data.model.AdminModel;

/**
 * User: Administrator
 * Date: 13-3-18
 * Time: 下午1:08
 */
public interface IAdminDao extends IBaseDao<AdminModel> {
    public AdminModel queryOneByName(String name);
}
