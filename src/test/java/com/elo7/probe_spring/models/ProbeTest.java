package com.elo7.probe_spring.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProbeTest {

    private int initialProbeX = 3;

    private int initialProbeY = 3;

    private int initialPlateauX = 10;

    private int initialPlateauY = 10;

    private Direction initialProbeDirection = Direction.N;

    @Mock
    private Plateau mockPlateau;

    private Plateau plateau;

    private Probe subject;

    @BeforeEach
    private void instantiate() {
        plateau = new Plateau(new Position(initialPlateauX,initialPlateauY));
        subject = new Probe(new Position(initialProbeX,initialProbeY), initialProbeDirection);
    }

    @Test
    void goodCaseMove() {
        subject.move();
        assertEquals(subject.getPosition(), new Position(initialProbeX, initialProbeY + 1));

        subject.setDirection(Direction.S);
        subject.move();
        assertEquals(subject.getPosition(), new Position(initialProbeX, initialProbeY));

        subject.setDirection(Direction.E);
        subject.move();
        assertEquals(subject.getPosition(), new Position(initialProbeX + 1, initialProbeY));

        subject.setDirection(Direction.W);
        subject.move();
        assertEquals(subject.getPosition(), new Position(initialProbeX, initialProbeY));
    }

    @Test
    void turn() {
        subject.turn('R');
        assertEquals(subject.getDirection(), Direction.E);
        subject.turn('R');
        assertEquals(subject.getDirection(), Direction.S);
        subject.turn('R');
        assertEquals(subject.getDirection(), Direction.W);
        subject.turn('R');
        assertEquals(subject.getDirection(), Direction.N);
        subject.turn('L');
        assertEquals(subject.getDirection(), Direction.W);
        subject.turn('L');
        assertEquals(subject.getDirection(), Direction.S);
        subject.turn('L');
        assertEquals(subject.getDirection(), Direction.E);
        subject.turn('L');
        assertEquals(subject.getDirection(), Direction.N);
    }
}