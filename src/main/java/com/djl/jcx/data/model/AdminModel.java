package com.djl.jcx.data.model;

/**
 * User: Administrator
 * Date: 13-3-18
 * Time: 下午12:44
 */
public class AdminModel extends BaseModel<AdminModel> {
    /** 用户名 */
    private String name = null;

    /** 密码 */
    private String password = null;

    /** 密码验证 */
    private String rePassword = null;

    /** 注释 */
    private String comment = null;

    public AdminModel() {
    }

    public AdminModel(int id) {
        setId(id);
    }

    public AdminModel(String comment, String name, String password) {
        this.comment = comment;
        this.name = name;
        this.password = password;
    }

    public AdminModel(String comment, String name, String password, String rePassword) {
        this.comment = comment;
        this.name = name;
        this.password = password;
        this.rePassword = rePassword;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    @Override
    public void copyValue(AdminModel model) {
        this.id = model.getId();
        this.comment = model.getComment();
        this.name = model.getName();
        this.password = model.getPassword();
        this.rePassword = model.getRePassword();
    }
}
