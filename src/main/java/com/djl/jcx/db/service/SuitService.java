package com.djl.jcx.db.service;

import com.djl.jcx.data.model.SuitModel;

import java.util.List;

/**
 * Created by ding on 14-2-12.
 */
public interface SuitService {
    public int insert(SuitModel suitModel);
    public int deleteById(int id);
    public void update(SuitModel suitModel);
    public SuitModel selectById(SuitModel suitModel);
    public List<SuitModel> selectAll();
    public SuitModel selectByMaxId();
}
