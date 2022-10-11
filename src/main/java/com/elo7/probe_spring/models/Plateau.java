package com.elo7.probe_spring.models;

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

    @Transient
    private Position minPosition;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "max_x_coordinates")),
            @AttributeOverride(name = "y", column = @Column(name = "max_y_coordinates")),
    })
    private Position maxPosition;

    @OneToMany(mappedBy = "plateau")
    private List<Probe> probes;

    Plateau() {
    }

    public Plateau(Position position) {
        this.minPosition = new Position(0, 0);
        this.maxPosition = position;
        probes = new ArrayList<>();
    }

    public Long getId() {

        return id;
    }

    public Position getMinPosition() {

        return Optional.ofNullable(minPosition).orElse(new Position(0, 0));
    }

    public Position getMaxPosition() {

        return maxPosition;
    }

    public List<Probe> getProbes() {

        return probes;
    }

    private boolean isInsidePlateau(Probe probe) {

        return (probe.getPosition().getX() >= getMinPosition().getX() &&
                probe.getPosition().getY() >= getMinPosition().getY() &&
                probe.getPosition().getX() <= getMaxPosition().getX() &&
                probe.getPosition().getY() <= getMaxPosition().getY());
    }

    public void checkPositionValid(Probe probe, ProbeCollisionException collisionException, ProbeOutOfPlateauException outOfPlateauException) {

        if (thereIsProbeWithPosition(probe))
            throw collisionException;

        if (!isInsidePlateau(probe))
            throw outOfPlateauException;
    }

    public void insertProbe(Probe probe) {

        checkPositionValid(probe,
                new ProbeCollisionException("Probe creation error: there already is a probe in this position"),
                new ProbeOutOfPlateauException("Probe creation error: this position is outside the chosen plateau"));

        probe.setPlateau(this);
        probes.add(probe);
    }

    private boolean thereIsProbeWithPosition(Probe inputProbe) {
        return probes
                .stream()
                .anyMatch((probe) -> probe.getPosition().equals(inputProbe.getPosition()));
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
