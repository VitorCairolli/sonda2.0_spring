package com.elo7.probe_spring.controllers;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Pattern(regexp = "\"(^[LMR]+)$\"gm\"", message = "Invalid command")
public record CommandDTO(@Valid String command) {

    public String command() {

        return command;
    }
}
