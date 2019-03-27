package com.dawid.ems.service;

import com.dawid.ems.entity.ProductionWorker;
import com.dawid.ems.repository.ProductionWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionWorkerServiceImpl implements ProductionWorkerService {

    private final ProductionWorkerRepository productionWorkerRepository;

    @Autowired
    public ProductionWorkerServiceImpl(ProductionWorkerRepository productionWorkerRepository) {
        this.productionWorkerRepository = productionWorkerRepository;
    }

    @Override
    public List<ProductionWorker> getAll() {
        return productionWorkerRepository.findAll();
    }
}
