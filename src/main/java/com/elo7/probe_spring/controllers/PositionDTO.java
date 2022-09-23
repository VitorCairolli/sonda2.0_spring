package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.models.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PositionDTO(Long id , Integer x, Integer y) {

    public Position toEntity() {

        return new Position(x.intValue(), y.intValue());
    }

    public static PositionDTO from(Position position){

        return new PositionDTO(position.getId() , position.getX(), position.getY());
    }
}
