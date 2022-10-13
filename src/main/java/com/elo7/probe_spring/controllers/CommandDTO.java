package com.elo7.probe_spring.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Pattern;

public record CommandDTO(@Pattern(regexp = "[RML]+", message = "Invalid command") @JsonProperty("command") String command) {

	@Override
	public String command() {
		return command.toUpperCase();
	}
}
