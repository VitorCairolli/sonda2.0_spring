package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.models.Plateau;
import com.elo7.probe_spring.models.Position;
import com.elo7.probe_spring.models.Probe;

import java.util.List;

public record OutputPlateauDTO(Long id, Position position1, Position position2, List<Probe> probeList) {

    public static OutputPlateauDTO from(Plateau plateau){
        return new OutputPlateauDTO(plateau.getId(),
                plateau.getPosition1(),
                plateau.getPosition2(),
                plateau.getProbeList());
    }
}
