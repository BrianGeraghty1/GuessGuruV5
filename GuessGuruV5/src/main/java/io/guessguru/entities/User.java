package io.guessguru.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class User {

	@Id
	@Email
	@NotEmpty
	@Column(unique = true)
	private String email;
	@NotEmpty
	private String name;
	@Size(min = 4)
	private String password;
	private double balance;
	@ManyToMany(mappedBy = "users")
	private Set<Tournament> tournaments = new HashSet<Tournament>();
	@OneToMany(mappedBy = "user")
	private List<Prediction> predictions;
	@OneToMany(mappedBy = "user")
	private List<Points> points;
	@OneToMany(mappedBy = "user")
	private List<PreviousBalance> previousBalances;

	@JoinTable(name = "USER_ROLES", joinColumns = {
			@JoinColumn(name = "USER_EMAIL", referencedColumnName = "email") }, inverseJoinColumns = {
					@JoinColumn(name = "ROLE_NAME", referencedColumnName = "name") })
	@ElementCollection(targetClass = Role.class)
	private List<Role> roles;

	public List<Points> getPoints() {
		return points;
	}

	public void setPoints(List<Points> points) {
		this.points = points;
	}

	public List<Prediction> getPredictions() {
		return predictions;
	}

	public void setPredictions(List<Prediction> predictions) {
		this.predictions = predictions;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(Set<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

	public User(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.previousBalances = new ArrayList<>();
	}

	
	public List<PreviousBalance> getPreviousBalances() {
		return previousBalances;
	}

	public void setPreviousBalances(List<PreviousBalance> previousBalances) {
		this.previousBalances = previousBalances;
	}
	

	public User() {

	}

}
