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


}
