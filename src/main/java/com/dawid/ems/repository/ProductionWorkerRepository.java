package com.dawid.ems.repository;

import com.dawid.ems.entity.ProductionWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionWorkerRepository extends JpaRepository<ProductionWorker, Integer> {
}
