package com.dawid.ems.controller;

import com.dawid.ems.dao.SeamstressDAO;
import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SeamstressController {

    @Autowired
    SeamstressDAO seamstressDAO;

    @GetMapping("/seamstress")
    public List<Seamstress> getAll(){
        return seamstressDAO.getAll();
    }


    @GetMapping("/seamstress/results/{seamstressId}")
    public List<Result> getAllResults(@PathVariable int seamstressId){
        System.out.println(seamstressDAO.getAllResults(seamstressId));
        return seamstressDAO.getAllResults(seamstressId);
    }
}
