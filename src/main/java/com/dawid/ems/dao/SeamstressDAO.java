package com.dawid.ems.dao;

import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SeamstressDAO {
    List<Seamstress> getAll();

    Optional<Seamstress> getSingle(int id);

    List<Result> getAllResultsFromDateInterval(int seamstressId, LocalDate from, LocalDate to);

    int save(Seamstress seamstress);
}
