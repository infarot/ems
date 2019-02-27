package com.dawid.ems.service;

import com.dawid.ems.entity.ShiftProduction;
import com.dawid.ems.payload.AveragePerAllFromMonth;

import java.util.List;

public interface ShiftProductionService {

    List<ShiftProduction> getAll();

    AveragePerAllFromMonth getAveragePerAllFromMonth(int month);

}
