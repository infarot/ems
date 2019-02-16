package com.dawid.ems.controller;

import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;
import com.dawid.ems.exception.SeamstressNotFoundException;
import com.dawid.ems.service.SeamstressService;
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

    @GetMapping("/seamstress")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Seamstress> getAll() {
        List<Seamstress> seamstresses = seamstressService.getAll();
        Collections.sort(seamstresses);
        return seamstresses;
    }


    @GetMapping("/seamstress/results/{seamstressId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Result> getAllResults(@PathVariable int seamstressId) {
        Seamstress seamstress = seamstressService.getSingle(seamstressId);
        if (seamstress == null) {
            throw new SeamstressNotFoundException("Seamstress with id " + seamstressId + " not found");
        }
        return seamstressService.getAllResults(seamstressId);
    }

    @GetMapping("/seamstress/{seamstressId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Seamstress getSingle(@PathVariable int seamstressId) {
        Seamstress seamstress = seamstressService.getSingle(seamstressId);
        if (seamstress == null) {
            throw new SeamstressNotFoundException("Seamstress with id " + seamstressId + " not found");
        }
        return seamstressService.getSingle(seamstressId);
    }

    @GetMapping("/seamstress/results/average/{seamstressId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Double getAverageResult(@PathVariable int seamstressId) {
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
            return average.getAsDouble();
        } else {
            return 0.0;
        }
    }

    @GetMapping("/seamstress/score/{seamstressId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Double getScore(@PathVariable int seamstressId){
        return seamstressService.getAllResults(seamstressId).stream().mapToDouble(Result::getPercentageResult).sum();
    }
}
