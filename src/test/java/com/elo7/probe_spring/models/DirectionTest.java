package com.elo7.probe_spring.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    private int initialPositionX = 5;
    private int initialPositionY = 5;
    private Position position;
    private Direction subject;

    @BeforeEach
    private void instantiate() {
        position = new Position(initialPositionX, initialPositionY);
    }

    @Test
    void moveOneUnitOfMovementUp() {
        subject = Direction.N;
        subject.move(position);
        assertEquals(position, new Position(initialPositionX, initialPositionY + 1));
    }

    @Test
    void moveOneUnitOfMovementDown() {
        subject = Direction.S;
        subject.move(position);
        assertEquals(position, new Position(initialPositionX, initialPositionY -1));
    }

    @Test
    void moveOneUnitOfMovementEast() {
        subject = Direction.E;
        subject.move(position);
        assertEquals(position, new Position(initialPositionX + 1, initialPositionY));
    }

    @Test
    void moveOneUnitOfMovementWest() {
        subject = Direction.W;
        subject.move(position);
        assertEquals(position, new Position(initialPositionX - 1, initialPositionY));
    }

    @Test
    void turn() {
        subject = Direction.N;
        subject = subject.turn('R');
        assertEquals(subject, Direction.E);
        subject = subject.turn('R');
        assertEquals(subject, Direction.S);
        subject = subject.turn('R');
        assertEquals(subject, Direction.W);
        subject = subject.turn('R');
        assertEquals(subject, Direction.N);
        subject = subject.turn('L');
        assertEquals(subject, Direction.W);
        subject = subject.turn('L');
        assertEquals(subject, Direction.S);
        subject = subject.turn('L');
        assertEquals(subject, Direction.E);
        subject = subject.turn('L');
        assertEquals(subject, Direction.N);
    }
}