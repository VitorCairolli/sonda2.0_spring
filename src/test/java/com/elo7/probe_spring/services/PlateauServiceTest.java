package com.elo7.probe_spring.services;

import com.elo7.probe_spring.models.Plateau;
import com.elo7.probe_spring.models.Position;
import com.elo7.probe_spring.repositories.PlateauRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;
import java.util.random.RandomGenerator;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlateauServiceTest {

    @Mock
    private PlateauRepository repository;

    private PlateauService subject;

    @BeforeEach
    void setup() {
        subject = new PlateauService(repository);
    }

    @Test
    void shouldReturnEmptyWhenPlateauIdDontExist() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Plateau> result = subject.findById(id);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnPlateauWhenPlateauIdExist() {
        Long id = 1L;
        when(repository.findById((id))).thenReturn(Optional.of(new Plateau(new Position(1,1))));

        Optional<Plateau> result = subject.findById(id);

        Assertions.assertTrue(result.isPresent());
    }

}