package com.dawid.ems.service;

import com.dawid.ems.dao.SeamstressDAO;
import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SeamstressServiceImpl implements SeamstressService {

    private final SeamstressDAO seamstressDAO;

    @Autowired
    public SeamstressServiceImpl(SeamstressDAO seamstressDAO){
        this.seamstressDAO = seamstressDAO;
    }

    @Override
    @Transactional
    public List<Seamstress> getAll() {
        return seamstressDAO.getAll();
    }

    @Override
    @Transactional
    public Seamstress getSingle(int id) {
        return seamstressDAO.getSingle(id);
    }

    @Override
    @Transactional
    public List<Result> getAllResults(int id) {
        return seamstressDAO.getAllResults(id);
    }
}
