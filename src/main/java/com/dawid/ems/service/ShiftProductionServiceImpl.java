package com.dawid.ems.service;

import com.dawid.ems.dao.ShiftProductionDAO;
import com.dawid.ems.entity.ShiftProduction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShiftProductionServiceImpl implements ShiftProductionService {

    private final ShiftProductionDAO shiftProductionDAO;

    @Autowired
    public ShiftProductionServiceImpl(ShiftProductionDAO shiftProductionDAO) {
        this.shiftProductionDAO = shiftProductionDAO;
    }

    @Override
    @Transactional
    public List<ShiftProduction> getAll() {

        return shiftProductionDAO.getAll().stream().sorted().collect(Collectors.toList());
    }
}
