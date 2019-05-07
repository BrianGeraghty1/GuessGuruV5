package io.guessguru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.guessguru.entities.Tournament;
import io.guessguru.entities.User;

public interface TournamentRepository  extends JpaRepository<Tournament, Long> {

	List<Tournament> findByNameLike(String name);
	/*List<User> findAllRegistered(Tournament tournament);*/

	//List<Tournament> findByActiveAndNameLikeAnd(int active,String string);
	List<Tournament> findByActive(int active);
}
