package com.dawid.ems.service;

import com.dawid.ems.dao.SeamstressDAO;
import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;
import com.dawid.ems.exception.ResourceNotFoundException;
import com.dawid.ems.exception.SeamstressNotFoundException;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeamstressServiceImpl implements SeamstressService {

    private final SeamstressDAO seamstressDAO;

    @Autowired
    public SeamstressServiceImpl(SeamstressDAO seamstressDAO) {
        this.seamstressDAO = seamstressDAO;
    }

    private double getAverageResult(int seamstressId) {
        List<Result> results = getAllResults(seamstressId);
        return calculateAverage(results);
    }

    private double getScore(int seamstressId) {
        List<Result> results = getAllResults(seamstressId);
        return Precision.round(results.stream().mapToDouble(Result::getPercentageResult).sum(), 2);
    }

    private Double calculateAverage(List<Result> results) {
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

    private Double getAverageResultFromDateInterval(int seamstressId, LocalDate from, LocalDate to) {
        List<Result> results = getAllResultsFromDateInterval(seamstressId, from, to);
        return calculateAverage(results);
    }

    private Double getScoreFromDateInterval(int seamstressId, LocalDate from, LocalDate to) {
        return Precision.round(getAllResultsFromDateInterval(seamstressId, from, to).stream().mapToDouble(Result::getPercentageResult).sum(), 2);
    }

    @Override
    public List<Result> getDailyResults(int id) {
        List<Result> results = getAllResults(id);
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
                if (result.getId() != null) {
                    result.concatenateId(r.getId());
                    result.setShift('B');
                } else {
                    result.setId(r.getId());
                    result.setShift(r.getShift());
                }

            }
            resultsList.add(result);
        }
        Collections.sort(resultsList);
        return resultsList;
    }

    @Override
    public List<Seamstress> getFromDateInterval(LocalDate from, LocalDate to) {
        List<Seamstress> seamstresses = getAll();

        seamstresses.forEach(s -> s.setAverage(getAverageResultFromDateInterval(s.getId(), from, to)));
        seamstresses.forEach(s -> s.setScore(getScoreFromDateInterval(s.getId(), from, to)));
        seamstresses.sort((a, b) -> b.getAverage().compareTo(a.getAverage()));

        return seamstresses;

    }

    @Override
    @Transactional
    public List<Result> getAllResults(int id) {
        Seamstress seamstress = seamstressDAO.getSingle(id).orElseThrow(() -> new SeamstressNotFoundException("Seamstress with id " + id + " not found"));
        return seamstress.getResults();
    }

    @Override
    @Transactional
    public List<Result> getAllResultsFromDateInterval(int seamstressId, LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new RuntimeException("Invalid date interval");
        }
        return seamstressDAO.getAllResultsFromDateInterval(seamstressId, from, to);
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
        Seamstress seamstress = seamstressDAO.getSingle(id).orElseThrow(() -> new SeamstressNotFoundException("Seamstress with id " + id + " not found"));
        seamstress.setAverage(getAverageResult(id));
        seamstress.setScore(getScore(id));
        return seamstress;
    }


}
