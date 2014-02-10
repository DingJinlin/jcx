package com.djl.jcx.data.model;

/**
 * User: Administrator
 * Date: 13-3-18
 * Time: 上午9:39
 * 库存信息
 */
public class DepositoryModel extends BaseModel<DepositoryModel> {
    /** 商品 */
    SuitModel suit = null;

    /** 存量 */
    Integer amount = null;

    public DepositoryModel() {
    }

    public DepositoryModel(int id) {
        setId(id);
    }

    public DepositoryModel(Integer amount, SuitModel suit) {
        this.amount = amount;
        this.suit = suit;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public SuitModel getSuit() {
        return suit;
    }

    public void setSuit(SuitModel suit) {
        this.suit = suit;
    }

    public FactoryModel getFactory() {
        return suit.getFactory();
    }

    public void setFactory(FactoryModel factory) {
        this.suit.setFactory(factory);
    }

    public String getInternationalCode() {
        return suit.getInternationalCode();
    }

    public void setInternationalCode(String internationalCode) {
        this.suit.setInternationalCode(internationalCode);
    }

    public String getName() {
        return suit.getName();
    }

    public void setName(String name) {
        this.suit.setName(name);
    }

    public Float getPurchasePrice() {
        return suit.getPurchasePrice();
    }

    public void setPurchasePrice(Float purchasePrice) {
        suit.setPurchasePrice(purchasePrice);
    }

    public Float getSellingPrice() {
        return suit.getSellingPrice();
    }

    public void setSellingPrice(Float sellingPrice) {
        suit.setSellingPrice(sellingPrice);
    }

    public String getStandard() {
        return suit.getStandard();
    }

    public void setStandard(String standard) {
        suit.setStandard(standard);
    }

    public TypeModel getType() {
        return suit.getType();
    }

    public void setType(TypeModel type) {
        suit.setType(type);
    }

    public String getUnit() {
        return suit.getUnit();
    }

    public void setUnit(String unit) {
        suit.setUnit(unit);
    }

    public String getComment() {
        return suit.getComment();
    }

    public void setComment(String comment) {
        suit.setComment(comment);
    }

    public String getAbbreviation() {
        return suit.getAbbreviation();
    }

    public void setAbbreviation(String abbreviation) {
        suit.setAbbreviation(abbreviation);
    }

    @Override
    public void copyValue(DepositoryModel model) {
        this.id = model.getId();
        this.amount = model.getAmount();
        this.suit.copyValue(model.getSuit());
    }
}
