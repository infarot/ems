package com.dawid.ems.dao;

import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;

import java.util.List;

public interface SeamstressDAO {
    List<Seamstress> getAll();
    Seamstress getSingle();
    List<Result> getAllResults();
}
