package io.guessguru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.guessguru.entities.Standing;
import io.guessguru.entities.Tournament;
import io.guessguru.entities.User;

public interface StandingRepository  extends JpaRepository<Standing, Integer> {
		
}
