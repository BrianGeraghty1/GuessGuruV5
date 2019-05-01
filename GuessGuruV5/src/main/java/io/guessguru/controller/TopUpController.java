package io.guessguru.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import io.guessguru.entities.User;
import io.guessguru.services.UserService;

@Controller
public class TopUpController {
	
	@Autowired
	private UserService userService;
	@GetMapping("/topup") 
	public String showTopUpForm(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findOne(auth.getName());
		
		model.addAttribute("balance",user.getBalance());
		return "views/topUp";  
	}
	
	@PostMapping("/topup")
	public String topUpAccount(HttpServletRequest request) {
		try {
		Stripe.apiKey = "sk_test_7x0kQrQs5YvQkGVxETquMVy1001D0sHu3B";
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		double price = (Double.parseDouble(request.getParameter("amount"))*100);
		int topUpPrice = (int) Math.round(price);
		chargeParams.put("amount", topUpPrice);
		chargeParams.put("currency", "eur");
		chargeParams.put("description", "Top up");
		chargeParams.put("source", "tok_amex");
		
			Charge.create(chargeParams);
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findOne(auth.getName());
		double topup = Double.parseDouble(request.getParameter("amount"));
		userService.topUpAccount(user, topup);
		
		return "views/successPayment";
		
	}
	
	@GetMapping("/withdraw") 
	public String showWithdrawForm(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findOne(auth.getName());
		
		model.addAttribute("balance",user.getBalance());
		return "views/withdraw";  
	}
	
	@PostMapping("/withdraw")
	public String withdraw(HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findOne(auth.getName());
		double amount =Double.parseDouble(request.getParameter("amount"));
		if(amount<=user.getBalance()) {
		try {
		Stripe.apiKey = "sk_test_7x0kQrQs5YvQkGVxETquMVy1001D0sHu3B";
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		double price = (Double.parseDouble(request.getParameter("amount"))*100);
		int eventPrice = (int) Math.round(price);
		chargeParams.put("amount", eventPrice);
		chargeParams.put("currency", "eur");
		chargeParams.put("description", "Withdrawal");
		chargeParams.put("source", "tok_amex");
		
			Charge.create(chargeParams);
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double topup = Double.parseDouble(request.getParameter("amount"));
		userService.withdrawAccount(user, topup);
		
		return "views/successWithdraw";}
		else {return "views/errorWithdraw";}
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
