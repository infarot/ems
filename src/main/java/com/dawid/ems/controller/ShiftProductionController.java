package com.dawid.ems.controller;

import com.dawid.ems.entity.ShiftProduction;
import com.dawid.ems.payload.AveragePerAllFromMonth;
import com.dawid.ems.service.ShiftProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShiftProductionController {

    private final ShiftProductionService shiftProductionService;

    @Autowired
    public ShiftProductionController(ShiftProductionService shiftProductionService) {
        this.shiftProductionService = shiftProductionService;
    }

    @GetMapping("/shiftProduction")
    public List<ShiftProduction> getAll(){
        return shiftProductionService.getAll();
    }

    @GetMapping("/shiftProduction/averagePerAll/{month}")
    public AveragePerAllFromMonth getAveragePerAllFromMonth(@PathVariable int month){
        return shiftProductionService.getAveragePerAllFromMonth(month);
    }
}
