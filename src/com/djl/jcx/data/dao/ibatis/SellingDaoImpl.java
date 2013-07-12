package com.djl.jcx.data.dao.ibatis;

import com.djl.jcx.data.dao.ISellingDao;
import com.djl.jcx.data.model.SellingModel;
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
public class SellingDaoImpl extends BaseDaoImpl<SellingModel> implements ISellingDao {
    protected SellingDaoImpl() {
        super("SellingSQL");
    }

    @Override
    public List<SellingModel> listByModel(SellingModel model) {
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
    public List<SellingModel> listAllByAdmin(AdminModel admin) {
        SellingModel sellingModel = new SellingModel();
        sellingModel.setAdmin(admin);
        return listByModel(sellingModel);
    }

    @Override
    public List<SellingModel> listAllBySuit(SuitModel suit) {
        SellingModel sellingModel = new SellingModel();
        sellingModel.setSuit(suit);
        return listByModel(sellingModel);
    }

    @Override
    public List<SellingModel> listAllByAction(SellingModel.EAction action) {
        SellingModel sellingModel = new SellingModel();
        sellingModel.setAction(action);
        return listByModel(sellingModel);
    }

    @Override
    public List<SellingModel> listAllByProperty(SellingModel.EAction action, AdminModel admin, int count, Date date, SuitModel suit) {
        SellingModel sellingModel = new SellingModel(action, admin, count, date, suit);
        return listByModel(sellingModel);
    }
}
