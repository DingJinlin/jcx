package com.djl.jcx.ui.table.container;

import com.djl.jcx.data.dao.IDepositoryDao;
import com.djl.jcx.data.model.DepositoryModel;
import com.djl.jcx.data.model.SuitModel;
import com.spring.SpringContextHelperHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * User: Administrator
 * Date: 13-3-22
 * Time: 上午9:30
 */
public class DepositoryContainerGetter extends AbstractContainerGetter<DepositoryModel, IDepositoryDao> {
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
    public static final String V_UNIT = "Unit";
    public static final String V_AMOUNT = "Amount";
    public static final String V_COMMENT = "Comment";

    static IDepositoryDao dao;

    static {
        dao = SpringContextHelperHandler.getSch().getBean("depositoryDao", IDepositoryDao.class);
    }

    public DepositoryContainerGetter() {
        super(dao, DepositoryModel.class);
        initFillContainer();
    }

    @Override
    @Deprecated
    public DepositoryModel searchItem(DepositoryModel model) {
        return null;
    }

    /**
     * 查询商品库存
     * @param suits
     * @return
     */
    public List<DepositoryModel> searchBySuit(Collection<SuitModel> suits) {
        List<DepositoryModel> depositories = new ArrayList();
        for (SuitModel suit : suits) {
            DepositoryModel depository = dao.queryOneBySuit(suit);
            if(depository != null) {
                depositories.add(depository);
            }
        }

        return depositories;
    }

    /**
     * 增加商品存量
     * @param model
     */
    public void addDepository(DepositoryModel model) {
        DepositoryModel resultModel = dao.add(model.getSuit(), model.getAmount());

        // 更新缓存中的数据
        if(resultModel != null) {
            DepositoryModel containerItem = getItem(resultModel);
            containerItem.setAmount(resultModel.getAmount());
            super.addItem(containerItem);
        }
    }

    /**
     * 移去商品存量
     * @param model
     */
    public void subtractDepository(DepositoryModel model) {
        DepositoryModel resultModel = dao.subtract(model.getSuit(), model.getAmount());

        // 更新缓存中的数据
        if(resultModel != null) {
            DepositoryModel containerItem = getItem(resultModel);
            containerItem.setAmount(resultModel.getAmount());
            super.addItem(containerItem);
        }
    }

    public DepositoryModel getItemByInternationalCode(String internationaCode) {
        for(DepositoryModel containerItem : containerCollection) {
            if(containerItem.getSuit().getInternationalCode().equalsIgnoreCase(internationaCode))
                return containerItem;
        }

        return null;
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
    public void fillContainerByName(String name) {
        Iterator<DepositoryModel> iterator = containerCollection.iterator();
        clearContainer();
        if(name != null && !name.isEmpty()) {
            while(iterator.hasNext()) {
                DepositoryModel model = iterator.next();
                if(model.getSuit().getAbbreviation().contains(name.toUpperCase())) {
                    addItem(model);
                }
            }
        } else {
            fillContainerByAll();
        }
    }

    public void fillContainerByCode(String code) {
        Iterator<DepositoryModel> iterator = containerCollection.iterator();
        clearContainer();
        if(code != null && !code.isEmpty()) {
            while(iterator.hasNext()) {
                DepositoryModel model = iterator.next();
                if(model.getSuit().getInternationalCode().equalsIgnoreCase(code)) {
                    addItem(model);
                }
            }
        } else {
            fillContainerByAll();
        }
    }
}
