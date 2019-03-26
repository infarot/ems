package com.dawid.ems.controller;

import com.dawid.ems.entity.ProductionWorker;
import com.dawid.ems.entity.QuiltingData;
import com.dawid.ems.payload.QuiltingStatisticsFromMonth;
import com.dawid.ems.service.QuiltingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QuiltingController {

    private final QuiltingService quiltingService;

    @Autowired
    public QuiltingController(QuiltingService quiltingService) {
        this.quiltingService = quiltingService;
    }

    @GetMapping("/quilting")
    public List<QuiltingData> getAll() {
        return quiltingService.getAll();
    }

    @GetMapping("/quilting/{from}/{to}/{id}")
    public List<QuiltingData> getAllByDateIntervalAndOperator(@PathVariable("from") String from, @PathVariable("to") String to, @PathVariable("id") int id) {
        LocalDate parsedFrom = LocalDate.parse(from);
        LocalDate parsedTo = LocalDate.parse(to);
        ProductionWorker operator = quiltingService.getProductionWorker(id);
        return quiltingService.getAllByDateBetweenAndOperator(parsedFrom, parsedTo, operator);
    }

    @GetMapping("/quilting/statistics/{month}/{year}")
    public QuiltingStatisticsFromMonth getQuiltingStatisticsFromMonth(@PathVariable("month") int month, @PathVariable("year") int year) {
        return quiltingService.getQuiltingStatisticsFromMonth(month, year);
    }

    @GetMapping("/quilting/statistics/{month}/{year}/{id}")
    public QuiltingStatisticsFromMonth getQuiltingStatisticsFromMonthByOperator(@PathVariable("month") int month, @PathVariable("year") int year, @PathVariable("id") int id) {
        return quiltingService.getQuiltingStatisticsFromMonthByOperator(month, year, id);
    }
}
