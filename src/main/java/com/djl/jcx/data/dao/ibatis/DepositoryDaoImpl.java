package com.djl.jcx.data.dao.ibatis;

import com.djl.jcx.data.dao.IDepositoryDao;
import com.djl.jcx.data.model.DepositoryModel;
import com.djl.jcx.data.model.FactoryModel;
import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.data.model.TypeModel;

import java.util.List;

/**
 * User: Administrator
 * Date: 13-2-19
 * Time: 下午3:17
 */
public class DepositoryDaoImpl extends BaseDaoImpl<DepositoryModel> implements IDepositoryDao {
    protected DepositoryDaoImpl() {
        super("DepositorySQL");
    }

    public void delBySuitId(int suitId) {
        getSqlMapClientTemplate().delete(sqlMap + ".delBySuitId", suitId);
    }

    @Override
    public DepositoryModel add(SuitModel suit, int amount) {
        DepositoryModel depository = queryOneBySuit(suit);
        depository.setAmount(depository.getAmount() + amount);
        update(depository);

        return depository;
    }

    @Override
    public DepositoryModel subtract(SuitModel suit, int amount) {
        DepositoryModel depository = queryOneBySuit(suit);
        depository.setAmount(depository.getAmount() - amount);
        update(depository);

        return depository;
    }

    @Override
    public List<DepositoryModel> listAllByFactory(FactoryModel factory) {
        return getSqlMapClientTemplate().queryForList(sqlMap + ".selectByFactory", factory.getId());
    }

    @Override
    public List<DepositoryModel> listAllByType(TypeModel type) {
        return getSqlMapClientTemplate().queryForList(sqlMap + ".selectByType", type.getId());

    }

    @Override
    public DepositoryModel queryOneBySuit(SuitModel suit) {
        DepositoryModel model = new DepositoryModel();
        model.setSuit(suit);
        List<DepositoryModel> depositoryList = listByModel(model);
        if (!depositoryList.isEmpty()) {
            return depositoryList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<DepositoryModel> listAllByAmount(int amount) {
        DepositoryModel model = new DepositoryModel();
        model.setAmount(amount);

        return listByModel(model);
    }

    @Override
    public List<DepositoryModel> listAllByAmountGreater(int amount) {
        return getSqlMapClientTemplate().queryForList(sqlMap + ".selectByAmountGreater", amount);
    }

    @Override
    public List<DepositoryModel> listAllByAmountLess(int amount) {
        return getSqlMapClientTemplate().queryForList(sqlMap + ".selectByAmountLess", amount);
    }
}
