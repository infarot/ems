package com.dawid.ems.controller;

import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;
import com.dawid.ems.exception.SeamstressNotFoundException;
import com.dawid.ems.service.SeamstressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/seamstress")
    public List<Seamstress> getAll() {
        return seamstressService.getAll();
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
        return seamstressService.getSingle(seamstressId);
    }

    @GetMapping("/seamstress/results/average/{seamstressId}")
    public Double getAverageResult(@PathVariable int seamstressId) {
        List<Result> results = seamstressService.getAllResults(seamstressId);
        //get all results from one day
        HashMap<LocalDate, List<Result>> map = results.stream().collect(
                Collectors.groupingBy(Result::getDate, HashMap::new, Collectors.toList()));
        //sum all results from one day
        HashMap<LocalDate, Double> percentageResultMap = new HashMap<>();
        for (LocalDate d : map.keySet()) {
            percentageResultMap.put(d, map.get(d).stream().mapToDouble(Result::getPercentageResult).sum());
        }
        //calculate average and ignore results which are less then 30 %
        OptionalDouble average = percentageResultMap.values().stream().filter(a -> a > 30).mapToDouble(a -> a).average();

        if (average.isPresent()) {
            return average.getAsDouble();
        } else {
            return 0.0;
        }
    }
}
