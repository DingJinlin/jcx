package com.djl.jcx.ui.table.container;

import com.djl.jcx.data.dao.IAccessDao;
import com.djl.jcx.data.dao.IDepositoryDao;
import com.djl.jcx.data.dao.ISellingDao;
import com.djl.jcx.data.model.AccessModel;
import com.djl.jcx.data.model.DepositoryModel;
import com.djl.jcx.data.model.SellingModel;
import com.spring.SpringContextHelperHandler;

/**
 * User: Administrator
 * Date: 13-3-22
 * Time: 上午9:30
 */
public class SellingContainerGetter extends AbstractContainerGetter<SellingModel, ISellingDao> {
    // 字段名
    public static final String H_NAME = "suitName";
    public static final String H_AMOUNT = "amount";
    public static final String H_COUNT = "count";
    public static final String H_PRICE = "sellingPrice";
    public static final String H_REDUCE_PRICE = "reducePrice";
    public static final String H_SUM = "sum";

    // 显示名
    public static final String V_NAME = "商品名";
    public static final String V_AMOUNT = "存量";
    public static final String V_COUNT = "数量";
    public static final String V_PRICE = "单价";
    public static final String V_REDUCE_PRICE = "减价";
    public static final String V_SUM = "金额";

    static ISellingDao dao;
    static IDepositoryDao depositoryDao;

    static {
        dao = SpringContextHelperHandler.getSch().getBean("sellingDao", ISellingDao.class);
        depositoryDao = SpringContextHelperHandler.getSch().getBean("depositoryDao", IDepositoryDao.class);
    }

    public SellingContainerGetter() {
        super(dao, SellingModel.class);
    }

    @Override
    @Deprecated
    public SellingModel searchItem(SellingModel model) {
        return null;
    }

    /**
     * 提交商品销售
     */
    public void submitCheckout() {
        for(SellingModel model : this.getContainer().getItemIds()) {
            dao.save(model);
            DepositoryContainerGetter depositoryContainerGetter = new DepositoryContainerGetter();
            DepositoryModel depositoryModel = new DepositoryModel(model.getCount(), model.getSuit());
            depositoryContainerGetter.subtractDepository(depositoryModel);
        }
    }

    @Override
    public Object[] getColumns() {
        return new Object[] {H_NAME, H_AMOUNT, H_COUNT, H_PRICE, H_REDUCE_PRICE,H_SUM};
    }
    @Override
    public String[] getColumnsHeaders() {
        return new String[]{V_NAME, V_AMOUNT, V_COUNT, V_PRICE, V_REDUCE_PRICE, V_SUM};
    }

    @Override
    public void fillContainerByName(String name) {
        return;
    }

    /**
     * 在container中增加一条记录
     * @param model
     */
    @Override
    public void addItem(SellingModel model) {
        DepositoryModel depositoryModel = depositoryDao.queryOneBySuit(model.getSuit());
        model.setAmount(depositoryModel.getAmount());
        container.addItem(model);
        containerCollection.add(model);
    }
}
