package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.models.Plateau;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlateauDTO(@JsonProperty("id")Long id,
                         @JsonProperty("position") @Valid PositionDTO position,
                         @JsonProperty("probes") @Valid List<ProbeDTO> probes) {

    public Plateau toEntity(){
        return new Plateau(position.toEntity());
    }

    public static PlateauDTO from(Plateau plateau){
        return new PlateauDTO(plateau.getId(),
                PositionDTO.from(plateau.getMaxPosition()),
                plateau.getProbes().stream().map(probe -> ProbeDTO.from(probe)).toList());
    }
}
