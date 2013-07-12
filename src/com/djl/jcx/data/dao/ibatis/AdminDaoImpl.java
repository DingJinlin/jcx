package com.djl.jcx.data.dao.ibatis;

import com.djl.jcx.data.dao.IAdminDao;
import com.djl.jcx.data.model.AdminModel;

import java.util.List;

/**
 * User: Administrator
 * Date: 13-2-19
 * Time: 下午3:17
 */
public class AdminDaoImpl extends BaseDaoImpl<AdminModel> implements IAdminDao {
    protected AdminDaoImpl() {
        super("AdminSQL");
    }

    @Override
    public AdminModel queryOneByName(String name) {
        AdminModel admin = new AdminModel();
        admin.setName(name);
        List<AdminModel> adminList =  listByModel(admin);
        if(!adminList.isEmpty()) {
            return adminList.get(0);
        } else {
            return null;
        }
    }
}
