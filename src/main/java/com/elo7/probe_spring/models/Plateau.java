package com.elo7.probe_spring.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "plateau")
public class Plateau {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "minPosition_id")
    private Position minPosition;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "maxPosition_id")
    private Position maxPosition;

    @OneToMany(mappedBy = "plateau")
    private List<Probe> probes;

    Plateau(){}

    public Plateau(Position position){
        this.minPosition = new Position(0,0);
        this.maxPosition = position;
        probes = new ArrayList<>() {
        };
    }

    public Long getId(){

        return id;
    }

    public Position getMinPosition() {

        return minPosition;
    }

    public Position getMaxPosition() {

        return maxPosition;
    }

    public List<Probe> getProbes(){

        return probes;
    }

    public boolean isInside(Probe probe) {

        return (probe.getPosition().getX() >= getMinPosition().getX() &&
                probe.getPosition().getY() >= getMinPosition().getY() &&
                probe.getPosition().getX() <= getMaxPosition().getX() &&
                probe.getPosition().getY() <= getMaxPosition().getY());
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Plateau plateau))
            return false;

        return (this.id.equals(plateau.id) &&
                this.minPosition.equals(plateau.minPosition) &&
                this.maxPosition.equals(plateau.maxPosition));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.minPosition, this.maxPosition);
    }
}
