package com.dawid.ems.controller;

import com.dawid.ems.dao.SeamstressDAO;
import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;
import com.dawid.ems.service.SeamstressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SeamstressController {

    private SeamstressService seamstressService;

    @Autowired
    public SeamstressController(SeamstressService seamstressService){
        this.seamstressService = seamstressService;
    }

    @GetMapping("/seamstress")
    public List<Seamstress> getAll(){
        return seamstressService.getAll();
    }


    @GetMapping("/seamstress/results/{seamstressId}")
    public List<Result> getAllResults(@PathVariable int seamstressId){
        Seamstress seamstress = seamstressService.getSingle(seamstressId);
        if (seamstress == null){
            throw new RuntimeException("Seamstress with id " + seamstressId + " not found");
        }
        return seamstressService.getAllResults(seamstressId);
    }

    @GetMapping("/seamstress/{seamstressId}")
    public Seamstress getSingle(@PathVariable int seamstressId){
        Seamstress seamstress = seamstressService.getSingle(seamstressId);
        if (seamstress == null){
            throw new RuntimeException("Seamstress with id " + seamstressId + " not found");
        }
        return seamstressService.getSingle(seamstressId);
    }
}
