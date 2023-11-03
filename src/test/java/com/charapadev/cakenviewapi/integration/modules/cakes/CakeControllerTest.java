package com.charapadev.cakenviewapi.integration.modules.cakes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.charapadev.cakenviewapi.modules.cakes.CakeMapper;
import com.charapadev.cakenviewapi.modules.cakes.dtos.CreateCakeDTO;
import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.cakes.repositories.CakeRepository;
import com.charapadev.cakenviewapi.modules.cakes.repositories.CakeViewRepository;
import com.charapadev.cakenviewapi.modules.cakes.services.CakeService;
import com.charapadev.cakenviewapi.modules.cakes.services.DailyCakeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.NoSuchElementException;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CakeControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CakeRepository cakeRepository;

    @Autowired
    private DailyCakeService dailyCakeService;

    @Autowired
    private CakeViewRepository cakeViewRepository;

    @Autowired
    private CakeMapper cakeMapper;

    @Autowired
    private CakeService cakeService;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void listCakes() throws Exception {
        // Currently quantity of registered cakes
        int currentCakes = cakeRepository.findAll().size();

        // Perform GET /cakes endpoint
        mvc.perform(get("/cakes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(currentCakes));
    }

    @Test
    public void findDailyCake() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String expectedJSON = mapper.writer().writeValueAsString(
            cakeMapper.toShowDaily(dailyCakeService.getCurrent())
        );

        // Perform GET /cakes/daily endpoint
        mvc.perform(get("/cakes/daily"))
            .andExpect(status().isOk()).andExpect(content().json(expectedJSON));
    }

    @Test
    public void findTrendingCakes() throws Exception {
        // Quantity of trending cakes currently found
        int trendingCakesSize = cakeViewRepository.findTrendingCakes().size();

        // Perform GET /cakes/trending endpoint
        mvc.perform(get("/cakes/trendings"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(trendingCakesSize));
    }

    @Test
    public void searchCake() throws Exception {
        // Valid cake identifier to search
        Long validCakeID = 1L;
        String expectedJSON = new ObjectMapper().writer().writeValueAsString(
            cakeService.find(validCakeID)
        );

        String url = String.format("/cakes/%s", validCakeID);

        // Perform GET /cakes/{id} endpoint
        mvc.perform(get(url))
            .andExpect(status().isOk()).andExpect(content().json(expectedJSON));
    }

    @Test
    public void notFoundOnSearchCake() throws Exception {
        // Invalid cake identifier
        Long invalidCakeID = 5L;

        String url = String.format("/cakes/%s", invalidCakeID);

        // Perform GET /cakes/{id} endpoint
        mvc.perform(get(url))
            .andExpect(status().isNotFound());

        // Checks if the application really not have this cake stored
        assertThrows(NoSuchElementException.class, () -> cakeRepository.findById(invalidCakeID).orElseThrow());
    }

    @Test
    public void createCake() throws Exception {
        // Currently quantity of registered cakes
        int currentCakes = cakeRepository.findAll().size();

        String createJSON = new ObjectMapper().writer().writeValueAsString(
            new CreateCakeDTO("Bolo de arroz", "Bolo macio de arroz", "http://localhost:8080/teste.jpg")
        );

        // Perform POST /cakes endpoint
        mvc.perform(
            post("/cakes").contentType(MediaType.APPLICATION_JSON).content(createJSON)
        ).andExpect(status().isCreated());

        // Checks if the cake found was added
        assertEquals(currentCakes + 1, cakeRepository.findAll().size());
    }

    @Test
    public void removeCake() throws Exception {
        CreateCakeDTO createDTO = new CreateCakeDTO("Bolo de teste", "O quão saboroso um teste é?", "http://localhost:8080/teste.jpg");
        // Creating the cake to be deleted
        Cake createdCake = cakeService.create(createDTO);

        String url = String.format("/cakes/%s", createdCake.getId());

        // Perform DELETE /cakes/{id} endpoint
        mvc.perform(delete(url))
            .andExpect(status().isNoContent());

        // Checks if the cake is not persisted on database yet
        assertThrows(
            NoSuchElementException.class,
            () -> cakeRepository.findById(createdCake.getId()).orElseThrow()
        );
    }

    @Test
    public void notFoundOnRemoveCake() throws Exception {
        // Invalid cake identifier
        Long invalidCakeID = 5L;

        String url = String.format("/cakes/%s", invalidCakeID);

        // Perform DELETE /cakes/{id} endpoint
        mvc.perform(delete(url))
            .andExpect(status().isNotFound());

        assertThrows(
            NoSuchElementException.class,
            () -> cakeRepository.findById(invalidCakeID).orElseThrow()
        );
    }

}
