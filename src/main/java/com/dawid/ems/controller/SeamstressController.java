package com.dawid.ems.controller;

import com.dawid.ems.dao.SeamstressDAO;
import com.dawid.ems.entity.Seamstress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeamstressController {

    @Autowired
    SeamstressDAO seamstressDAO;

    @GetMapping(name = "/seamstress")
    public List<Seamstress> getAll(){
        return seamstressDAO.getAll();
    }
}
