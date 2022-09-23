package com.elo7.probe_spring.controllers;

import com.elo7.probe_spring.models.Plateau;

import java.util.List;

public record OutputAllPlateauDTO (List<PlateauDTO> plateaus){

    public static OutputAllPlateauDTO from(List<Plateau> plateaus){

        return new OutputAllPlateauDTO(plateaus.stream().map(plateau -> PlateauDTO.from(plateau)).toList());
    }
}
