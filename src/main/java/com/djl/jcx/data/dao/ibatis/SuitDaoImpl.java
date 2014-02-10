package com.djl.jcx.data.dao.ibatis;

import com.djl.jcx.data.dao.ISuitDao;
import com.djl.jcx.data.model.FactoryModel;
import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.data.model.TypeModel;

import java.util.List;

/**
 * User: Administrator
 * Date: 13-2-19
 * Time: 下午3:17
 */
public class SuitDaoImpl extends BaseDaoImpl<SuitModel> implements ISuitDao {
    protected SuitDaoImpl() {
        super("SuitSQL");
    }

    @Override
    public List<SuitModel> queryByName(String name) {
        SuitModel model = new SuitModel();
        model.setName(name);

        return listByModel(model);
    }

    @Override
    public List<SuitModel> queryByTypeId(int typeId) {
        TypeModel type = new TypeModel();
        type.setId(typeId);
        SuitModel suit = new SuitModel();
        suit.setType(type);

        return listByModel(suit);
    }

    @Override
    public List<SuitModel> queryByFactoryId(int factoryId) {
        FactoryModel factory = new FactoryModel();
        factory.setId(factoryId);
        SuitModel suit = new SuitModel();
        suit.setFactory(factory);

        return listByModel(suit);
    }

    @Override
    public SuitModel queryByMaxId() {
        List<SuitModel> list = getSqlMapClientTemplate().queryForList(sqlMap + ".selectByMaxId");
        if(list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<SuitModel> queryByCode(String code) {
        SuitModel suit = new SuitModel();
        suit.setInternationalCode(code);

        return listByModel(suit);
    }

    @Override
    public List<SuitModel> listAllAmount() {
        return getSqlMapClientTemplate().queryForList(sqlMap + ".select");
    }
}
