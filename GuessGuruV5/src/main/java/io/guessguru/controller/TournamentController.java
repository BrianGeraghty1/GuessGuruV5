package io.guessguru.controller;

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
import io.guessguru.entities.Tournament;
import io.guessguru.entities.User;
import io.guessguru.services.FixtureService;
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

	@GetMapping("/tournaments")
	public String listUsers(Model model, @RequestParam(defaultValue = "") String name) {
		model.addAttribute("tournaments", tournamentService.findByName(name));
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
		System.out.println(tournament2.getName());
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
		Set<Fixture> fixtures = fixtureService.findTournamentFixtures(tournament);
		model.addAttribute("fixtures", fixtures);
		model.addAttribute("tournamentId", tournId);
		model.addAttribute("tournamentName", tournament.getName());
		model.addAttribute("users", tournament.getUsers());
		
		model.addAttribute("playerAmount", tournament.getPlayerAmount());
		model.addAttribute("prizePool", tournament.getPrizePool());
		model.addAttribute("buyIn", tournament.getBuyIn());
		
		return "views/tourneyInfoPage";
	}

}
