package com.dawid.ems.dao;

import com.dawid.ems.entity.ShiftProduction;
import com.dawid.ems.payload.AveragePerAllFromMonth;

import java.util.List;

public interface ShiftProductionDAO {

    List<ShiftProduction> getAll();

}
