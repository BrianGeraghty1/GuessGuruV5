package io.guessguru.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Fixture {
	
	@Id
	private String id;
	private String date;
	private int matchPlayed;
	private String homeTeam;
	private int homeScore;
	private String awayTeam;
	private int awayScore;
	@ManyToMany(mappedBy = "fixtures")
	private Set<Tournament> tournaments;
	@OneToMany(mappedBy = "fixture")
	private List<Prediction> predictions;
	
	public List<Prediction> getPredictions() {
		return predictions;
	}
	public void setPredictions(List<Prediction> predictions) {
		this.predictions = predictions;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public int getHomeScore() {
		return homeScore;
	}
	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	public int getAwayScore() {
		return awayScore;
	}
	public void setAwayScore(int awayScore) {
		this.awayScore = awayScore;
	}
	
	
	public int getMatchPlayed() {
		return matchPlayed;
	}
	public void setMatchPlayed(int matchPlayed) {
		this.matchPlayed = matchPlayed;
	}
	public Set<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(Set<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

	
	public Fixture(String id, String date, int matchPlayed, String homeTeam, int homeScore, String awayTeam,
			int awayScore ) {
		this.id = id;
		this.date = date;
		this.matchPlayed = matchPlayed;
		this.homeTeam = homeTeam;
		this.homeScore = homeScore;
		this.awayTeam = awayTeam;
		this.awayScore = awayScore;
	}
	public Fixture() {
		
}	}
	

