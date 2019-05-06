package io.guessguru.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.guessguru.entities.Fixture;
import io.guessguru.entities.Prediction;
import io.guessguru.entities.Tournament;
import io.guessguru.entities.User;
import io.guessguru.services.FixtureService;
import io.guessguru.services.PredictionService;
import io.guessguru.services.TournamentService;
import io.guessguru.services.UserService;

@Controller
public class PredictionController {

	@Autowired
	private FixtureService fixtureService;

	@Autowired
	private TournamentService tournamentService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private PredictionService predictionService;



	@GetMapping("/predictionForm")
	public String viewForm(Model model, @RequestParam("id") String id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findOne(auth.getName());
		Long tournId = Long.parseLong(id);
		Tournament tournament = tournamentService.getTournament(tournId);
		if(tournamentService.isUserPresent(user, tournament)) {
			Set<Fixture> fixtures = fixtureService.findTournamentFixtures(tournament);
			model.addAttribute("fixtures", fixtures);
			model.addAttribute("tournId", tournId);
			return "views/predictionForm";
		}
		else {
			String errorPredictionExists = "";
			model.addAttribute("errorPredictionExists", errorPredictionExists);
			model.addAttribute("tournaments", tournamentService.findByName(""));
			return "views/tournamentList";
		}
		

	}
	
	@PostMapping("/predictionForm")
	public String makePrediction(Model model,HttpServletRequest request, 
			@RequestParam("fixtureId") String fixtureId, @RequestParam("tournId") Long tournId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findOne(auth.getName());
		Tournament tournament = tournamentService.getTournament(tournId);
		Set<Fixture> fixtures = fixtureService.findTournamentFixtures(tournament);
		Fixture fixture = fixtureService.findById(fixtureId);
		int homeScore = Integer.parseInt(request.getParameter("homeScore"));
		int awayScore = Integer.parseInt(request.getParameter("awayScore"));
		if (predictionService.checkPredictions(user, fixtureId)) {
				Prediction prediction = predictionService.findExistingPredictionFromUser(user, fixture);
				
				prediction.setHomeTeamScore(homeScore);
				prediction.setAwayTeamScore(awayScore);
				predictionService.savePrediction(prediction);
				String predictionUpdated = fixture.getId();
				model.addAttribute("predictionUpdated", predictionUpdated);
				model.addAttribute("fixtures", fixtures);
				model.addAttribute("tournId", tournId);
				return "views/predictionForm";
		}
		Prediction prediction = new Prediction(homeScore, awayScore, user, fixture, tournament);
		predictionService.savePrediction(prediction);
		String predictionSuccess = fixture.getId();
		model.addAttribute("predictionSuccess", predictionSuccess);
		model.addAttribute("fixtures", fixtures);
		model.addAttribute("tournId", tournId);
		
		return "views/predictionForm";
	}
}
