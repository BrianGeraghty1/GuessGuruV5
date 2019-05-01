package io.guessguru.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.guessguru.controller.FixtureController;
import io.guessguru.entities.Tournament;
import io.guessguru.entities.User;
import io.guessguru.repositories.TournamentRepository;
import io.guessguru.repositories.UserRepository;

@Service
public class TournamentService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TournamentRepository tournamentRepository;
	@Autowired
	private FixtureController fixtureController;
	
	public void createTournament(Tournament tournament) {
		tournament.setFixtures(fixtureController.parseFixtureList(tournament.getGameweek()));
		tournamentRepository.save(tournament);
	}
	
	public void addUser(Tournament tournament, User user) {
		tournament.registerNewUser(user);
		tournament.setPlayerAmount(tournament.getPlayerAmount()+1);
		tournament.setPrizePool(tournament.getPrizePool()+tournament.getBuyIn());
		user.setBalance(user.getBalance()-tournament.getBuyIn());
		userRepository.save(user);
		tournamentRepository.save(tournament);
	}
	
	public Tournament getTournament(Long tournamentId) {
		return tournamentRepository.getOne(tournamentId);
	}
	
	public boolean isUserPresent(User user, Tournament tournament) {
		List<User> usersRegistered = (List<User>) tournament.getUsers();
		boolean present=false;
		for(int i=0; i<usersRegistered.size(); i++) {
			if (usersRegistered.get(i).equals(user)) {
				present = true;
			}
			else {
				present= false;
			}
			
		}
		return present;
	}
	
	
	public List<Tournament> findAll() {
		return tournamentRepository.findAll();
	}

	
	public List<Tournament> findByName(String name) {
		// TODO Auto-generated method stub
		return  tournamentRepository.findByNameLike("%"+name+"%");
	}
	
}