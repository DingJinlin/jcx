package com.djl.jcx.db.service.impl;

import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.db.dao.SuitDao;
import com.djl.jcx.db.service.SuitService;

import java.util.List;

/**
 * Created by ding on 14-2-12.
 *
 */
public class SuitServiceImpl implements SuitService{
    private SuitDao suitDao;

    public SuitDao getSuitDao() {
        return suitDao;
    }

    public void setSuitDao(SuitDao suitDao) {
        this.suitDao = suitDao;
    }

    @Override
    public int insert(SuitModel suitModel) {
        return suitDao.insert(suitModel);
    }

    @Override
    public int deleteById(int id) {
        return suitDao.deleteById(id);
    }

    @Override
    public void update(SuitModel suitModel) {
        suitDao.update(suitModel);
    }

    @Override
    public SuitModel selectById(SuitModel suitModel) {
        return suitDao.selectById(suitModel);
    }

    @Override
    public List<SuitModel> selectAll() {
        return suitDao.selectAll();
    }

    @Override
    public SuitModel selectByMaxId() {
        return suitDao.selectByMaxId();
    }


}
