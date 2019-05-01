package io.guessguru.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import io.guessguru.entities.Fixture;
import io.guessguru.entities.Tournament;

public interface FixtureRepository extends JpaRepository<Fixture, String> {
	
	List<Fixture> findByhomeTeamLike(String homeTeam);
	List<Fixture> findByawayTeamLike(String awayTeam);
	Set<Fixture> findByTournaments(Tournament tournament);

}
