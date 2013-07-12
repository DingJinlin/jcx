package com.djl.jcx.data.dao.ibatis;

import com.djl.jcx.data.dao.ITypeDao;
import com.djl.jcx.data.model.TypeModel;

/**
 * User: Administrator
 * Date: 13-2-19
 * Time: 下午3:17
 */
public class TypeDaoImpl extends BaseDaoImpl<TypeModel> implements ITypeDao {
    protected TypeDaoImpl() {
        super("TypeSQL");
    }
}
