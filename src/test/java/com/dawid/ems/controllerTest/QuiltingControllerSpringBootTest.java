package com.dawid.ems.controllerTest;


import com.dawid.ems.entity.ProductionWorker;
import com.dawid.ems.entity.QuiltedIndex;
import com.dawid.ems.entity.QuiltingData;
import com.dawid.ems.payload.QuilterStatistics;
import com.dawid.ems.payload.QuiltingStatisticsFromMonth;
import com.dawid.ems.service.QuiltingService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser
public class QuiltingControllerSpringBootTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private QuiltingService quiltingService;

    private QuiltingData quiltingData;

    @MockBean
    private ProductionWorker operator;


    private MockMvc mvc;

    @Before
    public void setup() {
        operator = new ProductionWorker("test", "test");
        operator.setId(53);

        quiltingData = new QuiltingData();
        quiltingData.setOperator(operator);
        quiltingData.setDate(LocalDate.of(2019, 3, 15));
        quiltingData.setId(1);
        quiltingData.setQuilterStatistics(new QuilterStatistics(202.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0));
        quiltingData.setQuiltedIndices(Collections.singletonList(
                new QuiltedIndex("MALFORS_80", 200, 2, operator, quiltingData, 1)));

        given(quiltingService.getAll()).willReturn(Collections.singletonList(quiltingData));
        given(quiltingService.getProductionWorker(53)).willReturn(new ProductionWorker("test", "test"));
        given(quiltingService.getQuiltingStatisticsFromMonth(3, 2019)).willReturn(new QuiltingStatisticsFromMonth(30.0, 4.0, 3));
        given(quiltingService.getAllByDateBetweenAndOperator(LocalDate.of(2019, 3, 1), LocalDate.of(2019, 3, 22), new ProductionWorker("test", "test"))).willReturn(Collections.singletonList(quiltingData));
        given(quiltingService.getQuiltingStatisticsFromMonthByOperator(3, 2019, 53)).willReturn(new QuiltingStatisticsFromMonth(30.0, 4.0, 3));

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

    }

    @Test
    public void canGetAllQuiltingData() throws Exception {

        mvc.perform(get("/api/quilting").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].operator.name", Matchers.is("test")))
                .andExpect(jsonPath("$.[0].quilterStatistics.lmtQ1", Matchers.is(202.0)));
    }


    @Test
    public void canGetQuiltingDataByDateIntervalAndOperator() throws Exception {

        mvc.perform(get("/api/quilting/2019-03-01/2019-03-22/53").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].operator.name", Matchers.is("test")))
                .andExpect(jsonPath("$.[0].quilterStatistics.lmtQ1", Matchers.is(202.0)));
    }


    @Test
    public void canGetQuiltingStatisticsFromMonth() throws Exception {

        mvc.perform(get("/api/quilting/statistics/3/2019").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageLmt", Matchers.is(30.0)))
                .andExpect(jsonPath("$.averageTotalLoss", Matchers.is(4.0)))
                .andExpect(jsonPath("$.month", Matchers.is(3)));
    }

    @Test
    public void canGetQuiltingStatisticsFromMonthAndOperator() throws Exception {

        mvc.perform(get("/api/quilting/statistics/3/2019/53").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageLmt", Matchers.is(30.0)))
                .andExpect(jsonPath("$.averageTotalLoss", Matchers.is(4.0)))
                .andExpect(jsonPath("$.month", Matchers.is(3)));

    }
}
