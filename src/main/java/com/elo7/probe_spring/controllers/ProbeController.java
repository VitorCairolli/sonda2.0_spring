package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.services.ProbeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/probe")
public class ProbeController {

    @Autowired
    private ProbeService service;

    @GetMapping("/{probeId}")
    public ResponseEntity<OutputProbeDTO> getProbe(@PathVariable Long probeId) {
        var probe = service.findById(probeId);

        return ResponseEntity.ok(OutputProbeDTO.from(probe.get()));
    }

    @PostMapping("/create/{plateauId}")
    public ResponseEntity<OutputProbeDTO> createProbe(@RequestBody InputProbeDTO inputProbeDTO, @PathVariable Long plateauId) {
        var probe = service.create(inputProbeDTO.toEntity(), plateauId);

        return ResponseEntity.ok(OutputProbeDTO.from(probe));
    }

    @PostMapping("/move/{probeId}")
    public ResponseEntity<OutputProbeDTO> moveProbe(@RequestBody InputCommandDTO commandDTO, @PathVariable Long probeId) {
        var probe = service.move(commandDTO.command().toUpperCase(), probeId);

        return ResponseEntity.ok(OutputProbeDTO.from(probe));
    }
}
