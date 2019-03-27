package com.dawid.ems.controllerTest;

import com.dawid.ems.entity.ProductionWorker;
import com.dawid.ems.entity.QuiltedIndex;
import com.dawid.ems.entity.QuiltingData;
import com.dawid.ems.payload.QuilterStatistics;
import com.dawid.ems.service.QuiltingService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.spy;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WithMockUser
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuiltingControllerSpringBootTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private QuiltingService quiltingService;

    private MockMvc mvc;

    @Before
    public void setup() {
        ProductionWorker productionWorker = new ProductionWorker("test", "test");
        productionWorker.setId(53);

        QuiltingData quiltingData = new QuiltingData();
        quiltingData.setOperator(productionWorker);
        quiltingData.setDate(LocalDate.of(2019, 3, 15));
        quiltingData.setId(1);
        quiltingData.setQuilterStatistics(new QuilterStatistics(202.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0));
        quiltingData.setQuiltedIndices(Collections.singletonList(
                new QuiltedIndex("MALFORS_80", 200, 2, productionWorker, quiltingData, 1)));

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Mockito.when(quiltingService.getAll()).thenReturn(Collections.singletonList(quiltingData));

        Mockito.when(quiltingService.getAllByDateBetweenAndOperator(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 5, 22), productionWorker)).thenReturn(Collections.singletonList(quiltingData));

    }

    @Test
    public void canGetAllQuiltingData() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/quilting").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].operator.name", Matchers.is("test")))
                .andExpect(jsonPath("$.[0].quilterStatistics.lmtQ1", Matchers.is(202.0)));
    }

    @Test
    public void canGetQuiltingDataByDateIntervalAndOperator() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/quilting/2019-03-01/2019-03-22/53").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].operator.name", Matchers.is("test")))
                .andExpect(jsonPath("$.[0].quilterStatistics.lmtQ1", Matchers.is(202.0)));
    }
}
