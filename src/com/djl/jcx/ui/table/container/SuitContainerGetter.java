package com.djl.jcx.ui.table.container;

import com.djl.jcx.data.dao.IDepositoryDao;
import com.djl.jcx.data.dao.ISuitDao;
import com.djl.jcx.data.model.DepositoryModel;
import com.djl.jcx.data.model.SuitModel;
import com.spring.SpringContextHelperHandler;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * User: Administrator
 * Date: 13-3-22
 * Time: 上午9:30
 */
public class SuitContainerGetter extends AbstractContainerGetter<SuitModel, ISuitDao> {
    // 字段名
    public static final String H_ID = "id";
    public static final String H_NAME = "name";
    public static final String H_VIEW_NAME = "viewName";
    public static final String H_INTERNATIONAL_CODE = "internationalCode";
    public static final String H_TYPE = "type";
    public static final String H_FACTORY = "factory";
    public static final String H_STANDARD = "standard";
    public static final String H_UNIT = "unit";
    public static final String H_PURCHASE_PRICE = "purchasePrice";
    public static final String H_SELLING_PRICE = "sellingPrice";
    public static final String H_COMMENT = "comment";

    // 显示名
    public static final String V_NAME = "商品名";
    public static final String V_VIEW_NAME = "显示名";
    public static final String V_INTERNATIONAL_CODE = "条码";
    public static final String V_TYPE = "类型";
    public static final String V_FACTORY = "厂家";
    public static final String V_STANDARD = "尺寸";
    public static final String V_UNIT = "单位";
    public static final String V_PURCHASE_PRICE = "进价";
    public static final String V_SELLING_PRICE = "售价";
    public static final String V_COMMENT = "注释";

    static ISuitDao dao;
    static IDepositoryDao depositoryDao;

    static {
        dao = SpringContextHelperHandler.getSch().getBean("suitDao", ISuitDao.class);
        depositoryDao = SpringContextHelperHandler.getSch().getBean("depositoryDao", IDepositoryDao.class);
    }

    public SuitContainerGetter() {
        super(dao, SuitModel.class);
        initFillContainer();
    }

    @Override
    public void initFillContainer() {
        Collection<SuitModel> models = dao.listAllAmount();
        containerCollection.addAll(models);
        fillContainer(models);
    }

    @Override
    public void insertItem(SuitModel model) {
        super.insertItem(model);
        DepositoryModel depositoryModel = new DepositoryModel(0, model);
        depositoryDao.save(depositoryModel);
    }

    @Override
    public void delItem(SuitModel model) throws Exception {
        DepositoryModel depositoryModel = depositoryDao.queryOneBySuit(model);
        if(depositoryModel.getAmount() < 1) {
            depositoryDao.delete(model.getId());
            super.delItem(model);
        } else {
            throw new Exception("此商品还有库存!");
        }
    }

    @Override
    public Object[] getColumns() {
        return new Object[] {H_NAME, H_STANDARD, H_UNIT, H_SELLING_PRICE, H_INTERNATIONAL_CODE, H_TYPE, H_FACTORY, H_PURCHASE_PRICE};
    }
    @Override
    public String[] getColumnsHeaders() {
        return new String[]{V_NAME, V_STANDARD, V_UNIT, V_SELLING_PRICE, V_INTERNATIONAL_CODE, V_TYPE, V_FACTORY, V_PURCHASE_PRICE};
    }

    @Override
    @Deprecated
    public SuitModel searchItem(SuitModel model) {
        return null;
    }

    public SuitModel searchItemByCode(String code) {
        List<SuitModel> modelList = dao.queryByCode(code);
        if(!modelList.isEmpty()) {
            return modelList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void fillContainerByName(String name) {
        Iterator<SuitModel> iterator = containerCollection.iterator();
        clearContainer();
        if(name != null && !name.isEmpty()) {
            while(iterator.hasNext()) {
                SuitModel model = iterator.next();
                if(model.getAbbreviation().contains(name.toUpperCase())) {
                    addItem(model);
                }
            }
        } else {
            fillContainerByAll();
        }
    }

    public SuitModel searchMaxIdSuit() {
        return dao.queryByMaxId();
    }
}
