package com.elo7.probe_spring.services;

import com.elo7.probe_spring.exceptions.InvalidCommandException;
import com.elo7.probe_spring.exceptions.InvalidProbeException;
import com.elo7.probe_spring.models.Plateau;
import com.elo7.probe_spring.models.Position;
import com.elo7.probe_spring.models.Probe;
import com.elo7.probe_spring.repositories.ProbeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProbeService {

    @Autowired
    private ProbeRepository repository;
    @Autowired
    PlateauService plateauService;
    @Autowired
    InvalidProbeException probeException;
    @Autowired
    InvalidCommandException commandException;

    public Optional<Probe> findById(Long probeId) {
        Optional<Probe> probe = repository.findById(probeId);

        if(probe.isEmpty()) {
            probeException.setBody("Probe id invalid");
            throw probeException;
        }

        return probe;
    }

    public Probe create(Probe newProbe, Long plateauId) {
        Optional<Plateau> plateau = plateauService.findById(plateauId);

        if(!isInside(newProbe.getPosition(), plateau.get())) {
            probeException.setBody("Probe position is outside chosen plateau");
            throw probeException;
        }

        for (Probe probe:plateau.get().getProbeList()) {
            if(newProbe.getPosition().equals(probe.getPosition())) {
                probeException.setBody("There's already a probe in this position");
                throw probeException;
            }
        }

        newProbe.setPlateau(plateau.get());
        return repository.save(newProbe);
    }

    public Probe move(String command, Long probeId) {
        Optional<Probe> probe = repository.findById(probeId);

        if(!commandIsValid(command)) {
            commandException.setBody("Invalid command. Must contain only 'L'(left), 'R'(right) or 'M'(move)");
            throw commandException;
        }

        Optional<Plateau> plateau = plateauService.findById(probe.get().getPlateau().getId());

        HashMap<Probe, Position> hashMap = probeListToHashmap(plateau.get().getProbeList());

        Probe dummy = new Probe(new Position(probe.get().getPosition().getX(), probe.get().getPosition().getY()), probe.get().getDirection());

        for(int i = 0; i < command.length(); i++) {
            if(command.charAt(i) == 'M') {
                dummy.move();
                if (hashMap.containsValue(dummy.getPosition())) {
                    commandException.setBody("Invalid command. This command will cause probe collision");
                    throw commandException;
                }
                if (!isInside(dummy.getPosition(), plateau.get())) {
                    commandException.setBody("Invalid command. This command will cause the probe to leave the plateau");
                    throw commandException;
                }
            }
            else dummy.turn(command.charAt(i));
        }

        probe.get().getPosition().setX(dummy.getPosition().getX());
        probe.get().getPosition().setY(dummy.getPosition().getY());
        probe.get().setDirection(dummy.getDirection());
        return repository.save(probe.get());
    }

    private boolean isInside(Position position, Plateau plateau) {
        return (position.getX() >= plateau.getPosition1().getX() &&
                position.getY() >= plateau.getPosition1().getY() &&
                position.getX() <= plateau.getPosition2().getX() &&
                position.getY() <= plateau.getPosition2().getY());
    }

    private boolean commandIsValid(String command) {
        for(int i = 0; i < command.length(); i++)
            if (command.charAt(i) != 'L' &&
            command.charAt(i) != 'R' &&
            command.charAt(i) != 'M')
                return false;

        return true;
    }

    private HashMap probeListToHashmap(List<Probe> probeList) {
        HashMap<Probe, Position> hashMap = new HashMap<>();
        for (Probe probe:probeList) {
            hashMap.put(probe, probe.getPosition());
        }

        return hashMap;
    }
}
