package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.models.Direction;
import com.elo7.probe_spring.models.Position;
import com.elo7.probe_spring.models.Probe;

public record InputProbeDTO(Position position, Direction direction) {

    public Probe toEntity() {
        return new Probe(position, direction);
    }
}
