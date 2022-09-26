package com.elo7.probe_spring.services;

import com.elo7.probe_spring.exceptions.InvalidPlateauException;
import com.elo7.probe_spring.exceptions.InvalidProbeException;
import com.elo7.probe_spring.models.Plateau;
import com.elo7.probe_spring.models.Position;
import com.elo7.probe_spring.models.Probe;
import com.elo7.probe_spring.repositories.ProbeRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProbeService {

    private final ProbeRepository repository;

    private final PlateauService plateauService;

    public ProbeService(ProbeRepository repository, PlateauService plateauService) {

        this.repository = repository;
        this.plateauService = plateauService;
    }

    public Optional<Probe> findById(Long probeId) {
        Optional<Probe> probe = repository.findById(probeId);

        return probe;
    }

    public Probe create(Probe newProbe, Long plateauId) {
        Plateau plateau = plateauService.findById(plateauId)
                .orElseThrow(() -> new InvalidPlateauException("Plateau not found"));

        plateau.insertProbe(newProbe);
        return repository.save(newProbe);
    }

    public Probe move(String command, Long probeId) {
        Probe probe = repository.findById(probeId)
                .orElseThrow(() -> new InvalidProbeException("Probe not found"));

        Probe dummy = new Probe(new Position(probe.getPosition().getX(), probe.getPosition().getY()), probe.getDirection());

        for(int i = 0; i < command.length(); i++) {
            if(command.charAt(i) == 'M')
                dummy.move();

            else
                dummy.turn(command.charAt(i));
        }

        probe.getPosition().setX(dummy.getPosition().getX());
        probe.getPosition().setY(dummy.getPosition().getY());
        probe.setDirection(dummy.getDirection());
        return repository.save(probe);
    }

    private HashMap<Position, Probe> probeListToHashmap(List<Probe> probeList) {
        HashMap<Position, Probe> hashMap = new HashMap<>();
        for (Probe probe:probeList) {
            hashMap.put(probe.getPosition(), probe);
        }

        return hashMap;
    }
}
