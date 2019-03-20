package com.dawid.ems.serviceTest;

import com.dawid.ems.dao.SeamstressDAO;
import com.dawid.ems.entity.ProductionWorker;
import com.dawid.ems.entity.QuiltedIndex;
import com.dawid.ems.entity.QuiltingData;
import com.dawid.ems.repository.QuiltingDataRepository;
import com.dawid.ems.service.QuiltingService;
import com.dawid.ems.service.QuiltingServiceImpl;
import com.dawid.ems.service.SeamstressService;
import com.dawid.ems.service.SeamstressServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class QuiltingServiceUnitTest {

    private QuiltingService instance;
    private ProductionWorker productionWorker;

    @MockBean
    private QuiltingDataRepository quiltingDataRepository;

    @Before
    public void setup() {
        productionWorker = new ProductionWorker("Test", "Test");
        instance = new QuiltingServiceImpl(quiltingDataRepository);
    }

    @Test
    public void isReturningAllQuiltingData() {
        QuiltingData quiltingData = new QuiltingData();
        quiltingData.setOperator(productionWorker);
        quiltingData.setDate(LocalDate.of(2019, 3, 15));
        quiltingData.setId(1);
        quiltingData.setQuiltedIndices(Collections.singletonList(
                new QuiltedIndex("item_name", 200, 2, productionWorker, quiltingData,1)));


        Mockito.when(quiltingDataRepository.findAll()).thenReturn(Collections.singletonList(quiltingData));
        Assert.assertEquals(instance.getAll(), Collections.singletonList(quiltingData));
    }
}
