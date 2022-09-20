package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.services.ProbeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/probes")
public class ProbeController {

    private ProbeService service;

    public ProbeController(ProbeService service){this.service = service;}

    @GetMapping("/{probeId}")
    public ResponseEntity<OutputProbeDTO> getProbe(@PathVariable Long probeId) {
        var probe = service.findById(probeId);

        return ResponseEntity.ok(OutputProbeDTO.from(probe.get()));
    }

    @PostMapping
    public ResponseEntity<OutputProbeDTO> createProbe(@RequestBody InputProbeDTO inputProbeDTO) {
        var probe = service.create(inputProbeDTO.toEntity(), inputProbeDTO.getPlateauId());

        return ResponseEntity.ok(OutputProbeDTO.from(probe));
    }

    @PostMapping("/{probeId}/move")
    public ResponseEntity<OutputProbeDTO> moveProbe(@RequestBody InputCommandDTO commandDTO, @PathVariable Long probeId) {
        var probe = service.move(commandDTO.command().toUpperCase(), probeId);

        return ResponseEntity.ok(OutputProbeDTO.from(probe));
    }
}
