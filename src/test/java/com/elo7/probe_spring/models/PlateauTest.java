package com.elo7.probe_spring.models;

import com.elo7.probe_spring.exceptions.ProbeCollisionException;
import com.elo7.probe_spring.exceptions.ProbeOutOfPlateauException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlateauTest {

    private Probe probe;
    private Plateau plateau;
    private int initialProbeX = 3;
    private int initialProbeY = 3;
    private int initialPlateauX = 10;
    private int initialPlateauY = 10;

    @BeforeEach
    private void instantiate() {
        probe = new Probe(new Position(initialProbeX, initialProbeY), Direction.N);
        plateau = new Plateau(new Position(initialPlateauX, initialPlateauY));
        probe.setPlateau(plateau);
    }

    @Test
    void goodCaseIsPositionValid() {
        plateau.checkPositionValid(probe);
    }

    @Test
    void badCaseIsPositionValidOutOfPlateau() {
        probe.getPosition().setX(initialProbeX);
        probe.getPosition().setY(initialPlateauY + 1);
        Throwable exception = assertThrows(ProbeOutOfPlateauException.class, () -> plateau.checkPositionValid(probe));

        probe.getPosition().setX(initialProbeX);
        probe.getPosition().setY(-1);
        probe.setDirection(Direction.S);
        exception = assertThrows(ProbeOutOfPlateauException.class, () -> plateau.checkPositionValid(probe));

        probe.getPosition().setX(initialPlateauY + 1);
        probe.getPosition().setY(initialProbeY);
        probe.setDirection(Direction.E);
        exception = assertThrows(ProbeOutOfPlateauException.class, () -> plateau.checkPositionValid(probe));

        probe.getPosition().setX(-1);
        probe.getPosition().setY(initialProbeY);
        probe.setDirection(Direction.W);
        exception = assertThrows(ProbeOutOfPlateauException.class, () -> plateau.checkPositionValid(probe));
    }

    void badCaseIsPositionValidSamePositionAsAnotherProbe() {
        Probe dummy = new Probe( new Position(initialProbeX,initialProbeY), Direction.N);
        dummy.setPlateau(plateau);
        plateau.getProbes().add(dummy);
        Throwable exception = assertThrows(ProbeCollisionException.class, () -> probe.move());
    }

    @Test
    void insertProbe() {
        assertEquals(plateau.getProbes().size(), 0);
        plateau.insertProbe(probe);
        assertEquals(plateau.getProbes().size(), 1);
        assertEquals(plateau.getProbes().get(0).getPosition(), probe.getPosition());
    }
}