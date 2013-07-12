package com.djl.jcx.data.model;

/**
 * User: Administrator
 * Date: 13-3-18
 * Time: 上午9:33
 * 商品类别信息
 */
public class TypeModel extends BaseModel<TypeModel> {
    /** 类型名 */
    private String name;

    /** 缩写 */
    private String abbreviation;


    public TypeModel() {
    }

    public TypeModel(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
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
    public void copyValue(TypeModel model) {
        this.id = model.getId();
        this.name = model.getName();
        this.abbreviation = model.getAbbreviation();
    }
}
