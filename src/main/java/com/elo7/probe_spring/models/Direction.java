package com.elo7.probe_spring.models;

public enum Direction {
	N {
		@Override
		public Position move(Position position) {
			return new Position(position.getX(), position.getY() + 1);
		}

		@Override
		public Direction turn(char side) {
			if (side == 'R')
				return Direction.E;

			else
				return Direction.W;
		}
	},
	E {
		@Override
		public Position move(Position position) {
            return new Position(position.getX() + 1, position.getY());
		}

		@Override
		public Direction turn(char side) {
			if (side == 'R')
				return Direction.S;

			else
				return Direction.N;
		}
	},
	S {
		@Override
		public Position move(Position position) {
            return new Position(position.getX(), position.getY() - 1);
		}

		@Override
		public Direction turn(char side) {
			if (side == 'R')
				return Direction.W;

			else
				return Direction.E;
		}
	},
	W {
		@Override
		public Position move(Position position) {
			return new Position(position.getX() - 1, position.getY());
		}

		@Override
		public Direction turn(char side) {
			if (side == 'R')
				return Direction.N;

			else
				return Direction.S;
		}
	};

	public abstract Position move(Position position);

	public abstract Direction turn(char side);
}
