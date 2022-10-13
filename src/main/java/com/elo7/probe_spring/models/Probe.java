package com.elo7.probe_spring.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "probe")
public class Probe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "x", column = @Column(name = "x_coordinates")),
			@AttributeOverride(name = "y", column = @Column(name = "y_coordinates")),
	})
	private Position position;

	@Enumerated(EnumType.STRING)
	@Column(name = "direction")
	private Direction direction;


	@Column(name = "plateau_id")
	@NotNull
	private Long plateauId;

	Probe() {
	}

	public Probe(Position position, Direction direction) {

		this.position = position;
		this.direction = direction;
	}

	public Long getId() {

		return id;
	}

	public Long getPlateauId() {
		return plateauId;
	}

	public void setPlateauId(Long plateauId) {
		this.plateauId = plateauId;
	}

	public Direction getDirection() {
		return direction;
	}

	public Position getPosition() {

		return position;
	}

	public void setDirection(Direction direction) {

		this.direction = direction;
	}

	public Probe handleCommand(char command) {
		if (command == 'M')
			this.position = move(position);
		else
			this.direction = turn(command);

		return this;
	}

	private Position move(Position position) {
		return direction.move(position);
	}

	private Direction turn(char side) {
		return direction.turn(side);
	}
}
