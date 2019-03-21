package com.dawid.ems.service;

import com.dawid.ems.entity.QuiltedIndex;
import com.dawid.ems.entity.QuiltingData;
import com.dawid.ems.payload.QuilterStatistics;
import com.dawid.ems.repository.QuiltingDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class QuiltingServiceImpl implements QuiltingService {

    private final QuiltingDataRepository quiltingDataRepository;

    @Autowired
    public QuiltingServiceImpl(QuiltingDataRepository quiltingDataRepository) {
        this.quiltingDataRepository = quiltingDataRepository;
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
    @Transactional
    public List<QuiltingData> getAll() {
        return addQuiltingStatistics(quiltingDataRepository.findAll());
    }
}
