package com.dawid.ems.service;

import com.dawid.ems.entity.QuiltingData;
import com.dawid.ems.repository.QuiltingDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuiltingServiceImpl implements QuiltingService {

    private final QuiltingDataRepository quiltingDataRepository;

    @Autowired
    public QuiltingServiceImpl(QuiltingDataRepository quiltingDataRepository) {
        this.quiltingDataRepository = quiltingDataRepository;
    }

    @Override
    public List<QuiltingData> getAll() {
        return quiltingDataRepository.findAll();
    }
}
