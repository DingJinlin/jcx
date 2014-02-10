package com.djl.jcx.data.dao.ibatis;

import com.djl.jcx.data.dao.IBaseDao;
import com.djl.jcx.data.model.BaseModel;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * @param <M>  模型对象
 * @author Zhang Kaitao
 */
public abstract class BaseDaoImpl<M extends BaseModel> extends SqlMapClientDaoSupport
        implements IBaseDao<M> {

    private Class<M> entityClass;

    protected String sqlMap;

    protected BaseDaoImpl(String sqlMap) {
        this.sqlMap = sqlMap;
    }

    @Override
    public void createTable() throws SQLException {
        getSqlMapClientTemplate().update(sqlMap + ".createTable");
    }

    @Override
    public void dropTable() throws SQLException {
        getSqlMapClientTemplate().update(sqlMap + ".dropTable");
    }

    @Override
    public Integer save(M model) {
        return (Integer) getSqlMapClientTemplate().insert(sqlMap + ".insert", model);
    }

    @Override
    public void saveOrUpdate(M model) {
    }

    @Override
    public void update(M model) {
        getSqlMapClientTemplate().update(sqlMap + ".update", model);
    }

    @Override
    public void merge(M model) {
    }

    @Override
    public void delete(Integer id) {
        getSqlMapClientTemplate().delete(sqlMap + ".deleteById", id);
    }

    @Override
    public M get(Integer id) {
        return (M) getSqlMapClientTemplate().queryForObject(sqlMap + ".selectById", id);
    }

    @Override
    public int countAll() {
//        return (Integer) getSqlMapClientTemplate().queryForObject("UserSQL.countAll");
        return 0;
    }

    @Override
    public List<M> listAll() {
        return getSqlMapClientTemplate().queryForList(sqlMap + ".select");
    }

    @Override
    public List<M> listByModel(M model) {
        return getSqlMapClientTemplate().queryForList(sqlMap + ".selectByParam", model);
    }

    @Override
    public List<M> listAll(int pn, int pageSize) {
        return null;
    }
}
