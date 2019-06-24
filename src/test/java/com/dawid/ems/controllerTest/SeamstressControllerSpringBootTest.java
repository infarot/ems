package com.dawid.ems.controllerTest;

import com.dawid.ems.entity.Result;
import com.dawid.ems.entity.Seamstress;
import com.dawid.ems.exception.SeamstressNotFoundException;
import com.dawid.ems.service.SeamstressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser
public class SeamstressControllerSpringBootTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SeamstressService seamstressService;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/api/seamstress/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {

        given(seamstressService.getSingle(2))
                .willReturn(new Seamstress(2, "SName", "SLastName", 15.0, 14.0));

        mvc.perform(
                get("/api/seamstress/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.average", Matchers.is(15.0)))
                .andExpect(jsonPath("$.score", Matchers.is(14.0)))
                .andExpect(jsonPath("$.id", Matchers.is(2)))
                .andExpect(jsonPath("$.lastName", Matchers.is("SLastName")))
                .andExpect(jsonPath("$.name", Matchers.is("SName")));

    }

    @Test
    public void canRetrieveByIdWhenDoesNotExist() throws Exception {


        given(seamstressService.getSingle(2))
                .willThrow(new SeamstressNotFoundException("Seamstress not found"));


        MockHttpServletResponse response = mvc.perform(
                get("/api/seamstress/2").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).contains("Seamstress not found");
    }

    @Test
    public void canRetrieveAllResultsBySeamstressId() throws Exception {

        Seamstress seamstress = new Seamstress(2, "SName", "SLastName", 15.0, 14.0);
        Result result = new Result("1", LocalDate.of(2019, 3, 19), 12.0, 'A', seamstress);
        seamstress.setResults(Collections.singletonList(result));

        given(seamstressService.getAllResults(2))
                .willReturn(seamstress.getResults());


        mvc.perform(
                get("/api/seamstress/allResults/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.[0].id", Matchers.is("1")))
                .andExpect(jsonPath("$.[0].date", Matchers.is("2019-03-19")))
                .andExpect(jsonPath("$.[0].percentageResult", Matchers.is(12.0)))
                .andExpect(jsonPath("$.[0].shift", Matchers.containsString("A")))
                .andExpect(jsonPath("$.[0].seamstress.name", Matchers.is("SName")))
                .andExpect(jsonPath("$.[0].seamstress.lastName", Matchers.is("SLastName")));
    }

    @Test
    public void canRetrieveAllResultsWhenSeamstressDoesNotExist() throws Exception {


        given(seamstressService.getAllResults(2))
                .willThrow(new SeamstressNotFoundException("Seamstress not found"));


        MockHttpServletResponse response = mvc.perform(
                get("/api/seamstress/allResults/2").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).contains("Seamstress not found");
    }

    @Test
    public void canRetrieveDailyResultsBySeamstressId() throws Exception {

        Seamstress seamstress = new Seamstress(2, "SName", "SLastName", 15.0, 14.0);
        Result result = new Result("1", LocalDate.of(2019, 3, 19), 12.0, 'A', seamstress);
        seamstress.setResults(Collections.singletonList(result));

        given(seamstressService.getDailyResults(2))
                .willReturn(seamstress.getResults());


        mvc.perform(
                get("/api/seamstress/dailyResults/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.[0].id", Matchers.is("1")))
                .andExpect(jsonPath("$.[0].date", Matchers.is("2019-03-19")))
                .andExpect(jsonPath("$.[0].percentageResult", Matchers.is(12.0)))
                .andExpect(jsonPath("$.[0].shift", Matchers.containsString("A")))
                .andExpect(jsonPath("$.[0].seamstress.name", Matchers.is("SName")))
                .andExpect(jsonPath("$.[0].seamstress.lastName", Matchers.is("SLastName")));
    }

    @Test
    public void canRetrieveDailyResultsWhenSeamstressDoesNotExist() throws Exception {


        given(seamstressService.getDailyResults(2))
                .willThrow(new SeamstressNotFoundException("Seamstress not found"));


        MockHttpServletResponse response = mvc.perform(
                get("/api/seamstress/dailyResults/2").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).contains("Seamstress not found");
    }

    @Test
    public void canRetrieveSeamstressDataFromDateInterval() throws Exception {

        Seamstress seamstress = new Seamstress(2, "SName", "SLastName", 15.0, 14.0);

        given(seamstressService.getFromDateInterval(LocalDate.of(2019, 3, 19), LocalDate.of(2019, 3, 19)))
                .willReturn(Collections.singletonList(seamstress));


        mvc.perform(
                get("/api/seamstress/2019-03-19/2019-03-19").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.[0].average", Matchers.is(15.0)))
                .andExpect(jsonPath("$.[0].score", Matchers.is(14.0)))
                .andExpect(jsonPath("$.[0].id", Matchers.is(2)))
                .andExpect(jsonPath("$.[0].lastName", Matchers.is("SLastName")))
                .andExpect(jsonPath("$.[0].name", Matchers.is("SName")));
    }

    @Test
    public void canRetrieveEmptySeamstressListFromDateInterval() throws Exception {


        given(seamstressService.getFromDateInterval(LocalDate.of(2053, 3, 19), LocalDate.of(2083, 3, 19)))
                .willReturn(Collections.emptyList());


        mvc.perform(
                get("/api/seamstress/2053-03-19/2083-03-19").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void canRetrieveAllSeamstressData() throws Exception {

        Seamstress seamstress = new Seamstress(2, "SName", "SLastName", 15.0, 14.0);

        given(seamstressService.getAll())
                .willReturn(Collections.singletonList(seamstress));


        mvc.perform(
                get("/api/seamstress").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.[0].average", Matchers.is(15.0)))
                .andExpect(jsonPath("$.[0].score", Matchers.is(14.0)))
                .andExpect(jsonPath("$.[0].id", Matchers.is(2)))
                .andExpect(jsonPath("$.[0].lastName", Matchers.is("SLastName")))
                .andExpect(jsonPath("$.[0].name", Matchers.is("SName")));
    }

    @Test
    public void isAbleToAddNewSeamstress() throws Exception {
        Seamstress seamstress = new Seamstress();
        seamstress.setName("TestName");
        seamstress.setLastName("TestLastName");
        given(seamstressService.createNew(seamstress)).willReturn(1);

        mvc.perform(post("/api/seamstress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(seamstress)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", Matchers.is("Seamstress with id: 1 has been created")))
                .andExpect(status().isCreated());
    }

    @Test
    public void isUnableToAddNewSeamstressWithInvalidCredentials() throws Exception {
        Seamstress seamstress = new Seamstress();
        seamstress.setName("a");
        seamstress.setLastName("b");

        mvc.perform(post("/api/seamstress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(seamstress)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().is4xxClientError());
        Mockito.verify(seamstressService, Mockito.never()).createNew(seamstress);
    }
}