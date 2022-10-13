package com.elo7.probe_spring.services;

import com.elo7.probe_spring.models.Plateau;
import com.elo7.probe_spring.models.Probe;
import com.elo7.probe_spring.repositories.PlateauRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlateauService {

	private final PlateauRepository repository;

	public PlateauService(PlateauRepository repository) {

		this.repository = repository;
	}

	public List<Plateau> findAll() {

		return repository.findAll();
	}

	public Optional<Plateau> findById(Long id) {

		return repository.findById(id);
	}

	public Plateau create(Plateau newPlateau) {
		return repository.save(newPlateau);
	}

	public Optional<Probe> findProbeInPlateauById(Long plateauId, Long probeId) {

		return this.findById(plateauId)
				.flatMap(plateau -> plateau.getProbeById(probeId));
	}

	public Optional<Probe> insertProbeAtPlateau(Long plateauId, Probe newProbe) {
		return this.findById(plateauId)
				.map(plateau -> plateau.insertProbe(newProbe))
				.map(repository::save)
				.map(Plateau::getProbes)
				.flatMap(probes -> probes
						.stream()
						.filter(probe -> probe.getPosition().equals(newProbe.getPosition()))
						.findFirst());

	}

	public Optional<Probe> moveProbeInPlateau(Long plateauId, Long probeId, String command) {
		return this.findById(plateauId)
				.map(plateau -> plateau.moveProbe(probeId, command))
				.map(repository::save)
				.flatMap(plateau -> plateau.getProbeById(probeId));
	}
}
