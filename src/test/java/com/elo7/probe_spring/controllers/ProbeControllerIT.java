package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.IntegrationTest;
import com.elo7.probe_spring.models.Direction;
import com.elo7.probe_spring.models.Plateau;
import com.elo7.probe_spring.models.Position;
import com.elo7.probe_spring.models.Probe;
import com.elo7.probe_spring.repositories.PlateauRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProbeControllerIT extends IntegrationTest {
	private MockMvc mockMvc;

	@Autowired
	private PlateauRepository repository;
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Value("classpath:create-probe.json")
	private Resource createProbe;

	@Value("classpath:move-probe.json")
	private Resource moveProbe;


	private static final ObjectMapper jackson = new ObjectMapper();

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}


	@Test
	void shouldCreateANewProbe() throws Exception {
		Plateau savedPlateau = repository.save(new Plateau(new Position(5, 5)));
		File json = createProbe.getFile();
		ProbeDTO probeDto = jackson.readValue(json, ProbeDTO.class);
		mockMvc.perform(post("/api/plateaus/" + savedPlateau.getId() + "/probes")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jackson.writeValueAsString(probeDto)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.direction").value(probeDto.direction().name()))
				.andExpect(jsonPath("$.position.y").value(probeDto.position().y()))
				.andExpect(jsonPath("$.position.x").value(probeDto.position().x()));
	}

	@Test
	void shouldListProbeById() throws Exception {
		Plateau plateau = new Plateau(new Position(5, 5));
		Probe probe = new Probe(new Position(1, 1), Direction.N);

		Plateau savedPlateau = repository.save(plateau);
		savedPlateau.insertProbe(probe);
		Plateau updatedPlateau = repository.save(savedPlateau);

		Long idProbe = updatedPlateau
				.getProbes()
				.stream()
				.findFirst()
				.map(Probe::getId)
				.stream()
				.findFirst()
				.orElseThrow();


		mockMvc.perform(get("/api/plateaus/" + savedPlateau.getId() + "/probes/" + idProbe)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.direction").value(probe.getDirection().name()))
				.andExpect(jsonPath("$.position.y").value(probe.getPosition().getY()))
				.andExpect(jsonPath("$.position.x").value(probe.getPosition().getX()));
	}


	@Test
	void shouldMoveProbeInPlateau() throws Exception {
		File file = moveProbe.getFile();
		CommandDTO commandDTO = jackson.readValue(file, CommandDTO.class);

		Plateau plateau = new Plateau(new Position(5, 5));
		Probe probe = new Probe(new Position(3, 3), Direction.E);

		Plateau savedPlateau = repository.save(plateau);
		savedPlateau.insertProbe(probe);
		Plateau updatedPlateau = repository.save(savedPlateau);

		Long idProbe = updatedPlateau
				.getProbes()
				.stream()
				.findFirst()
				.map(Probe::getId)
				.stream()
				.findFirst()
				.orElseThrow();

		mockMvc.perform(post("/api/plateaus/" + savedPlateau.getId() + "/probes/" + idProbe + "/move")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jackson.writeValueAsString(commandDTO)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(idProbe))
				.andExpect(jsonPath("$.position.x").value("5"))
				.andExpect(jsonPath("$.position.y").value("1"))
				.andExpect(jsonPath("$.direction").value(Direction.E.name()));

	}


	@Test
	void shouldReturnNotFoundWhenSondaNotExists() throws Exception {
		Plateau savedPlateau = repository.save(new Plateau(new Position(5, 5)));

		mockMvc.perform(get("/api/plateaus/" + savedPlateau.getId() + "/probes/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound());
	}


}
