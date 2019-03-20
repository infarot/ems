package com.dawid.ems.controllerTest;

import com.dawid.ems.entity.ProductionWorker;
import com.dawid.ems.entity.QuiltedIndex;
import com.dawid.ems.entity.QuiltingData;
import com.dawid.ems.service.QuiltingService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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

    private ProductionWorker productionWorker;

    private MockMvc mvc;

    @Before
    public void setup() {
        productionWorker = new ProductionWorker("test", "test");
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void canGetAllQuiltingData() throws Exception {
        QuiltingData quiltingData = new QuiltingData();
        quiltingData.setOperator(productionWorker);
        quiltingData.setDate(LocalDate.of(2019, 3, 15));
        quiltingData.setId(1);
        quiltingData.setQuiltedIndices(Collections.singletonList(
                new QuiltedIndex("item_name", 200, 2, productionWorker, quiltingData, 1)));

        Mockito.when(quiltingService.getAll()).thenReturn(Collections.singletonList(quiltingData));

        mvc.perform(MockMvcRequestBuilders.get("/api/quilting").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].operator.name", Matchers.is("test")));
    }
}
