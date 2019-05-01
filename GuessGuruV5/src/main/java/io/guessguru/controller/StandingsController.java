package io.guessguru.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import io.guessguru.entities.Standing;
import io.guessguru.services.StandingService;

@Controller
public class StandingsController {
	@Autowired
	 private StandingService standingService;

	public List<Standing> parseLeagueStandings() {

		// Working code for adding objects to the leader

		HttpResponse<JsonNode> response = null;
		try {

			response = Unirest.get(
					"https://sportsop-soccer-sports-open-data-v1.p.rapidapi.com/v1/leagues/premier-league/seasons/18-19/standings")
					.header("X-RapidAPI-Key", "2NnKY7E3SwmshFmxcGq1no8kPyZ8p1A4dxJjsnhap8STtoc62Y").asJson();

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		// retrieve the parsed JSONObject from the response
		JSONObject myObj = response.getBody().getObject();
		JSONObject object = myObj.getJSONObject("data");
		JSONArray array = object.getJSONArray("standings");
		List<Standing> standings = new ArrayList<Standing>();
		for (int i = 0; i <= 19; i++) {
			JSONObject team = array.optJSONObject(i);
			JSONObject points = team.getJSONObject("overall");
			String teamName = team.getString("team");
			int pointsTotal = Integer.parseInt(points.get("points").toString());
			int position = i + 1;
			Standing standing = new Standing(position, teamName, pointsTotal);
			standings.add(standing);
		}
		return standings;

	}
	
	@GetMapping("/standings")
	public String addStandings(Model model) {
		List<Standing> standings = parseLeagueStandings();
		standingService.createStandings(standings);
		model.addAttribute("standings", standings);
		return "views/premierLeaderboard";
		
	}

	// extract fields from the object

}
