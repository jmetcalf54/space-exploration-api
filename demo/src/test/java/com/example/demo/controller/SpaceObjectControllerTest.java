package com.example.demo.controller;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.service.SpaceObjectService;

@WebMvcTest(SpaceObjectController.class)
public class SpaceObjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SpaceObjectService spaceObjectService;

    @Test
    void testGetAsteroidsReturns200() throws Exception {

        mockMvc.perform(get("/api/asteroids"))
            .andExpect(status().isOk());
    }

    @Test
    void testInvalidThreatLevelReturns400() throws Exception {
        when(spaceObjectService.returnAsteroidsByThreatLevel(7))
            .thenThrow(new IllegalArgumentException(
                "Threat level must be between 1 and 5."
            ));


        mockMvc.perform(get("/api/asteroids/threat/7"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("Threat level must be between 1 and 5."))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.error").value("invalid_argument"));
    }

}
