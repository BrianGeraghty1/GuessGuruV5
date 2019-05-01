package io.guessguru.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Standing {
	
	@Id
	private int position;
	private String name;
	private int points;
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	} 
	
	public Standing() {
		
	}
	public Standing(int position, String name, int points) {
		this.position = position;
		this.name = name;
		this.points = points;
	}
	
	
	
}
