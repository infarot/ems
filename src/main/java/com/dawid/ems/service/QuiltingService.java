package com.dawid.ems.service;

import com.dawid.ems.entity.ProductionWorker;
import com.dawid.ems.entity.QuiltingData;

import java.time.LocalDate;
import java.util.List;

public interface QuiltingService {

    List<QuiltingData> getAll();

    List<QuiltingData> getAllByDateBetweenAndOperator(LocalDate from, LocalDate to, ProductionWorker operator);

    ProductionWorker getProductionWorker(Integer id);
}
