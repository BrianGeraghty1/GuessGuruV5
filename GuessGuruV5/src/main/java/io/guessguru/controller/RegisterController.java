package io.guessguru.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import io.guessguru.entities.PreviousBalance;
import io.guessguru.entities.User;
import io.guessguru.services.PreviousBalanceService;
import io.guessguru.services.UserService;

@Controller
public class RegisterController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PreviousBalanceService previousBalanceService;

	@GetMapping("/register")
	public String registerForm(Model model) {

		model.addAttribute("user", new User());
		return "views/registerForm";
	}
	
	
	
	@PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "views/registerForm";
		}
		if(userService.isUserPresent(user.getEmail())) {
			model.addAttribute("exist",true);

			return "views/registerForm";

		}
		userService.createUser(user);
		PreviousBalance balance = new PreviousBalance(user, 0);
		previousBalanceService.savePreviousBalance(balance);
		return "views/success";

	}

}
