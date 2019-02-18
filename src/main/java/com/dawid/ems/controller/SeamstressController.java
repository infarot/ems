package com.dawid.ems.controller;

import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;
import com.dawid.ems.exception.SeamstressNotFoundException;
import com.dawid.ems.service.SeamstressService;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SeamstressController {

    private SeamstressService seamstressService;

    @Autowired
    public SeamstressController(SeamstressService seamstressService) {
        this.seamstressService = seamstressService;
    }

    private double getAverageResult(int seamstressId) {
        List<Result> results = seamstressService.getAllResults(seamstressId);
        //get all results from one day
        Map<LocalDate, List<Result>> map = results.stream().collect(
                Collectors.groupingBy(Result::getDate, HashMap::new, Collectors.toList()));
        //sum all results from one day
        Map<LocalDate, Double> percentageResultMap = new HashMap<>();
        for (LocalDate d : map.keySet()) {
            percentageResultMap.put(d, map.get(d).stream().mapToDouble(Result::getPercentageResult).sum());
        }
        //calculate average and ignore results which are less then 50 %
        OptionalDouble average = percentageResultMap.values().stream().filter(a -> a > 50).mapToDouble(a -> a).average();
        if (average.isPresent()) {
            return Precision.round(average.getAsDouble(), 2);
        } else {
            return 0.0;
        }
    }

    public Double getScore(int seamstressId) {
        return Precision.round(seamstressService.getAllResults(seamstressId).stream().mapToDouble(Result::getPercentageResult).sum(),2);
    }

    @GetMapping("/seamstress")
    public List<Seamstress> getAll() {
        List<Seamstress> seamstresses = seamstressService.getAll();
        seamstresses.forEach(s -> s.setAverage(getAverageResult(s.getId())));
        seamstresses.forEach(s -> s.setScore(getScore(s.getId())));
        Collections.sort(seamstresses);
        return seamstresses;
    }


    @GetMapping("/seamstress/results/{seamstressId}")
    public List<Result> getAllResults(@PathVariable int seamstressId) {
        Seamstress seamstress = seamstressService.getSingle(seamstressId);
        if (seamstress == null) {
            throw new SeamstressNotFoundException("Seamstress with id " + seamstressId + " not found");
        }
        return seamstressService.getAllResults(seamstressId);
    }

    @GetMapping("/seamstress/{seamstressId}")
    public Seamstress getSingle(@PathVariable int seamstressId) {
        Seamstress seamstress = seamstressService.getSingle(seamstressId);
        if (seamstress == null) {
            throw new SeamstressNotFoundException("Seamstress with id " + seamstressId + " not found");
        }
        seamstress.setAverage(getAverageResult(seamstressId));
        seamstress.setScore(getScore(seamstressId));
        return seamstressService.getSingle(seamstressId);
    }


}
