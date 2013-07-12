package com.djl.jcx.ui.table.container;

import com.djl.jcx.data.dao.IDepositoryDao;
import com.djl.jcx.data.model.DepositoryModel;
import com.spring.SpringContextHelperHandler;
import com.vaadin.ui.Table;

/**
 * User: Administrator
 * Date: 13-3-22
 * Time: 上午9:30
 */
public class DepositoryAddContainerGetter extends AbstractContainerGetter<DepositoryModel, IDepositoryDao> {
    // 字段名
    public static final String H_ID = "id";
    public static final String H_NAME = "name";
    public static final String H_INTERNATIONAL_CODE = "internationalCode";
    public static final String H_TYPE = "type";
    public static final String H_FACTORY = "factory";
    public static final String H_STANDARD = "standard";
    public static final String H_UNIT = "unit";
    public static final String H_AMOUNT = "amount";
    public static final String H_COMMENT = "comment";

    // 显示名
    public static final String V_NAME = "Name";
    public static final String V_INTERNATIONAL_CODE = "International code";
    public static final String V_TYPE = "Type";
    public static final String V_FACTORY = "Factory";
    public static final String V_STANDARD = "Standard";
    public static final String V_AMOUNT = "Amount";
    public static final String V_UNIT = "Unit";
    public static final String V_COMMENT = "Comment";

    static IDepositoryDao dao;

    static {
        dao = SpringContextHelperHandler.getSch().getBean("depositoryDao", IDepositoryDao.class);
    }

    public DepositoryAddContainerGetter() {
        super(dao, DepositoryModel.class);
    }

    @Override
    @Deprecated
    public DepositoryModel searchItem(DepositoryModel model) {
        return null;
    }

    /**
     * 提交商品存量增加
     */
    public void submitDepository() {
        for(DepositoryModel model : containerCollection) {
//            dao.add(model.getSuit(), model.getAmount());
            DepositoryContainerGetter depositoryContainerGetter = new DepositoryContainerGetter();
            depositoryContainerGetter.addDepository(model);
        }
    }

    @Override
    public Object[] getColumns() {
        return new Object[] {H_NAME, H_INTERNATIONAL_CODE, H_TYPE, H_FACTORY, H_STANDARD, H_UNIT, H_AMOUNT};
    }
    @Override
    public String[] getColumnsHeaders() {
        return new String[]{V_NAME, V_INTERNATIONAL_CODE, V_TYPE, V_FACTORY, V_STANDARD, V_UNIT, H_AMOUNT};
    }

    @Override
    public String[] getColumnAlignments() {
        return new String[]{ Table.ALIGN_LEFT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT,
                Table.ALIGN_RIGHT, Table.ALIGN_RIGHT, Table.ALIGN_RIGHT
        };
    }

    @Override
    public void fillContainerByName(String name) {
        return;
    }
}
