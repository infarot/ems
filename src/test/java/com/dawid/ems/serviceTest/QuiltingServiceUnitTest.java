package com.dawid.ems.serviceTest;

import com.dawid.ems.entity.ProductionWorker;
import com.dawid.ems.entity.QuiltedIndex;
import com.dawid.ems.entity.QuiltingData;
import com.dawid.ems.repository.ProductionWorkerRepository;
import com.dawid.ems.repository.QuiltingDataRepository;
import com.dawid.ems.service.QuiltingService;
import com.dawid.ems.service.QuiltingServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
public class QuiltingServiceUnitTest {

    private QuiltingService instance;
    private ProductionWorker productionWorker;

    @MockBean
    private QuiltingDataRepository quiltingDataRepository;

    @MockBean
    private ProductionWorkerRepository productionWorkerRepository;

    @Before
    public void setup() {
        productionWorker = new ProductionWorker("Test", "Test");
        instance = new QuiltingServiceImpl(quiltingDataRepository, productionWorkerRepository);
    }

    @Test
    public void isReturningAllQuiltingData() {
        QuiltingData quiltingData = new QuiltingData();
        quiltingData.setOperator(productionWorker);
        quiltingData.setDate(LocalDate.of(2019, 3, 15));
        quiltingData.setId(1);
        quiltingData.setQuiltedIndices(Collections.singletonList(
                new QuiltedIndex("MALFORS_80", 200, 2, productionWorker, quiltingData, 1)));


        Mockito.when(quiltingDataRepository.findAll()).thenReturn(Collections.singletonList(quiltingData));
        Assert.assertEquals(instance.getAll(), Collections.singletonList(quiltingData));
    }

    @Test
    public void isReturningQuiltingDataFromDateInterval() {
        QuiltingData quiltingData = new QuiltingData();
        quiltingData.setOperator(productionWorker);
        quiltingData.setDate(LocalDate.of(2019, 3, 15));
        quiltingData.setId(1);
        quiltingData.setQuiltedIndices(Collections.singletonList(
                new QuiltedIndex("MALFORS_80", 200, 2, productionWorker, quiltingData, 1)));
        Mockito.when(quiltingDataRepository.getAllByDateBetweenAndOperator(LocalDate.of(2019, 3, 1), LocalDate.of(2019, 4, 2), productionWorker)).thenReturn(Collections.singletonList(quiltingData));
        Assert.assertEquals(instance.getAllByDateBetweenAndOperator(LocalDate.of(2019, 3, 1), LocalDate.of(2019, 4, 2), productionWorker), Collections.singletonList(quiltingData));
    }
}
