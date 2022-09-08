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

    @Autowired
    private PlateauRepository repository;

    @Autowired
    InvalidPlateauException invalidPlateauException;

    public List<Plateau> findAll(){
        return repository.findAll();
    }

    public Optional<Plateau> findById(Long id){
        Optional<Plateau> plateau = repository.findById(id);
        if(plateau.isEmpty()){
            invalidPlateauException.setBody("Plateau not found");
            throw invalidPlateauException;
        }
        return plateau;
    }

    public Plateau create(Plateau newPlateau){
        if((newPlateau.getPosition1().getX() == newPlateau.getPosition2().getX()) ||
        (newPlateau.getPosition1().getY() == newPlateau.getPosition2().getY())) {
            invalidPlateauException.setBody("Invalid plateau coordinates");
            throw invalidPlateauException;
        }


        if(newPlateau.getPosition1().getX() > newPlateau.getPosition2().getX()) {
            var temp = newPlateau.getPosition1().getX();
            newPlateau.getPosition1().setX(newPlateau.getPosition2().getX());
            newPlateau.getPosition2().setX(temp);
        }

        if(newPlateau.getPosition1().getY() > newPlateau.getPosition2().getY()) {
            var temp = newPlateau.getPosition1().getY();
            newPlateau.getPosition1().setY(newPlateau.getPosition2().getY());
            newPlateau.getPosition2().setY(temp);
        }

        List<Plateau> allPlateaus = repository.findAll();

        for (Plateau plateau: allPlateaus) {
            if(occupySameArea(plateau, newPlateau)){
                invalidPlateauException.setBody("Another plateau is occupying the same space");
                throw invalidPlateauException;
            }
        }

        return repository.save(newPlateau);
    }

    private boolean occupySameArea(Plateau plateau1, Plateau plateau2) {
        if (plateau1.getPosition1().getX() > plateau2.getPosition2().getX() ||
            plateau1.getPosition1().getY() > plateau2.getPosition2().getY() ||
            plateau1.getPosition2().getX() < plateau2.getPosition1().getX() ||
            plateau1.getPosition2().getY() < plateau2.getPosition1().getY()) {
            return false;
        }

        return true;
    }
}
