package com.dawid.ems.dao;

import com.dawid.ems.entity.ShiftProduction;

import java.util.List;
import java.util.Optional;

public interface ShiftProductionDAO {

    Optional<List<ShiftProduction>> getAll();

}
