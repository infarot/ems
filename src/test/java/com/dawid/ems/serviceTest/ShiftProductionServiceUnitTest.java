package com.dawid.ems.serviceTest;

import com.dawid.ems.dao.ShiftProductionDAO;
import com.dawid.ems.entity.ShiftProduction;
import com.dawid.ems.payload.StatisticsFromMonth;
import com.dawid.ems.service.ShiftProductionService;
import com.dawid.ems.service.ShiftProductionServiceImpl;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
public class ShiftProductionServiceUnitTest {

    private ShiftProductionService instance;

    @MockBean
    private ShiftProductionDAO shiftProductionDAO;

    @Before
    public void before() {
        this.instance = new ShiftProductionServiceImpl(shiftProductionDAO);
    }

    @Test
    public void shouldReturnShiftProductionList() throws Exception {
        List<ShiftProduction> list = Collections.singletonList(
                new ShiftProduction("1", LocalDate.of(2019, 3, 25),
                        'A', 2.0, 3.0, 4.0, 5.0,
                        6.0, 7.0));
        Mockito.when(shiftProductionDAO.getAll()).thenReturn(list);
        Assert.assertEquals(instance.getAll(), list);
    }

    @Test
    public void shouldReturnShiftProductionStatisticsFromMonth() throws Exception {
        List<ShiftProduction> list = Arrays.asList(
                new ShiftProduction("1", LocalDate.of(2019, 3, 25),
                        'A', 2.0, 3.0, 4.0, 5.0,
                        6.0, 7.0),
                new ShiftProduction("1", LocalDate.of(2019, 3, 29),
                        'A', 4.0, 3.0, 4.0, 3.0,
                        6.0, 7.0),
                new ShiftProduction("1", LocalDate.of(2015, 3, 25),
                        'A', 20.0, 30.0, 40.0, 50.0,
                        6.0, 7.0),
                new ShiftProduction("1", LocalDate.of(2015, 2, 25),
                        'A', 20.0, 30.0, 40.0, 50.0,
                        6.0, 7.0));


        StatisticsFromMonth statistics = new StatisticsFromMonth(3.0, 4.0, 7.0, 3);

        Mockito.when(shiftProductionDAO.getAll()).thenReturn(list);
        Assert.assertEquals(instance.getStatisticsFromMonth(3, 2019), statistics);
    }
}
