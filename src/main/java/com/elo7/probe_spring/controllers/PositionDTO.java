package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.models.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Min;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PositionDTO(@Min(value = 0, message = "'X' and 'Y' value can not be less than 0") Integer x,
                          @Min(value = 0, message = "'X' and 'Y' value can not be less than 0") Integer y) {

    public Position toEntity() {

        return new Position(x.intValue(), y.intValue());
    }

    public static PositionDTO from(Position position){

        return new PositionDTO(position.getX(), position.getY());
    }
}
