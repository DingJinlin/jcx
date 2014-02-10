package com.djl.jcx.data.model;

/**
 * User: Administrator
 * Date: 13-3-18
 * Time: 上午9:47
 * 商品花色信息
 */
public class SuitModel extends BaseModel<SuitModel> {
    /** 条码 */
    private String internationalCode;

    /** 类别 */
    private TypeModel type;

    /** 名称 */
    private String name;

    /** 显名名称 */
    private String viewName;

    /** 缩写 */
    private String abbreviation;

    /** 厂家 */
    private FactoryModel factory;

    /** 规格 */
    private String standard;

    /** 单位 */
    private String unit;

    /** 进价 */
    private Float purchasePrice;

    /** 售价 */
    private Float sellingPrice;

    /** 备注 */
    private String comment;

    /** 存量 */
    private int amount;

    public SuitModel() {
    }

    public SuitModel(int id) {
        setId(id);
    }

    public SuitModel(String comment, FactoryModel factory, String internationalCode, String name, String abbreviation, Float purchasePrice, Float sellingPrice, String standard, TypeModel type, String unit) {
        this.comment = comment;
        this.factory = factory;
        this.internationalCode = internationalCode;
        this.name = name;
        this.abbreviation = abbreviation;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.standard = standard;
        this.type = type;
        this.unit = unit;
    }

    public SuitModel(String comment, FactoryModel factory, String internationalCode, String name, String abbreviation, Float purchasePrice, Float sellingPrice, String standard, TypeModel type, String unit, int amount) {
        this.comment = comment;
        this.factory = factory;
        this.internationalCode = internationalCode;
        this.name = name;
        this.abbreviation = abbreviation;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.standard = standard;
        this.type = type;
        this.unit = unit;
        this.amount = amount;
    }

    public FactoryModel getFactory() {
        return factory;
    }

    public void setFactory(FactoryModel factory) {
        this.factory = factory;
    }

    public String getInternationalCode() {
        return internationalCode;
    }

    public void setInternationalCode(String internationalCode) {
        this.internationalCode = internationalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getViewName() {
        return viewName = name + "(" + standard + unit + ")" + " " + sellingPrice + "元";
    }

//    public void setViewName(String viewName) {
//        this.viewName = viewName;
//    }

    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public TypeModel getType() {
        return type;
    }

    public void setType(TypeModel type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void copyValue(SuitModel model) {
        this.id = model.getId();
        this.comment = model.getComment();
        this.factory.copyValue(model.getFactory());
        this.internationalCode = model.getInternationalCode();
        this.name = model.getName();
        this.abbreviation = model.getAbbreviation();
        this.purchasePrice = model.getPurchasePrice();
        this.sellingPrice = model.getSellingPrice();
        this.standard = model.getStandard();
        this.type.copyValue(model.getType());
        this.unit = model.getStandard();
        this.amount = model.getAmount();
    }
}
