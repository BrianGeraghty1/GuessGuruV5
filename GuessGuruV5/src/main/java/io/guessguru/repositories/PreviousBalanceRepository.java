package io.guessguru.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.guessguru.entities.Points;
import io.guessguru.entities.PreviousBalance;
import io.guessguru.entities.User;

public interface PreviousBalanceRepository extends JpaRepository<PreviousBalance, Integer> {
	
	List<PreviousBalance> findByUserOrderByIdAsc(User user);
	
}
