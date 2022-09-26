package com.elo7.probe_spring.models;

import javax.persistence.*;

@Embeddable
public class Position {

    private Integer x;

    private Integer y;

    Position() {
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {

        return this.x;
    }
    public int getY() {

        return this.y;
    }
    public void setX(int x) {

        this.x = x;
    }
    public void setY(int y) {

        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Position key = (Position) o;
        return x == key.x && y == key.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 42 * result + y;
        return result;
    }
}