package com.dawid.ems.service;

import com.dawid.ems.dao.ShiftProductionDAO;
import com.dawid.ems.entity.ShiftProduction;
import com.dawid.ems.payload.StatisticsFromMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class ShiftProductionServiceImpl implements ShiftProductionService {

    private final ShiftProductionDAO shiftProductionDAO;

    @Autowired
    public ShiftProductionServiceImpl(ShiftProductionDAO shiftProductionDAO) {
        this.shiftProductionDAO = shiftProductionDAO;
    }

    @Override
    public StatisticsFromMonth getStatisticsFromMonth(int month, int year) {
        StatisticsFromMonth statistics = new StatisticsFromMonth();
        statistics.setMonth(month);
        List<ShiftProduction> list = getAll();
        OptionalDouble optionalAveragePerAll = list.stream().filter(m -> m.getDate().getMonth().getValue() == month && m.getDate().getYear() == year).mapToDouble(ShiftProduction::getPerSeamstress).average();
        optionalAveragePerAll.ifPresent(statistics::setAveragePerAll);
        OptionalDouble optionalAverageResult = list.stream().filter(m -> m.getDate().getMonth().getValue() == month && m.getDate().getYear() == year).mapToDouble(ShiftProduction::getResult).average();
        optionalAverageResult.ifPresent(statistics::setAverageResult);
        OptionalDouble optionalAverageWorkOrganization = list.stream().filter(m -> m.getDate().getMonth().getValue() == month && m.getDate().getYear() == year).mapToDouble(ShiftProduction::getWorkOrganization).average();
        optionalAverageWorkOrganization.ifPresent(statistics::setAverageWorkOrganization);
        return statistics;
    }

    @Override
    @Transactional
    public List<ShiftProduction> getAll() {
        Optional<List<ShiftProduction>> o = shiftProductionDAO.getAll();
        return o.orElse(new ArrayList<>());
    }




}
