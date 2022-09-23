package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.models.Plateau;
import com.elo7.probe_spring.models.Position;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlateauDTO(Long id, PositionDTO position, List<ProbeDTO> probes) {

    public Plateau toEntity(){

        return new Plateau(position.toEntity());
    }

    public static PlateauDTO from(Plateau plateau){
        return new PlateauDTO(plateau.getId(),
                PositionDTO.from(plateau.getMaxPosition()),
                plateau.getProbes().stream().map(probe -> ProbeDTO.from(probe)).toList());
    }
}
