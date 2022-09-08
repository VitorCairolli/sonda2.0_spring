package com.elo7.probe_spring.controllers;

public record InputCommandDTO(String command) {

    public String command() {return command;}
}
