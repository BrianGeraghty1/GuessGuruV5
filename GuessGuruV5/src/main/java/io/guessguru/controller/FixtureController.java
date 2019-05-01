package io.guessguru.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import io.guessguru.entities.Fixture;
import io.guessguru.entities.Tournament;
import io.guessguru.services.FixtureService;
import io.guessguru.services.TournamentService;

@Controller
public class FixtureController {

	@Autowired
	private FixtureService fixtureService;

	@Autowired
	private TournamentService tournamentService;

	public List<Fixture> parseAllFixtures() {
		HttpResponse<JsonNode> response = null;
		List<Fixture> fixtures = new ArrayList<Fixture>();
		for (int gameweek = 1; gameweek < 39; gameweek++) {
			try {
				response = Unirest.get(
						"https://sportsop-soccer-sports-open-data-v1.p.rapidapi.com/v1/leagues/premier-league/seasons/18-19/rounds/round-"
								+ gameweek + "/matches")
						.header("X-RapidAPI-Key", "2NnKY7E3SwmshFmxcGq1no8kPyZ8p1A4dxJjsnhap8STtoc62Y").asJson();

			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject myResponse = response.getBody().getObject();
			JSONObject object = myResponse.getJSONObject("data");
			JSONArray matches = object.getJSONArray("matches");
			for (int i = 0; i < matches.length(); i++) {
				JSONObject match = matches.getJSONObject(i);
				// match identifier
				String identifier = match.getString("identifier");
				String datePlayed = match.getString("date_match");
				datePlayed = datePlayed.substring(0, 10);
				// home team info
				JSONObject homeTeam = match.getJSONObject("home");
				String homeName = homeTeam.getString("team");
				int homeGoalsScored = homeTeam.getInt("goals");
				// away team info
				JSONObject awayTeam = match.getJSONObject("away");
				String awayName = awayTeam.getString("team");
				int awayGoalsScored = awayTeam.getInt("goals");
				int matchPlayed = match.getInt("played");

				if (matchPlayed < 1) {
					homeGoalsScored = 0;
					awayGoalsScored = 0;
				}
				Fixture fixture = new Fixture(identifier, datePlayed, matchPlayed, homeName, homeGoalsScored, awayName,
						awayGoalsScored);
				fixtures.add(fixture);

			}
		}

		return fixtures;

	}

	public Set<Fixture> parseFixtureList(int gameweek) {

		Set<Fixture> fixtures = new HashSet<Fixture>();
		// Working code for adding objects to the leader

		HttpResponse<JsonNode> response = null;
		try {

			response = Unirest.get(
					"https://sportsop-soccer-sports-open-data-v1.p.rapidapi.com/v1/leagues/premier-league/seasons/18-19/rounds/round-"
							+ gameweek + "/matches")
					.header("X-RapidAPI-Key", "2NnKY7E3SwmshFmxcGq1no8kPyZ8p1A4dxJjsnhap8STtoc62Y").asJson();

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject myResponse = response.getBody().getObject();
		JSONObject object = myResponse.getJSONObject("data");
		JSONArray matches = object.getJSONArray("matches");
		for (int i = 0; i < matches.length(); i++) {
			JSONObject match = matches.getJSONObject(i);
			// match identifier
			String identifier = match.getString("identifier");
			String datePlayed = match.getString("date_match");
			datePlayed = datePlayed.substring(0, 10);
			// home team info
			JSONObject homeTeam = match.getJSONObject("home");
			String homeName = homeTeam.getString("team");
			int homeGoalsScored = homeTeam.getInt("goals");
			// away team info
			JSONObject awayTeam = match.getJSONObject("away");
			String awayName = awayTeam.getString("team");
			int awayGoalsScored = awayTeam.getInt("goals");
			int matchPlayed = match.getInt("played");

			if (matchPlayed < 1) {
				homeGoalsScored = 0;
				awayGoalsScored = 0;
			}
			Fixture fixture = new Fixture(identifier, datePlayed, matchPlayed, homeName, homeGoalsScored, awayName,
					awayGoalsScored);
			fixtures.add(fixture);
		}

		return fixtures;
	}

	@PostMapping("/addFixturesDB")
	public String addFixturesDB() {
		List<Fixture> fixtures = parseAllFixtures();
		for (int i = 0; i < fixtures.size(); i++) {
			Fixture fixture = fixtures.get(i);
			fixtureService.createFixture(fixture);
		}
		return "views/profile";

	}

	@GetMapping("/viewAllFixtures")
	public String addFixtures(Model model, @RequestParam(defaultValue = "") String teamName) {
		List<Fixture> fixtures = fixtureService.findByName(teamName);
		model.addAttribute("fixtures", fixtures);
		return "views/listOfAllFixtures";

	}

/*	@GetMapping("/viewTournamentFixtures")
	public String viewTournamentFixtures(Model model) {
		
		Long tournId = Long.parseLong(tournamentId);
		Tournament tournament = tournamentService.getTournament(tournId);
		Set<Fixture> fixtures = fixtureService.findTournamentFixtures(tournament);
		model.addAttribute("fixtures", fixtures);
		return "views/tournamentFixtureList";

	}*/

}
