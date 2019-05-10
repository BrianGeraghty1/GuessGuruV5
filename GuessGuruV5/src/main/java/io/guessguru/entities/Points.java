package io.guessguru.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Points {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_EMAIL")
	private User user;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TOURNAMENT_ID")
	private Tournament tournament;
	private int total;


	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public Points( User user,Tournament tournament, int total) {
		this.user = user;
		this.tournament = tournament;
		this.total = total;
	}

	public Points() {

	}

}
