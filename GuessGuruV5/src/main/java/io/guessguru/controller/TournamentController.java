package io.guessguru.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.guessguru.entities.Fixture;
import io.guessguru.entities.Points;
import io.guessguru.entities.PreviousBalance;
import io.guessguru.entities.Tournament;
import io.guessguru.entities.User;
import io.guessguru.services.FixtureService;
import io.guessguru.services.PointsService;
import io.guessguru.services.PreviousBalanceService;
import io.guessguru.services.TournamentService;
import io.guessguru.services.UserService;

@Controller
public class TournamentController {

	@Autowired
	private TournamentService tournamentService;
	@Autowired
	private UserService userService;
	@Autowired
	private FixtureService fixtureService;
	@Autowired
	private PointsService pointsService;
	@Autowired
	private PreviousBalanceService previousBalanceService;
	@Autowired
	private PointsController pointsController;
	
	@GetMapping("/tournaments")
	public String listUsers(Model model) {
		model.addAttribute("tournaments", tournamentService.findByActive());
		return "views/tournamentList";
	}

	@GetMapping("/addTournament")
	public String registerForm(Model model) {
		
		model.addAttribute("tournament", new Tournament());
		return "views/addTournament";
	}

	@PostMapping("/addTournament")
	public String registerUser(@Valid Tournament tournament, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "views/addTournament";
		}
		tournamentService.createTournament(tournament);

		return "views/successTourneyReg";

	}

	@GetMapping("/addTournamentUser")
	public String addTournamentUser(@RequestParam(name = "id") String tournamentId, HttpSession session) {

		Long tournId = Long.parseLong(tournamentId);
		Tournament tournament2 = tournamentService.getTournament(tournId);
		session.setAttribute("tournament", tournament2);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		session.setAttribute("email", auth.getName());
		return "views/regTournConfirmation";

	}

	@PostMapping("/addTournamentUser")
	public String registerTournamentUser(HttpSession session) {
		
		String email = (String) session.getAttribute("email");
		Tournament tournament = (Tournament) session.getAttribute("tournament");
		
		User user = userService.findOne(email);
		if(user.getBalance()<tournament.getBuyIn()) {
			return "views/topUp";
		}
		else {
		tournamentService.addUser(tournament, user);
		return "views/successRegistration";
		}
		
			
		
		
	}

	@GetMapping("/viewTournament")
	public String showTournamentPage(Session session, Model model, @RequestParam(name = "id") String tournamentId) {
		
		Long tournId = Long.parseLong(tournamentId);
		Tournament tournament = tournamentService.getTournament(tournId);
		List<Points> points = pointsService.findByTournament(tournament);
		Set<Fixture> fixtures = fixtureService.findTournamentFixtures(tournament);
		model.addAttribute("fixtures", fixtures);
		boolean finished = true;
		for (Fixture fixJ:fixtures) {
			if(fixJ.getMatchPlayed()!=1) {
				finished=false;
			}
			
		}
		if (finished&& tournament.getActive()==0) {
			User winner = points.get(0).getUser();
			User runnerUp = points.get(1).getUser();
			PreviousBalance balance = new PreviousBalance(winner, winner.getBalance());
			previousBalanceService.savePreviousBalance(balance);
			PreviousBalance balance2 = new PreviousBalance(runnerUp, runnerUp.getBalance());
			previousBalanceService.savePreviousBalance(balance);
			winner.setBalance(winner.getBalance()+ ((tournament.getPrizePool()*(0.75))));
			runnerUp.setBalance(runnerUp.getBalance()+ ((tournament.getPrizePool()*(0.25))));
			userService.saveUser(winner);
			userService.saveUser(runnerUp);
			tournament.setActive(1);
			tournamentService.saveTournament(tournament);
		}
		pointsController.compareResults(tournament);
		
		model.addAttribute("points", points);
		model.addAttribute("tournamentId", tournId);
		model.addAttribute("tournamentName", tournament.getName());
		model.addAttribute("users", tournament.getUsers());
		
		model.addAttribute("playerAmount", tournament.getPlayerAmount());
		model.addAttribute("prizePool", tournament.getPrizePool());
		model.addAttribute("buyIn", tournament.getBuyIn());
		
		return "views/tourneyInfoPage";
	}

}
