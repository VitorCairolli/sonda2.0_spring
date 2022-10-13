package com.elo7.probe_spring.models;

import com.elo7.probe_spring.exceptions.InvalidProbeException;
import com.elo7.probe_spring.exceptions.ProbeCollisionException;
import com.elo7.probe_spring.exceptions.ProbeOutOfPlateauException;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name = "plateau")
public class Plateau {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Transient
	private Position minPosition;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "x", column = @Column(name = "max_x_coordinates")),
			@AttributeOverride(name = "y", column = @Column(name = "max_y_coordinates")),
	})
	private Position maxPosition;

	@OneToMany(mappedBy = "plateauId", cascade = CascadeType.ALL)
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

	public Optional<Probe> getProbeById(Long probeId) {
		return this.probes
				.stream()
				.filter(probe -> probe.getId().equals(probeId))
				.findFirst();
	}

	public List<Probe> getProbes() {
		return probes;
	}

	public Plateau moveProbe(Long probeId, @NotEmpty String command) {
		Probe probe = getProbes()
				.stream()
				.filter(filteredProbe -> Objects.equals(filteredProbe.getId(), probeId))
				.findFirst()
				.orElseThrow(() -> new InvalidProbeException("Probe not found"));

		for (int i = 0; i < command.length(); i++) {
			probe = probe.handleCommand(command.charAt(i));
			this.checkPositionValid(probe,
					new ProbeCollisionException("Cannot move, risk collision"),
					new ProbeOutOfPlateauException("Cannot move, it`s dangerous outside plauteu"));
		}

		return this;
	}

	private boolean isInsidePlateau(Probe probe) {

		return (probe.getPosition().getX() >= getMinPosition().getX() &&
				probe.getPosition().getY() >= getMinPosition().getY() &&
				probe.getPosition().getX() <= getMaxPosition().getX() &&
				probe.getPosition().getY() <= getMaxPosition().getY());
	}

	private void checkPositionValid(Probe probe,
									ProbeCollisionException collisionException,
									ProbeOutOfPlateauException outOfPlateauException) {

		if (thereIsProbeWithPosition(probe))
			throw collisionException;

		if (!isInsidePlateau(probe))
			throw outOfPlateauException;
	}

	public Plateau insertProbe(Probe probe) {
		checkPositionValid(probe,
				new ProbeCollisionException("Probe creation error: there already is a probe in this position"),
				new ProbeOutOfPlateauException("Probe creation error: this position is outside the chosen plateau"));

		probe.setPlateauId(this.id);
		probes.add(probe);
		return this;
	}

	private boolean thereIsProbeWithPosition(Probe inputProbe) {
		return probes
				.stream()
				.filter(probe -> !probe.getId().equals(inputProbe.getId()))
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
