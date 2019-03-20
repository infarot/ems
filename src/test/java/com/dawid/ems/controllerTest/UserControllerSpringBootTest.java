package com.dawid.ems.controllerTest;

import com.dawid.ems.payload.UserSummary;
import com.dawid.ems.repository.UserRepository;
import com.dawid.ems.security.UserPrincipal;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerSpringBootTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserRepository userRepository;

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
    public void isReturningFalseIfUserExists() throws Exception {
        given(userRepository.existsByUsername("test"))
                .willReturn(true);

        mvc.perform(get("/api/user/checkUsernameAvailability?username=test").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));
    }

    @WithMockUser("test")
    @Test
    public void isReturningTrueIfUserDoestNotExists() throws Exception {
        given(userRepository.existsByUsername("test"))
                .willReturn(false);

        mvc.perform(get("/api/user/checkUsernameAvailability?username=test").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));
    }
}
