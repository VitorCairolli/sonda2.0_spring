package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.IntegrationTest;
import com.elo7.probe_spring.models.Plateau;
import com.elo7.probe_spring.models.Position;
import com.elo7.probe_spring.repositories.PlateauRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlateauControllerIT extends IntegrationTest {
	private MockMvc mockMvc;

	@Autowired
	private PlateauRepository repository;
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Value("classpath:create-plateau.json")
	private Resource createPlateauJson;

	private static final ObjectMapper jackson = new ObjectMapper();

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	void shouldCreateANewPlateau() throws Exception {
		File json = createPlateauJson.getFile();
		PlateauDTO plateauDTO = jackson.readValue(json, PlateauDTO.class);
		mockMvc.perform(post("/api/plateaus")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jackson.writeValueAsString(plateauDTO)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.probes").isEmpty())
				.andExpect(jsonPath("$.position.y").value("5"))
				.andExpect(jsonPath("$.position.x").value("5"));
	}

	@Test
	void shouldListAllPlateaus() throws Exception {
		repository.save(new Plateau(new Position(5, 5)));
		repository.save(new Plateau(new Position(5, 5)));
		repository.save(new Plateau(new Position(5, 5)));

		mockMvc.perform(get("/api/plateaus")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.plateaus", hasSize(3)))
				.andExpect(jsonPath("$.plateaus").isArray());
	}


	@Test
	void shouldListPlateausById() throws Exception {
		Plateau savedPlateau = repository.save(new Plateau(new Position(5, 5)));

		mockMvc.perform(get("/api/plateaus/" + savedPlateau.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(savedPlateau.getId()))
				.andExpect(jsonPath("$.position.x").value("5"))
				.andExpect(jsonPath("$.position.y").value("5"));

	}


	@Test
	void shouldReturnNotFoundWhenPlateauNonExists() throws Exception {
		mockMvc.perform(get("/api/plateaus/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound());
	}


}
