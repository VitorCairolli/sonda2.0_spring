package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.exceptions.InvalidPlateauException;
import com.elo7.probe_spring.services.PlateauService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plateaus")
public class PlateauController {

    private final PlateauService service;

    public PlateauController(PlateauService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<OutputAllPlateauDTO> getAllPlateaus(){
        var allPlateaus = service.findAll();

        return ResponseEntity.ok(OutputAllPlateauDTO.from(allPlateaus));
    }

    @GetMapping("/{plateauId}")
    public ResponseEntity<PlateauDTO> getPlateau(@PathVariable Long plateauId){
        var plateau = service.findById(plateauId);

        if (plateau.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(PlateauDTO.from(plateau.get()));
    }

    @PostMapping
    public ResponseEntity<PlateauDTO> createPlateau(@RequestBody PlateauDTO inputPlateauDTO) {

        var plateau = service.create(inputPlateauDTO.toEntity());

        return ResponseEntity.ok(PlateauDTO.from(plateau));
    }
}
