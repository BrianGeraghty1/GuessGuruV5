package io.guessguru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.guessguru.entities.Fixture;
import io.guessguru.entities.Points;
import io.guessguru.entities.Prediction;
import io.guessguru.entities.Tournament;
import io.guessguru.entities.User;

public interface PointsRepository extends JpaRepository<Points, Integer> {
	
	List<Points> findByUser(User user);
	List<Points> findByTournamentOrderByTotalDesc(Tournament tournament);
	Points findByTournamentAndUser(Tournament tournament, User user);
	List<Points> findByTournament(Tournament tournament);
}
