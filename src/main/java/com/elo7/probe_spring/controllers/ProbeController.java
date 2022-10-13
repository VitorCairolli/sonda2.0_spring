package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.models.Probe;
import com.elo7.probe_spring.services.PlateauService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/plateaus/{plateauId}/probes")
public class ProbeController {

    private final PlateauService plateauService;

    public ProbeController(PlateauService plateauService) {
        this.plateauService = plateauService;
    }

    @GetMapping("{probeId}")
    public ResponseEntity<ProbeDTO> getProbe(@PathVariable Long plateauId,
                                             @PathVariable Long probeId) {
        var probe = plateauService.
                findProbeInPlateauById(plateauId, probeId);

        if (probe.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(ProbeDTO.from(probe.get()));
    }

    @PostMapping
    public ResponseEntity<ProbeDTO> createProbe(@RequestBody @Valid ProbeDTO inputProbeDTO,
                                                @PathVariable Long plateauId) throws URISyntaxException {
        Optional<Probe> probe = plateauService.insertProbeAtPlateau(plateauId, inputProbeDTO.toEntity());

        if (probe.isEmpty())
            return ResponseEntity.notFound().build();

        Probe createdProbe = probe.get();
        return ResponseEntity.created(new URI("/api/plateaus/" + plateauId + "/probes/" + createdProbe.getId()))
                .body(ProbeDTO.from(createdProbe));
    }

    @PostMapping("{probeId}/move")
    public ResponseEntity<ProbeDTO> moveProbe(@RequestBody @Valid CommandDTO commandDTO,
                                              @PathVariable Long probeId,
                                              @PathVariable Long plateauId) {
        Optional<Probe> probe = plateauService.moveProbeInPlateau(plateauId, probeId, commandDTO.command());

        if (probe.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(ProbeDTO.from(probe.get()));
    }
}
