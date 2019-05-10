package io.guessguru.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.guessguru.entities.PreviousBalance;
import io.guessguru.entities.Role;
import io.guessguru.entities.Tournament;
import io.guessguru.entities.User;
import io.guessguru.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PreviousBalanceService previousBalanceService;
	
	public void createUser(User user) {
		BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword())); 
		Role userRole = new Role("USER");
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);
	}
	
	public void createAdmin(User user) {
		BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));  	
		Role userRole = new Role("ADMIN");
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);
	}
	
	public User findOne(String email) {
		
	  return userRepository.findOne(email);
	}
	
	public void topUpAccount(User user, double amount) {
		PreviousBalance balance = new PreviousBalance(user, user.getBalance());
		previousBalanceService.savePreviousBalance(balance);
		user.setBalance(user.getBalance()+amount);
		userRepository.save(user);
	}
	
	public void withdrawAccount(User user, double amount) {
		PreviousBalance balance = new PreviousBalance(user, user.getBalance());
		previousBalanceService.savePreviousBalance(balance);
		user.setBalance(user.getBalance()-amount);
		userRepository.save(user);
	}

	public boolean isUserPresent(String email) {
		// TODO Auto-generated method stub
		User u=userRepository.findOne(email);
		if(u!=null)
			return true;
		
		return false;
	}
	
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	public List<User> findByTournament(Tournament tournament) {
		return userRepository.findByTournaments(tournament);
	}

	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	public List<User> findByName(String name) {
		// TODO Auto-generated method stub
		return  userRepository.findByNameLike("%"+name+"%");
	}

}
