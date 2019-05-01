package io.guessguru.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Prediction {

	@Id
	@GeneratedValue
	private Long id;
	private int homeTeamScore;
	private int awayTeamScore;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_EMAIL")
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FIXTURE_ID")
	private Fixture fixture;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOURNAMENT_ID")
	private Tournament tournament;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getHomeTeamScore() {
		return homeTeamScore;
	}

	public void setHomeTeamScore(int homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}

	public int getAwayTeamScore() {
		return awayTeamScore;
	}

	public void setAwayTeamScore(int awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Fixture getFixture() {
		return fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public Prediction( int homeTeamScore, int awayTeamScore, User user, Fixture fixture,
			Tournament tournament) {
		this.homeTeamScore = homeTeamScore;
		this.awayTeamScore = awayTeamScore;
		this.user = user;
		this.fixture = fixture;
		this.tournament = tournament;
	}

	public Prediction() {

	}

}
