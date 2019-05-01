package io.guessguru.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.guessguru.entities.Standing;
import io.guessguru.entities.Tournament;
import io.guessguru.entities.User;
import io.guessguru.repositories.StandingRepository;

@Service
public class StandingService {

	@Autowired
	private StandingRepository standingRepository;

	public void createStandings(List<Standing> standings) {
		standingRepository.save(standings);
	}

	public void deleteStandings() {
		standingRepository.deleteAll();
	}

}
