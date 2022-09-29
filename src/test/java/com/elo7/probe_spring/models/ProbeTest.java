package com.elo7.probe_spring.models;

import com.elo7.probe_spring.exceptions.ProbeCollisionException;
import com.elo7.probe_spring.exceptions.ProbeOutOfPlateauException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProbeTest {

    private Probe probe;
    private Plateau plateau;
    private int initialProbeX = 3;
    private int initialProbeY = 3;
    private int initialPlateauX = 10;
    private int initialPlateauY = 10;
    private Direction initialProbeDirection = Direction.N;

    @BeforeEach
    private void instantiate() {
        plateau = new Plateau(new Position(initialPlateauX,initialPlateauY));
        probe = new Probe(new Position(initialProbeX,initialProbeY), initialProbeDirection);
        probe.setPlateau(plateau);
    }

    @Test
    void goodCaseMove() {
        probe.move();
        assertEquals(probe.getPosition(), new Position(initialProbeX, initialProbeY + 1));

        probe.setDirection(Direction.S);
        probe.move();
        assertEquals(probe.getPosition(), new Position(initialProbeX, initialProbeY));

        probe.setDirection(Direction.E);
        probe.move();
        assertEquals(probe.getPosition(), new Position(initialProbeX + 1, initialProbeY));

        probe.setDirection(Direction.W);
        probe.move();
        assertEquals(probe.getPosition(), new Position(initialProbeX, initialProbeY));
    }

    @Test
    void turn() {
        probe.turn('R');
        assertEquals(probe.getDirection(), Direction.E);
        probe.turn('R');
        assertEquals(probe.getDirection(), Direction.S);
        probe.turn('R');
        assertEquals(probe.getDirection(), Direction.W);
        probe.turn('R');
        assertEquals(probe.getDirection(), Direction.N);
        probe.turn('L');
        assertEquals(probe.getDirection(), Direction.W);
        probe.turn('L');
        assertEquals(probe.getDirection(), Direction.S);
        probe.turn('L');
        assertEquals(probe.getDirection(), Direction.E);
        probe.turn('L');
        assertEquals(probe.getDirection(), Direction.N);
    }
}