package com.elo7.probe_spring.controllers;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.Pattern;

public record CommandDTO(@Pattern(regexp = "[RML]+", message = "Invalid command") String command) {

    @JsonCreator
    public CommandDTO{}
}
