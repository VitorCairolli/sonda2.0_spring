package com.elo7.probe_spring.models;

import com.elo7.probe_spring.exceptions.InvalidProbeException;
import com.elo7.probe_spring.exceptions.ProbeCollisionException;
import com.elo7.probe_spring.exceptions.ProbeOutOfPlateauException;

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

    private boolean isInsidePlateau(Probe probe) {

        return (probe.getPosition().getX() >= getMinPosition().getX() &&
                probe.getPosition().getY() >= getMinPosition().getY() &&
                probe.getPosition().getX() <= getMaxPosition().getX() &&
                probe.getPosition().getY() <= getMaxPosition().getY());
    }

    public void isPositionValid(Probe probe) {

        if (thereIsProbeWithPosition(probe))
            throw new ProbeCollisionException("Command causes probe to collide");

        if (isInsidePlateau(probe))
            throw new ProbeOutOfPlateauException("Command causes probe to leave plateau");
    }

    public void insertProbe(Probe probe) {

        if (isInsidePlateau(probe))
            throw new ProbeOutOfPlateauException("Could not create probe: there is a probe in this position.");

        if (thereIsProbeWithPosition(probe))
            throw new ProbeCollisionException("Could not create probe: position outside plateau.");

        probe.setPlateau(this);
        probes.add(probe);
    }

    private boolean thereIsProbeWithPosition(Probe inputProbe) {
        for (Probe probe:this.probes) {
            if(probe.getPosition().equals(inputProbe.getPosition())) {
                return false;
            }
        }

        return true;
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
