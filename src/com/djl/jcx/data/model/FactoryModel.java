package com.djl.jcx.data.model;

/**
 * User: Administrator
 * Date: 13-3-18
 * Time: 上午9:33
 * 厂家信息
 */
public class FactoryModel extends BaseModel<FactoryModel> {
    /** 厂名 */
    private String name = null;

    /** 地址 */
    private String address = null;

    /** 联系人 */
    private String contactName = null;

    /** 缩写 */
    private String abbreviation = null;

    /** 联系人名 */
    private String contactPhone = null;

    public FactoryModel() {
    }

    public FactoryModel(int id) {
        setId(id);
    }

    public FactoryModel(String address, String contactName, String contactPhone, String name, String abbreviation) {
        this.address = address;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public void copyValue(FactoryModel model) {
        this.id = model.getId();
        this.address = model.getAddress();
        this.contactName = model.getContactName();
        this.contactPhone = model.getContactPhone();
        this.name = model.getName();
        this.abbreviation = model.getAbbreviation();
    }
}
