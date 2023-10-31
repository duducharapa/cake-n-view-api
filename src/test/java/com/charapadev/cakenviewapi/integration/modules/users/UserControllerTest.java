package com.charapadev.cakenviewapi.integration.modules.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.charapadev.cakenviewapi.modules.users.UserRepository;
import com.charapadev.cakenviewapi.modules.users.UserService;
import com.charapadev.cakenviewapi.modules.users.dtos.CreateUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void createUser() throws Exception {
        String createJSON = new ObjectMapper().writer().writeValueAsString(
            new CreateUserDTO("teste@teste.com", "Eduardo Charapa", "teste123")
        );

        // Perform POST /users endpoint
        mvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(createJSON)
        ).andExpect(status().isCreated());

        // Checks if the user really is registered
        assertDoesNotThrow(
            () -> userRepository.findByEmail("teste@teste.com").orElseThrow()
        );
    }

    @Test
    public void duplicatedEmailOnCreateUser() throws Exception {
        CreateUserDTO createData = new CreateUserDTO("teste@teste.com", "Eduardo Charapa", "teste123");
        String createJSON = new ObjectMapper().writer().writeValueAsString(createData);

        // Add the user before call the creation endpoint
        userService.create(createData);

        // Perform POST /users endpoint
        mvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(createJSON)
        ).andExpect(status().isUnprocessableEntity());

        // Check if the user is not added on application
        // Because we added only one user, must have the same value yet
        assertEquals(1, userRepository.findAll().size());
    }

}
