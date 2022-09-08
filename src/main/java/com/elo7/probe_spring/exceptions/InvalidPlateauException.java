package com.elo7.probe_spring.exceptions;

import org.springframework.stereotype.Component;

@Component
public class InvalidPlateauException extends RuntimeException{
    private String body = "";

    public void setBody(String body) {this.body = body;}

    public String getBody() {return body;}
}
