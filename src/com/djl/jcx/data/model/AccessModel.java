package com.djl.jcx.data.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Administrator
 * Date: 13-3-18
 * Time: 上午9:39
 * 存取信息
 */
public class AccessModel extends BaseModel<AccessModel> {
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

    public AccessModel() {
    }

    public AccessModel(int id) {
        setId(id);
    }

    public AccessModel(EAction action, AdminModel admin, Integer count, Date date, SuitModel suit) {
        this.action = action;
        this.admin = admin;
        this.count = count;
        this.date = date;
        this.suit = suit;
    }

    public AccessModel(AdminModel admin, Integer count, Date date, SuitModel suit, Float price) {
        this.action = EAction.SOLD;
        this.admin = admin;
        this.count = count;
        this.date = date;
        this.suit = suit;
        this.price = price;
    }

    public AccessModel(EAction action, AdminModel admin, Integer count, Date date, Date dateCondition1, Date dateCondition2, Float price, SuitModel suit) {
        this.action = action;
        this.admin = admin;
        this.count = count;
        this.date = date;
        this.dateCondition1 = dateCondition1;
        this.dateCondition2 = dateCondition2;
        this.price = price;
        this.suit = suit;
    }

    public AccessModel(EAction action, Date dateCondition1, Date dateCondition2, SuitModel suit) {
        this.action = action;
        this.dateCondition1 = dateCondition1;
        this.dateCondition2 = dateCondition2;
        this.suit = suit;
    }

    public void setAction(int actionCode) {
        action = EAction.valueOf(actionCode);
    }

    public void setAction(EAction action) {
        action = action;
    }

    public int getAction() {
        return action.value();
    }

//    publicDate getDate() {
//        return date;
//    }
//
    public String getDate() {
        String sData = "";
        if(date != null) {
            sData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E").format(date);
        }
        return sData;
    }

    public Date getAccessData() {
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
    private String name;
    private String adminName = "";
    private Float sellingPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuitName() {
        return name;
    }

    public String getAdminName() {
        return adminName;
    }

    public Float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public void copyValue(AccessModel model) {
        this.id = model.getId();
        this.action.valueOf(model.getAction());
        this.admin.copyValue(model.getAdmin());
        this.count = model.getCount();
        this.date = model.getAccessData();
        this.dateCondition1 = model.getDateCondition1();
        this.dateCondition2 = model.getDateCondition2();
        this.price = model.getPrice();
        this.suit.copyValue(model.getSuit());
    }
}
