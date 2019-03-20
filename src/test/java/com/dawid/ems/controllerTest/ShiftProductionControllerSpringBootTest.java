package com.dawid.ems.controllerTest;


import com.dawid.ems.entity.ShiftProduction;
import com.dawid.ems.payload.StatisticsFromMonth;
import com.dawid.ems.service.ShiftProductionService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShiftProductionControllerSpringBootTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ShiftProductionService shiftProductionService;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser("test")
    @Test
    public void canRetrieveAllShiftProductionData() throws Exception {

        ShiftProduction shiftProduction = new ShiftProduction("1", LocalDate.of(2019, 3, 25), 'A', 2.0, 3.0, 4.0, 5.0, 6.0, 7.0);

        given(shiftProductionService.getAll())
                .willReturn(Collections.singletonList(shiftProduction));


        mvc.perform(
                get("/api/shiftProduction").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.[0].id", Matchers.is("1")))
                .andExpect(jsonPath("$.[0].date", Matchers.is("2019-03-25")))
                .andExpect(jsonPath("$.[0].perSeamstress", Matchers.is(2.0)))
                .andExpect(jsonPath("$.[0].shift", Matchers.containsString("A")))
                .andExpect(jsonPath("$.[0].perSeamstressQc", Matchers.is(3.0)))
                .andExpect(jsonPath("$.[0].perEmployee", Matchers.is(4.0)))
                .andExpect(jsonPath("$.[0].result", Matchers.is(5.0)))
                .andExpect(jsonPath("$.[0].potentialUtilization", Matchers.is(6.0)))
                .andExpect(jsonPath("$.[0].workOrganization", Matchers.is(7.0)));
    }

    @WithMockUser("test")
    @Test
    public void canRetrieveStatisticsFromMonth() throws Exception {

        given(shiftProductionService.getStatisticsFromMonth(3,2019))
                .willReturn(new StatisticsFromMonth(50.0, 280.0, 75.0, 3));


        mvc.perform(
                get("/api/shiftProduction/monthStatistics/3/2019").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.averagePerAll", Matchers.is(50.0)))
                .andExpect(jsonPath("$.averageResult", Matchers.is(280.0)))
                .andExpect(jsonPath("$.averageWorkOrganization", Matchers.is(75.0)))
                .andExpect(jsonPath("$.month", Matchers.is(3)));
    }
}
