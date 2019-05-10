package io.guessguru.controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.guessguru.entities.PreviousBalance;
import io.guessguru.entities.User;
import io.guessguru.services.PreviousBalanceService;
import io.guessguru.services.UserService;

@Controller
public class ProfileController {
	
	@Autowired
	private PreviousBalanceService previousBalanceService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public String showProfilePage(Model model, Principal principal) {
		
		
		String email = principal.getName();
		User user = userService.findOne(email);
		List<PreviousBalance> balances = previousBalanceService.findByUser(user);
		Map<Integer, Double> surveyMap = new LinkedHashMap<>();
		for (int i=0; i<balances.size(); i++) {
			surveyMap.put(balances.get(i).getId(), balances.get(i).getPrevBalance());
		}
		surveyMap.put(balances.size()+1, user.getBalance());
		model.addAttribute("surveyMap", surveyMap);
		model.addAttribute("tournaments", user.getTournaments());
		model.addAttribute("name", user.getName());
		//model.addAttribute("previousBalances", previousBalanceService.findByUser(user));
		
		
		return "views/profile";
	}

}
