package com.elo7.probe_spring.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "probe")
public class Probe {

    @Id
    @SequenceGenerator(name = "PROBE_SEQ")
    @GeneratedValue(generator = "PROBE_SEQ")
    private Long id;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "position_id")
    private Position position;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="direction")
    private Direction direction;


    @ManyToOne
    @JoinColumn(name = "plateau_id")
    private Plateau plateau;

    Probe(){}

    public Probe(Position position, Direction direction){
        this.position = position;
        this.direction = direction;
    }

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {this.plateau = plateau;}

    public Direction getDirection() {return direction;}

    public Position getPosition() {
        return position;
    }

    public void setDirection(Direction direction) {this.direction = direction;}

    public void move() {
        direction.move(position);
    }

    public void turn(char side) {
        direction = direction.turn(side);
    }
}
