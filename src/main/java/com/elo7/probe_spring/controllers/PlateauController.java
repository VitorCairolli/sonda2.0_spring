package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.services.PlateauService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/plateaus")
public class PlateauController {

	private final PlateauService service;

	public PlateauController(PlateauService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<OutputAllPlateauDTO> getAllPlateaus() {
		var allPlateaus = service.findAll();

		return ResponseEntity.ok(OutputAllPlateauDTO.from(allPlateaus));
	}

	@GetMapping("/{plateauId}")
	public ResponseEntity<PlateauDTO> getPlateau(@PathVariable Long plateauId) {
		var plateau = service.findById(plateauId);

		if (plateau.isEmpty())
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(PlateauDTO.from(plateau.get()));
	}

	@PostMapping
	public ResponseEntity<PlateauDTO> createPlateau(@RequestBody @Valid PlateauDTO inputPlateauDTO) throws URISyntaxException {

		var plateau = service.create(inputPlateauDTO.toEntity());

		return ResponseEntity.created(new URI("/api/plateaus/" + plateau.getId()))
				.body(PlateauDTO.from(plateau));

	}
}
