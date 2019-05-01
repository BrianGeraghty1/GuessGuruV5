package io.guessguru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.guessguru.entities.Fixture;
import io.guessguru.entities.Prediction;
import io.guessguru.entities.Tournament;
import io.guessguru.entities.User;

public interface PredictionRepository extends JpaRepository<Prediction, Integer> {
	
	List<Prediction> findByUser(User user);
	List<Prediction> findByTournament(Tournament tournament);

}
