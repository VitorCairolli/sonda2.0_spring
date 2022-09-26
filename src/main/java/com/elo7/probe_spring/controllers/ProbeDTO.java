package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.models.Direction;
import com.elo7.probe_spring.models.Position;
import com.elo7.probe_spring.models.Probe;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.Valid;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProbeDTO(Long id, @Valid PositionDTO position, Direction direction, Long plateauId) {

    public Probe toEntity() {

        return new Probe(position.toEntity(), direction);
    }

    public static ProbeDTO from(Probe probe){
        return new ProbeDTO(probe.getId(),
                PositionDTO.from(probe.getPosition()),
                probe.getDirection(),
                probe.getPlateau().getId());
    }

    public Long getPlateauId() {

        return plateauId;
    }
}
