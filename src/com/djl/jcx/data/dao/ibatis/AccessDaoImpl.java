package com.djl.jcx.data.dao.ibatis;

import com.djl.jcx.data.dao.IAccessDao;
import com.djl.jcx.data.model.AccessModel;
import com.djl.jcx.data.model.AdminModel;
import com.djl.jcx.data.model.SuitModel;
import com.raycom.support.convert.DateConvert;

import java.util.Date;
import java.util.List;

/**
 * User: Administrator
 * Date: 13-2-19
 * Time: 下午3:17
 */
public class AccessDaoImpl extends BaseDaoImpl<AccessModel> implements IAccessDao {
    protected AccessDaoImpl() {
        super("AccessSQL");
    }

    @Override
    public List<AccessModel> listByModel(AccessModel model) {
        Date tempDate;
        Date beforeDate = model.getDateCondition1();
        Date afterDate = model.getDateCondition2();

        if(beforeDate != null) {
            if(afterDate != null) {
                if(DateConvert.isExpired(afterDate, beforeDate)) {
                    tempDate = (Date)beforeDate.clone();
                    beforeDate = afterDate;
                    afterDate = tempDate;
                }
            } else {
                afterDate = (Date)beforeDate.clone();
            }

            afterDate = DateConvert.addDay(afterDate, 1);
            model.setDateCondition1(beforeDate);
            model.setDateCondition2(afterDate);
        }

        return super.listByModel(model);
    }

    @Override
    public List<AccessModel> listAllByAdmin(AdminModel admin) {
        AccessModel access = new AccessModel();
        access.setAdmin(admin);
        return listByModel(access);
    }

    @Override
    public List<AccessModel> listAllBySuit(SuitModel suit) {
        AccessModel access = new AccessModel();
        access.setSuit(suit);
        return listByModel(access);
    }

    @Override
    public List<AccessModel> listAllByAction(AccessModel.EAction action) {
        AccessModel access = new AccessModel();
        access.setAction(action.value());
        return listByModel(access);
    }

    @Override
    public List<AccessModel> listAllByProperty(AccessModel.EAction action, AdminModel admin, int count, Date date, SuitModel suit) {
        AccessModel access = new AccessModel(action, admin, count, date, suit);
        return listByModel(access);
    }
}
