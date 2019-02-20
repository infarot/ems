package com.dawid.ems.service;

import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;

import java.util.List;

public interface SeamstressService {
    List<Seamstress> getAll();
    Seamstress getSingle(int id);
    List<Result> getAllResults(int id);
    List<Result> getDailyResults(int id);
}
