package io.guessguru.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PreviousBalance")
public class PreviousBalance {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_EMAIL")
	private User user;
	private double prevBalance;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public double getPrevBalance() {
		return prevBalance;
	}
	public void setPrevBalance(double prevBalance) {
		this.prevBalance = prevBalance;
	}
	public PreviousBalance(User user, double prevBalance) {
		this.user = user;
		this.prevBalance = prevBalance;
	}
	
	public PreviousBalance() {
		
	}
	
	
}
