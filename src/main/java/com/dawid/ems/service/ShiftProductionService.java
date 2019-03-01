package com.dawid.ems.service;

import com.dawid.ems.entity.ShiftProduction;
import com.dawid.ems.payload.StatisticsFromMonth;

import java.util.List;

public interface ShiftProductionService {

    List<ShiftProduction> getAll();

    StatisticsFromMonth getStatisticsFromMonth(int month, int year);

}
