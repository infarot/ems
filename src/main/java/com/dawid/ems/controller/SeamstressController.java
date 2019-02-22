package com.dawid.ems.controller;

import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;
import com.dawid.ems.exception.SeamstressNotFoundException;
import com.dawid.ems.service.SeamstressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

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

    @GetMapping("/seamstress/{from}/{to}")
    public List<Seamstress> getAllFromDateInterval(@PathVariable String from, @PathVariable String to) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate fromDate = LocalDate.parse(from, formatter);
            LocalDate toDate = LocalDate.parse(to, formatter);
            return seamstressService.getFromDateInterval(fromDate, toDate);
        } catch (DateTimeParseException e) {
            e.getParsedString();
            throw e;
        }
    }


    @GetMapping("/seamstress/allResults/{seamstressId}")
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

    @GetMapping("/seamstress/dailyResults/{seamstressId}")
    public List<Result> getDailyResults(@PathVariable int seamstressId) {
        return seamstressService.getDailyResults(seamstressId);
    }


}
