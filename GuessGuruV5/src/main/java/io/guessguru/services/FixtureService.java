package io.guessguru.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.guessguru.entities.Fixture;
import io.guessguru.entities.Tournament;
import io.guessguru.repositories.FixtureRepository;

@Service
public class FixtureService {

	@Autowired
	private FixtureRepository fixtureRepository;

	public void createFixture(Fixture fixture) {
		fixtureRepository.saveAndFlush(fixture);
	}
	
	public List<Fixture> findAll() {
		return fixtureRepository.findAll();
	}
	
	public Set<Fixture> findTournamentFixtures(Tournament tournament) {
		return fixtureRepository.findByTournaments(tournament);
	}
	
	public List<Fixture> findByName(String name) {
		// TODO Auto-generated method stub
		List<Fixture> fixtures = new ArrayList<>();
		fixtures.addAll(fixtureRepository.findByhomeTeamLike("%"+name+"%"));
		fixtures.addAll(fixtureRepository.findByawayTeamLike("%"+name+"%"));
		return  fixtures;
	}
	public Fixture findById(String id) {
		return fixtureRepository.findOne(id);
	}


}
