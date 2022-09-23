package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.services.ProbeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/probes")
public class ProbeController {

    private final ProbeService service;

    public ProbeController(ProbeService service) {

        this.service = service;
    }

    @GetMapping("/{probeId}")
    public ResponseEntity<ProbeDTO> getProbe(@PathVariable Long probeId) {
        var probe = service.findById(probeId);

        if (probe.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(ProbeDTO.from(probe.get()));
    }

    @PostMapping
    public ResponseEntity<ProbeDTO> createProbe(@RequestBody ProbeDTO inputProbeDTO) {
        var probe = service.create(inputProbeDTO.toEntity(), inputProbeDTO.getPlateauId());

        return ResponseEntity.ok(ProbeDTO.from(probe));
    }

    @PostMapping("/{probeId}/move")
    public ResponseEntity<ProbeDTO> moveProbe(@RequestBody @Valid CommandDTO commandDTO, @PathVariable Long probeId) {
        var probe = service.move(commandDTO.command().toUpperCase(), probeId);

        return ResponseEntity.ok(ProbeDTO.from(probe));
    }
}
