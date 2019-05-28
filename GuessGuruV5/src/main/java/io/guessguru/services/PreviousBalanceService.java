package io.guessguru.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.guessguru.entities.Points;
import io.guessguru.entities.PreviousBalance;
import io.guessguru.entities.Tournament;
import io.guessguru.entities.User;
import io.guessguru.repositories.PreviousBalanceRepository;

@Service
public class PreviousBalanceService {

	@Autowired
	private PreviousBalanceRepository previousBalanceRepository;

	
	public void savePreviousBalance(PreviousBalance balance) {
		previousBalanceRepository.save(balance);
	}
	
	public List<PreviousBalance> findByUser(User user) {
		return previousBalanceRepository.findByUserOrderByIdAsc(user);
	}
	
}
