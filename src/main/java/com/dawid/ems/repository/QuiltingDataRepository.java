package com.dawid.ems.repository;

import com.dawid.ems.entity.ProductionWorker;
import com.dawid.ems.entity.QuiltingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuiltingDataRepository extends JpaRepository<QuiltingData, Integer> {
    List<QuiltingData> getAllByDateBetweenAndOperator(LocalDate from, LocalDate to, ProductionWorker operator);
}
