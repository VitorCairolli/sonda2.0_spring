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
    @JoinColumn(name = "position_1_id")
    private Position position1;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "position_2_id")
    private Position position2;

    @OneToMany(mappedBy = "plateau")
    private List<Probe> probeList;

    Plateau(){}

    public Plateau(Position position1, Position position2){
        this.position1 = position1;
        this.position2 = position2;
        probeList = new ArrayList<>() {
        };
    }

    public Long getId(){return id;}

    public Position getPosition1() {
        return position1;
    }

    public Position getPosition2() {
        return position2;
    }

    public List<Probe> getProbeList(){return probeList;}

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Plateau plateau))
            return false;

        return (this.id.equals(plateau.id) &&
                this.position1.equals(plateau.position1) &&
                this.position2.equals(plateau.position2));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.position1, this.position2);
    }
}
