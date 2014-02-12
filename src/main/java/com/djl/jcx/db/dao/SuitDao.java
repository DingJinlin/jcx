package com.djl.jcx.db.dao;

import com.djl.jcx.data.model.SuitModel;

import java.util.List;

/**
 * Created by ding on 14-2-12.
 */
public interface SuitDao {
    public int insert(SuitModel suitModel);
    public int deleteById(int id);
    public void update(SuitModel suitModel);
    public SuitModel selectById(SuitModel suitModel);
    public List<SuitModel> selectAll();
    public SuitModel selectByMaxId();
}
