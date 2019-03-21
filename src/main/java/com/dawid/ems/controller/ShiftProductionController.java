package com.dawid.ems.controller;

import com.dawid.ems.entity.ShiftProduction;
import com.dawid.ems.payload.StatisticsFromMonth;
import com.dawid.ems.service.ShiftProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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
        List<ShiftProduction> shiftProductionList = shiftProductionService.getAll();
        Collections.sort(shiftProductionList);
        return shiftProductionList;
    }

    @GetMapping("/shiftProduction/monthStatistics/{month}/{year}")
    public StatisticsFromMonth getAveragePerAllFromMonth(@PathVariable int month, @PathVariable int year){
        return shiftProductionService.getStatisticsFromMonth(month, year);
    }
}
