package com.booking.room.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.booking.room.service.HotelService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HotelController {
	@Autowired
	HotelService hotelService;
	
	@GetMapping("/")
	public String index(Model model,HttpSession session) {
		model.addAttribute("Auth",session.getAttribute("Auth"));
		model.addAttribute("rooms",this.hotelService.getRoomBooking());
		return "screens/index";
	}
	
	@GetMapping("/login")
	public String login(HttpSession session) {
		session.setAttribute("Auth", this.hotelService.getAuthUser("mrs.amie@gmail.com","123456"));
		return "redirect:/";
		
	}

}
