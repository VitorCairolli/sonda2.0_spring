package com.elo7.probe_spring.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
class PlateauControllerIT {

    @Container
    private static final MySQLContainer MY_SQL_CONTAINER =
            new MySQLContainer(DockerImageName.parse("mysql:8.0.30"))
                    .withDatabaseName("sonda-spring-db")
                    .withPassword("123456")
                    .withUsername("root");

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Value("classpath:json")
    private Resource resource;

    private static final ObjectMapper jackson = new ObjectMapper();

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void shouldPersistP() throws Exception {
        File json = resource.getFile();
        PlateauDTO plateauDTO = jackson.readValue(json, PlateauDTO.class);
        mockMvc.perform(post("/api/plateaus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jackson.writeValueAsString(plateauDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", () -> MY_SQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.username", () -> "root");
        registry.add("spring.datasource.password", () -> "123456");
    }
}
