package com.elo7.probe_spring.services;

import com.elo7.probe_spring.exceptions.InvalidPlateauException;
import com.elo7.probe_spring.models.Plateau;
import com.elo7.probe_spring.repositories.PlateauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlateauService {

    private final PlateauRepository repository;

    public PlateauService(PlateauRepository repository){

        this.repository = repository;
    }

    public List<Plateau> findAll(){

        return repository.findAll();
    }

    public Optional<Plateau> findById(Long id) {

        return repository.findById(id);
    }

    public Plateau create(Plateau newPlateau){
        return repository.save(newPlateau);
    }
}
