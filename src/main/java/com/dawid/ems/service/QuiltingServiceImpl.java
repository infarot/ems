package com.dawid.ems.service;

import com.dawid.ems.entity.ProductionWorker;
import com.dawid.ems.entity.QuiltedIndex;
import com.dawid.ems.entity.QuiltingData;
import com.dawid.ems.payload.QuilterStatistics;
import com.dawid.ems.payload.QuiltingStatisticsFromMonth;
import com.dawid.ems.repository.ProductionWorkerRepository;
import com.dawid.ems.repository.QuiltingDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuiltingServiceImpl implements QuiltingService {

    private final QuiltingDataRepository quiltingDataRepository;

    private final ProductionWorkerRepository productionWorkerRepository;

    @Autowired
    public QuiltingServiceImpl(QuiltingDataRepository quiltingDataRepository, ProductionWorkerRepository productionWorkerRepository) {
        this.quiltingDataRepository = quiltingDataRepository;
        this.productionWorkerRepository = productionWorkerRepository;
    }

    private List<QuiltingData> addQuiltingStatistics(List<QuiltingData> quiltingData) {
        quiltingData.forEach(q -> q.setQuilterStatistics(
                new QuilterStatistics
                        (q.getQuiltedIndices().stream().filter(i -> i.getQuilterNumber() == 1).mapToDouble(QuiltedIndex::getLmt).sum(),

                                (q.getQuiltedIndices().stream().filter(i -> i.getQuilterNumber() == 2).mapToDouble(QuiltedIndex::getLmt)).sum(),

                                (q.getQuiltedIndices().stream().filter(i -> i.getQuilterNumber() == 3).mapToDouble(QuiltedIndex::getLmt)).sum(),

                                (q.getQuiltedIndices().stream().filter(i -> i.getQuilterNumber() == 1).mapToDouble(QuiltedIndex::getLossLmt).sum()) /
                                        ((q.getQuiltedIndices().stream().filter(i -> i.getQuilterNumber() == 1).mapToDouble(QuiltedIndex::getLossLmt).sum()) +
                                                (q.getQuiltedIndices().stream().filter(i -> i.getQuilterNumber() == 1).mapToDouble(QuiltedIndex::getLmt).sum())),

                                (q.getQuiltedIndices().stream().filter(i -> i.getQuilterNumber() == 2).mapToDouble(QuiltedIndex::getLossLmt).sum()) /
                                        ((q.getQuiltedIndices().stream().filter(i -> i.getQuilterNumber() == 2).mapToDouble(QuiltedIndex::getLossLmt).sum()) +
                                                (q.getQuiltedIndices().stream().filter(i -> i.getQuilterNumber() == 2).mapToDouble(QuiltedIndex::getLmt).sum())),

                                (q.getQuiltedIndices().stream().filter(i -> i.getQuilterNumber() == 3).mapToDouble(QuiltedIndex::getLossLmt).sum()) /
                                        ((q.getQuiltedIndices().stream().filter(i -> i.getQuilterNumber() == 3).mapToDouble(QuiltedIndex::getLossLmt).sum()) +
                                                (q.getQuiltedIndices().stream().filter(i -> i.getQuilterNumber() == 3).mapToDouble(QuiltedIndex::getLmt).sum())),

                                (q.getQuiltedIndices().stream().mapToDouble(QuiltedIndex::getLmt).sum()),

                                (q.getQuiltedIndices().stream().mapToDouble(QuiltedIndex::getLossLmt).sum()) /
                                        ((q.getQuiltedIndices().stream().mapToDouble(QuiltedIndex::getLossLmt).sum()) +
                                                (q.getQuiltedIndices().stream().mapToDouble(QuiltedIndex::getLmt).sum())))));
        return quiltingData;
    }

    @Override
    public List<QuiltingData> getAll() {
        return addQuiltingStatistics(quiltingDataRepository.findAll());
    }

    @Override
    public List<QuiltingData> getAllByDateBetweenAndOperator(LocalDate from, LocalDate to, ProductionWorker operator) {
        if (from.isAfter(to)) {
            throw new RuntimeException("Invalid date interval");
        }
        return addQuiltingStatistics(quiltingDataRepository.getAllByDateBetweenAndOperator(from, to, operator));
    }

    @Override
    public ProductionWorker getProductionWorker(Integer id) {
        return productionWorkerRepository.getOne(id);
    }

    @Override
    public QuiltingStatisticsFromMonth getQuiltingStatisticsFromMonth(int month, int year) {
        List<QuiltingData> quiltingDataList = new ArrayList<>(getAll());
        double monthLmtOfTotalLoss = quiltingDataList.stream()
                .filter(a -> a.getDate().getYear() == year && a.getDate().getMonth().getValue() == month)
                .mapToDouble(a -> a.getQuiltedIndices().stream().mapToDouble(QuiltedIndex::getLossLmt).sum()).sum();
        double monthLmt = quiltingDataList.stream()
                .filter(a -> a.getDate().getYear() == year && a.getDate().getMonth().getValue() == month)
                .mapToDouble(a -> a.getQuilterStatistics().getTotalLmt()).sum();
        double averageMonthLmt = quiltingDataList.stream()
                .filter(a -> a.getDate().getYear() == year && a.getDate().getMonth().getValue() == month)
                .mapToDouble(a -> a.getQuilterStatistics().getTotalLmt()).average().orElse(0.0);
        return new QuiltingStatisticsFromMonth(averageMonthLmt, monthLmtOfTotalLoss / (monthLmt + monthLmtOfTotalLoss), month);
    }

    @Override
    public QuiltingStatisticsFromMonth getQuiltingStatisticsFromMonthByOperator(int month, int year, int id) {
        List<QuiltingData> quiltingDataList = new ArrayList<>(getAll());
        double monthLmtOfTotalLoss = quiltingDataList.stream()
                .filter(a -> a.getDate().getYear() == year && a.getDate().getMonth().getValue() == month && a.getOperator().getId() == id)
                .mapToDouble(a -> a.getQuiltedIndices().stream().mapToDouble(QuiltedIndex::getLossLmt).sum()).sum();
        double monthLmt = quiltingDataList.stream()
                .filter(a -> a.getDate().getYear() == year && a.getDate().getMonth().getValue() == month && a.getOperator().getId() == id)
                .mapToDouble(a -> a.getQuilterStatistics().getTotalLmt()).sum();
        double averageMonthLmt = quiltingDataList.stream()
                .filter(a -> a.getDate().getYear() == year && a.getDate().getMonth().getValue() == month && a.getOperator().getId() == id)
                .mapToDouble(a -> a.getQuilterStatistics().getTotalLmt()).average().orElse(0.0);
        return new QuiltingStatisticsFromMonth(averageMonthLmt, monthLmtOfTotalLoss / (monthLmt + monthLmtOfTotalLoss), month);
    }


}
