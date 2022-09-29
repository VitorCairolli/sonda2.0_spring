package com.elo7.probe_spring.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    private Direction direction;

    private int initialPositionX = 5;
    private int initialPositionY = 5;

    @BeforeEach
    private void instantiate() {
        direction = Direction.N;
    }

    @Test
    void move() {
        Position position = new Position(initialPositionX,initialPositionY);
        direction.move(position);
        assertEquals(position, new Position(initialPositionX, initialPositionY + 1));

        direction = Direction.S;
        direction.move(position);
        assertEquals(position, new Position(initialPositionX, initialPositionY));

        direction = Direction.E;
        direction.move(position);
        assertEquals(position, new Position(initialPositionX + 1, initialPositionY));

        direction = Direction.W;
        direction.move(position);
        assertEquals(position, new Position(initialPositionX, initialPositionY));
    }

    @Test
    void turn() {
        direction = direction.turn('R');
        assertEquals(direction, Direction.E);
        direction = direction.turn('R');
        assertEquals(direction, Direction.S);
        direction = direction.turn('R');
        assertEquals(direction, Direction.W);
        direction = direction.turn('R');
        assertEquals(direction, Direction.N);
        direction = direction.turn('L');
        assertEquals(direction, Direction.W);
        direction = direction.turn('L');
        assertEquals(direction, Direction.S);
        direction = direction.turn('L');
        assertEquals(direction, Direction.E);
        direction = direction.turn('L');
        assertEquals(direction, Direction.N);
    }
}