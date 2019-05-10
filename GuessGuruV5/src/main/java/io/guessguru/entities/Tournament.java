package io.guessguru.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy=false)
public class Tournament {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	private String name;
	private int buyIn;
	private int prizePool;
	private int playerAmount;
	private int gameweek;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "TOURNAMENT_USERS", joinColumns = {
			@JoinColumn(name = "TOURNAMENT_ID", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "USER_EMAIL", referencedColumnName = "email") })
	@ElementCollection(targetClass = User.class)
	private Set<User> users;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "TOURNAMENT_FIXTURES", joinColumns = {
			@JoinColumn(name = "TOURNAMENT_ID", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "FIXTURE_ID", referencedColumnName = "id") })
	@ElementCollection(targetClass = Fixture.class)
	private Set<Fixture> fixtures = new HashSet<Fixture>();
	@OneToMany(mappedBy = "tournament")
	private List<Prediction> predictions;
	@OneToMany(mappedBy = "tournament")
	private List<Points> points;
	
	private int active;
	
	
	

	public List<Points> getPoints() {
		return points;
	}

	public void setPoints(List<Points> points) {
		this.points = points;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public List<Prediction> getPredictions() {
		return predictions;
	}

	public void setPredictions(List<Prediction> predictions) {
		this.predictions = predictions;
	}

	public int getGameweek() {
		return gameweek;
	}

	public void setGameweek(int gameweek) {
		this.gameweek = gameweek;
	}

	public Set<Fixture> getFixtures() {
		return fixtures;
	}

	public void setFixtures(Set<Fixture> fixtures) {
		this.fixtures = fixtures;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getBuyIn() {
		return buyIn;
	}

	public void setBuyIn(int buyIn) {
		this.buyIn = buyIn;
	}

	public int getPrizePool() {
		return prizePool;
	}

	public void setPrizePool(int prizePool) {
		this.prizePool = prizePool;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public int getPlayerAmount() {
		return playerAmount;
	}

	public void setPlayerAmount(int playerAmount) {
		this.playerAmount = playerAmount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Tournament(String name, int buyIn, int gameweek) {
		this.name = name;
		this.buyIn = buyIn;
		this.gameweek = gameweek;
		this.active=0;
	}

	public Tournament() {

	}

	public void registerNewUser(User user) {
		users.add(user);
	}

}
