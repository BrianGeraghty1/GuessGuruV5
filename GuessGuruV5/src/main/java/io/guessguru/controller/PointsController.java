package io.guessguru.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import io.guessguru.entities.Fixture;
import io.guessguru.entities.Points;
import io.guessguru.entities.Prediction;
import io.guessguru.entities.Tournament;
import io.guessguru.entities.User;
import io.guessguru.services.FixtureService;
import io.guessguru.services.PointsService;
import io.guessguru.services.PredictionService;
import io.guessguru.services.TournamentService;
import io.guessguru.services.UserService;

@Controller
public class PointsController {


	@Autowired
	private TournamentService tournamentService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private PredictionService predictionService;
	
	@Autowired
	private FixtureService fixtureService;
	
	@Autowired
	private PointsService pointsService;
	
	public int compareTo(int x, int y) {
		return Integer.compare(x, y);
	}
	public void compareResults(Tournament tournament) {
		List<User> users = userService.findByTournament(tournament);
		for (int i=0; i < users.size(); i++) {
			List<Prediction> usersPredictions = predictionService.findPredictionsByUserAndTournament(tournament, users.get(i));
			int userTotal=0;
			for (int j=0; j<usersPredictions.size(); j++) {
				Prediction prediction = usersPredictions.get(j);
				Fixture fixture = prediction.getFixture();
				int fixtureHomeScore = fixture.getHomeScore();
				int fixtureAwayScore = fixture.getAwayScore();
				int predictedHomeScore = prediction.getHomeTeamScore();
				int predictedAwayScore = prediction.getAwayTeamScore();
				if((compareTo(predictedHomeScore, predictedAwayScore)==compareTo(fixtureHomeScore, fixtureAwayScore))) {
					
					if((predictedHomeScore==fixtureHomeScore) && (predictedAwayScore==fixtureAwayScore)) {
						userTotal += 3; 
					}
					else {
						userTotal+=1;
					}
				}
			}
			if(pointsService.checkPoints(users.get(i), tournament)) {
				Points points = pointsService.findByTournamentAndUser(tournament, users.get(i));
				points.setTotal(userTotal);
				pointsService.savePoints(points);
			}
			else {
			Points points = new Points(users.get(i), tournament, userTotal);
			pointsService.savePoints(points);
			}
		}
		
	}
	
}
