package io.guessguru.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.guessguru.entities.Points;
import io.guessguru.entities.Prediction;
import io.guessguru.entities.Tournament;
import io.guessguru.entities.User;
import io.guessguru.repositories.PointsRepository;

@Service
public class PointsService {

	@Autowired
	private PointsRepository pointsRepository;

	
	public void savePoints(Points points) {
		pointsRepository.save(points);
	}
	
	public Points findByTournamentAndUser(Tournament tournament, User user) {
		return pointsRepository.findByTournamentAndUser(tournament, user);
	}
	
	public List<Points> findByTournament(Tournament tournament) {
		return pointsRepository.findByTournamentOrderByTotalDesc(tournament);
	}
	
	public boolean checkPoints(User user, Tournament tournament) {
		boolean exists = false;
		List<Points> tournamentPoints = pointsRepository.findByTournament(tournament);
		for(int i =0; i<tournamentPoints.size(); i++) {
			if(user.getEmail().equalsIgnoreCase(tournamentPoints.get(i).getUser().getEmail())) {
				exists=true;
				return exists;
			}
		}
		return exists;
	}
}
