package com.elo7.probe_spring.models;

import javax.persistence.*;

@Entity
@Table(name = "position")
public class Position {

    @Id
    @SequenceGenerator(name = "POSITION_SEQ")
    @GeneratedValue(generator = "POSITION_SEQ")
    private Long id;

    @Column(name = "x_coordinate")
    private int x;

    @Column(name = "y_coordinate")
    private int y;

    Position() {
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Long getId() {
        return id;
    }

    public int getX() {return this.x;}
    public int getY() {return this.y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}

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