package com.elo7.probe_spring.models;

public enum Direction {
    N{
        @Override
        public void move(Position position) {
            position.setY(position.getY() + 1);
        }
    },
    E{
        @Override
        public void move(Position position) {
            position.setX(position.getX() + 1);
        }
    },
    S{
        @Override
        public void move(Position position) {
            position.setY(position.getY() - 1);
        }
    },
    W{
        @Override
        public void move(Position position) {
            position.setX(position.getX() - 1);
        }
    };

    public abstract void move(Position position);
    public Direction turn(char side) {
        Direction[] values = Direction.values();
        if(side == 'L')
            return values[(this.ordinal() + 3) % 4];

        else
            return values[(this.ordinal() + 1) % 4];
    }
}
