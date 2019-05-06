package io.guessguru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.guessguru.entities.Tournament;
import io.guessguru.entities.User;

public interface UserRepository  extends JpaRepository<User, String> {

	List<User> findByNameLike(String name); 
	public User findByName(String name);
	List<User> findByTournaments(Tournament tournament);

}
