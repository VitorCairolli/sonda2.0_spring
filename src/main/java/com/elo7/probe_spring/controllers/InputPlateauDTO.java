package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.models.Plateau;
import com.elo7.probe_spring.models.Position;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public record InputPlateauDTO(Position position1, Position position2) {

    public Plateau toEntity(){
        return new Plateau(position1, position2);
    }
}
