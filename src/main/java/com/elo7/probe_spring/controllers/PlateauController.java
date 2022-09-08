package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.services.PlateauService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plateau")
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
    public ResponseEntity<OutputPlateauDTO> getPlateau(@PathVariable Long plateauId){
        var plateau = service.findById(plateauId);

        return ResponseEntity.ok(OutputPlateauDTO.from(plateau.get()));
    }

    @PostMapping
    public ResponseEntity<OutputPlateauDTO> createPlateau(@RequestBody InputPlateauDTO inputPlateauDTO){
        var plateau = service.create(inputPlateauDTO.toEntity());

        return ResponseEntity.ok(OutputPlateauDTO.from(plateau));
    }
}
