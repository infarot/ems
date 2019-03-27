package com.dawid.ems.controller;

import com.dawid.ems.entity.ProductionWorker;
import com.dawid.ems.service.ProductionWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductionWorkerController {

    private final ProductionWorkerService productionWorkerService;

    @Autowired
    public ProductionWorkerController(ProductionWorkerService productionWorkerService) {
        this.productionWorkerService = productionWorkerService;
    }


    @GetMapping("/productionWorker")
    public List<ProductionWorker> getAll() {
        return productionWorkerService.getAll();
    }
}
