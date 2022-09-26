package com.elo7.probe_spring.models;

import com.elo7.probe_spring.exceptions.ProbeCollisionException;
import com.elo7.probe_spring.exceptions.ProbeOutOfPlateauException;

import javax.persistence.*;

@Entity
@Table(name = "probe")
public class Probe {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "x_coordinates")),
            @AttributeOverride(name = "y", column = @Column(name = "y_coordinates")),
    })
    private Position position;

    @Enumerated(EnumType.STRING)
    @Column(name="direction")
    private Direction direction;


    @ManyToOne
    @JoinColumn(name = "plateau_id")
    private Plateau plateau;

    Probe(){}

    public Probe(Position position, Direction direction) {

        this.position = position;
        this.direction = direction;
    }

    public Long getId() {

        return id;
    }

    public Plateau getPlateau() {

        return plateau;
    }

    public void setPlateau(Plateau plateau) {

        this.plateau = plateau;
    }

    public Direction getDirection() {
        return direction;
    }

    public Position getPosition() {

        return position;
    }

    public void setDirection(Direction direction) {

        this.direction = direction;
    }

    public void move() {

        plateau.isPositionValid(this);
        direction.move(position);
    }

    public void turn(char side) {

        direction = direction.turn(side);
    }
}
