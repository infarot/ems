package com.dawid.ems.service;

import com.dawid.ems.dao.SeamstressDAO;
import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Console;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeamstressServiceImpl implements SeamstressService {

    private final SeamstressDAO seamstressDAO;

    private double getAverageResult(int seamstressId) {
        List<Result> results = seamstressDAO.getAllResults(seamstressId);
        //get all results from one day
        Map<LocalDate, List<Result>> map = results.stream().collect(
                Collectors.groupingBy(Result::getDate, HashMap::new, Collectors.toList()));
        //sum all results from one day
        Map<LocalDate, Double> percentageResultMap = new HashMap<>();
        for (LocalDate d : map.keySet()) {
            percentageResultMap.put(d, map.get(d).stream().mapToDouble(Result::getPercentageResult).sum());
        }
        //calculate average and ignore results which are less then 50 %
        OptionalDouble average = percentageResultMap.values().stream().filter(a -> a > 50).mapToDouble(a -> a).average();
        if (average.isPresent()) {
            return Precision.round(average.getAsDouble(), 2);
        } else {
            return 0.0;
        }
    }

    private double getScore(int seamstressId) {
        return Precision.round(seamstressDAO.getAllResults(seamstressId).stream().mapToDouble(Result::getPercentageResult).sum(), 2);
    }


    @Autowired
    public SeamstressServiceImpl(SeamstressDAO seamstressDAO) {
        this.seamstressDAO = seamstressDAO;
    }

    @Override
    @Transactional
    public List<Seamstress> getAll() {
        List<Seamstress> seamstresses = seamstressDAO.getAll();
        seamstresses.forEach(s -> s.setAverage(getAverageResult(s.getId())));
        seamstresses.forEach(s -> s.setScore(getScore(s.getId())));
        seamstresses.sort((a, b) -> b.getAverage().compareTo(a.getAverage()));
        return seamstresses;
    }

    @Override
    @Transactional
    public Seamstress getSingle(int id) {
        Seamstress seamstress = seamstressDAO.getSingle(id);
        seamstress.setAverage(getAverageResult(id));
        seamstress.setScore(getScore(id));
        return seamstress;
    }

    @Override
    @Transactional
    public List<Result> getAllResults(int id) {
        return seamstressDAO.getAllResults(id);
    }

    @Override
    @Transactional
    public List<Result> getDailyResults(int id) {
        List<Result> results = seamstressDAO.getAllResults(id);
        Map<LocalDate, List<Result>> resultMap = results.stream().collect(
                Collectors.groupingBy(Result::getDate, HashMap::new, Collectors.toList()));
        List<Result> resultsList = new ArrayList<>();
        for (LocalDate date : resultMap.keySet()) {
            List<Result> tempResults = resultMap.get(date);
            Result result = new Result();
            for (Result r : tempResults) {
                result.setDate(r.getDate());
                result.addPercentageResult(r.getPercentageResult());
                result.setSeamstress(r.getSeamstress());
                if (result.getId() != null)
                    result.concatenateId(r.getId());
                else {
                    result.setId(r.getId());
                }
                result.setShift('B');
            }
            resultsList.add(result);
        }
        Collections.sort(resultsList);
        return resultsList;
    }

}
