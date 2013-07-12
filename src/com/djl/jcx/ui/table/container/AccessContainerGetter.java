package com.djl.jcx.ui.table.container;

import com.djl.jcx.data.dao.IAccessDao;
import com.djl.jcx.data.dao.IDepositoryDao;
import com.djl.jcx.data.model.AccessModel;
import com.djl.jcx.data.model.DepositoryModel;
import com.djl.jcx.data.model.SuitModel;
import com.spring.SpringContextHelperHandler;
import com.vaadin.ui.Table;

import java.util.Date;
import java.util.List;

/**
 * User: Administrator
 * Date: 13-3-22
 * Time: 上午9:30
 */
public class AccessContainerGetter extends AbstractContainerGetter<AccessModel, IAccessDao> {
    // 字段名
    public static final String H_ID = "access_id";
    public static final String H_SUIT_NAME = "suitName";
    public static final String H_ACTION = "action";
    public static final String H_DATE = "date";
    public static final String H_ADMIN_NAME = "adminName";
    public static final String H_COUNT = "count";
    public static final String H_PRICE = "price";
    public static final String H_SELLING_PRICE = "sellingPrice";

    // 显示名
    public static final String V_ID = "ID";
    public static final String V_SUIT_NAME = "商品名";
    public static final String V_ACTION = "操作";
    public static final String V_DATE = "日期";
    public static final String V_ADMIN_NAME = "管理员";
    public static final String V_COUNT = "数量";
    public static final String V_PRICE = "售价";
    public static final String V_SELLING_PRICE = "标价";

    static IAccessDao dao;

    static {
        dao = SpringContextHelperHandler.getSch().getBean("accessDao", IAccessDao.class);
    }

    public AccessContainerGetter() {
        super(dao, AccessModel.class);
    }

    @Override
    @Deprecated
    public AccessModel searchItem(AccessModel model) {
        return null;
    }

    @Override
    @Deprecated
    public void fillContainerByName(String name) {
        //
    }

    // 按条件查询
    public void fillContainerByParam(Integer suitId, Date startDate, Date endData) {
        SuitModel suitModel = null;
        if(suitId != null) {
            suitModel = new SuitModel(suitId);
        }
        AccessModel accessModel = new AccessModel(AccessModel.EAction.SOLD, startDate, endData, suitModel);
        List<AccessModel> accessModels = dao.listByModel(accessModel);
        fillContainer(accessModels);
    }

    @Override
    public Object[] getColumns() {
        return new Object[] {H_SUIT_NAME, H_DATE, H_COUNT, H_PRICE, H_SELLING_PRICE};
    }
    @Override
    public String[] getColumnsHeaders() {
        return new String[] {V_SUIT_NAME, V_DATE, V_COUNT, V_PRICE, V_SELLING_PRICE};
    }

    @Override
    public String[] getColumnAlignments() {
        return new String[] {Table.ALIGN_LEFT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT,
                Table.ALIGN_RIGHT};
    }
}
