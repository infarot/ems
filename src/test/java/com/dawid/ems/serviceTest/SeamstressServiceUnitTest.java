package com.dawid.ems.serviceTest;

import com.dawid.ems.dao.SeamstressDAO;
import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeamstressServiceUnitTest {

    private SeamstressService instance;

    @MockBean
    private SeamstressDAO seamstressDAO;

    @Before
    public void setup() {
        instance = new SeamstressServiceImpl(seamstressDAO);
    }

    @Test
    public void isCalculatingProperAverageResult() {
        Seamstress seamstress = new Seamstress(1,"Test", "Test");
        List<Result> resultList = Arrays.asList(
                new Result("1", LocalDate.of(2004,3,15), 99.0, 'A', seamstress),
                new Result("2", LocalDate.of(2004,3,15), 1.0, 'B', seamstress),
                new Result("3", LocalDate.of(2005,4,20), 100.0, 'A', seamstress));
        seamstress.setResults(resultList);

        Mockito.when(seamstressDAO.getSingle(1)).thenReturn(Optional.of(seamstress));
        Assert.assertEquals(new Seamstress(2,"Test","Test",100.0,200.0).getAverage(), instance.getSingle(1).getAverage());

    }

    @Test
    public void isCalculatingProperScore() {
        Seamstress seamstress = new Seamstress(1,"Test", "Test");
        List<Result> resultList = Arrays.asList(
                new Result("1", LocalDate.of(2004,3,15), 99.0, 'A', seamstress),
                new Result("1", LocalDate.of(2005,4,20), 101.0, 'A', seamstress));
        seamstress.setResults(resultList);

        Mockito.when(seamstressDAO.getSingle(1)).thenReturn(Optional.of(seamstress));
        Assert.assertEquals(new Seamstress(2,"Test","Test",100.0,200.0).getScore(), instance.getSingle(1).getScore());

    }


}
