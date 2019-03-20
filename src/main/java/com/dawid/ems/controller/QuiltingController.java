package com.dawid.ems.controller;

import com.dawid.ems.entity.QuiltingData;
import com.dawid.ems.service.QuiltingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
