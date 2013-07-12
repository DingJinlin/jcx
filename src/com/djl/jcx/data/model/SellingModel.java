package com.djl.jcx.data.model;

import java.util.Date;

/**
 * User: Administrator
 * Date: 13-3-18
 * Time: 上午9:39
 * 存取信息
 */
public class SellingModel extends BaseModel<SellingModel> {
    public enum EAction {
        STORAGE(0),
        PULL(1),
        SOLD(2);

        int code;
        private EAction(int code) {
            this.code = code;
        }

        public int value() {
            return code;
        }

        public static EAction valueOf(int value) {
            EAction action = null;
            for(EAction type : EAction.values()) {
                if(type.value() == value) {
                    action = type;
                }
            }

            return action;
        }
    }


    /** 商品 */
    SuitModel suit = null;

    /** 动作 存入/销售/取出 */
    EAction action = null;

    /** 日期 */
    Date date = null;

    /** 管理员 */
    AdminModel admin = null;

    /** 进出库数量 */
    Integer count = null;

    /** 售价 */
    Float price = null;

    /** 查询时间条件1 */
    Date dateCondition1 = null;

    /** 查询时间条件2 */
    Date dateCondition2 = null;

    public SellingModel() {
    }

    public SellingModel(int id) {
        setId(id);
    }

    public SellingModel(EAction action, AdminModel admin, Integer count, Date date, SuitModel suit) {
        this.action = action;
        this.admin = admin;
        this.count = count;
        this.date = date;
        this.suit = suit;
    }

    public SellingModel(AdminModel admin, Integer count, Date date, SuitModel suit, Float price) {
        this.action = EAction.SOLD;
        this.admin = admin;
        this.count = count;
        this.date = date;
        this.suit = suit;
        this.price = price;
    }

    public SellingModel(EAction action, Date date, SuitModel suit, Float price) {
        this.action = EAction.SOLD;
        this.count = count;
        this.date = date;
        this.suit = suit;
        this.price = price;
    }


    public void setActionCode(int actionCode) {
        action = EAction.valueOf(actionCode);
    }

    public int getActionCode() {
        return action.value();
    }

    public void setAction(EAction action) {
        this.action = action;
    }

    public EAction getAction() {
        return action;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SuitModel getSuit() {
        return suit;
    }

    public void setSuit(SuitModel suit) {
        this.suit = suit;
    }

    public AdminModel getAdmin() {
        return admin;
    }

    public void setAdmin(AdminModel admin) {
        this.admin = admin;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getDateCondition1() {
        return dateCondition1;
    }

    public void setDateCondition1(Date dateCondition1) {
        this.dateCondition1 = dateCondition1;
    }

    public Date getDateCondition2() {
        return dateCondition2;
    }

    public void setDateCondition2(Date dateCondition2) {
        this.dateCondition2 = dateCondition2;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    /***********************************************************************************
     * 用于Table显示
     **********************************************************************************/
    /** 商品存量 */
    Integer amount = null;

//    private String suitName;
//    private String adminName;
//    private float sellingPrice;

    public int getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public float getSum() {
        return this.price * this.count;
    }

    /* 获取减价 */
    public float getReducePrice() {
        return suit.getSellingPrice() - price;
    }

    /* 设置减价 */
    public void setReducePrice(float reducePrice) {
        this.price = suit.getSellingPrice() - reducePrice;
    }

    public FactoryModel getFactory() {
        return suit.getFactory();
    }

    public String getInternationalCode() {
        return suit.getInternationalCode();
    }

    public String getName() {
        return suit.getName();
    }

    public Float getPurchasePrice() {
        return suit.getPurchasePrice();
    }

    public String getStandard() {
        return suit.getStandard();
    }

    public TypeModel getType() {
        return suit.getType();
    }

    public String getUnit() {
        return suit.getUnit();
    }

    public String getComment() {
        return suit.getComment();
    }

    public String getAbbreviation() {
        return suit.getAbbreviation();
    }

    public String getSuitName() {
        return suit.getName();
    }

    public String getSuitViewName() {
        return suit.getViewName();
    }

    public String getAdminName() {
        return admin.getName();
    }

    public float getSellingPrice() {
        return suit.getSellingPrice();
    }

    @Override
    public void copyValue(SellingModel model) {
        this.id = model.getId();
        this.action = model.getAction();
        this.admin.copyValue(model.getAdmin());
        this.count = model.getCount();
        this.date = model.getDate();
        this.dateCondition1 = model.getDateCondition1();
        this.dateCondition2 = model.getDateCondition2();
        this.price = model.getPrice();
        this.suit.copyValue(model.getSuit());
    }
}
