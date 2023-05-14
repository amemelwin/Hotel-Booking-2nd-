package com.booking.room.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.booking.room.service.HotelService;

@Controller
public class HotelController {
	@Autowired
	HotelService hotelService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("rooms",this.hotelService.getRoomBooking());
		return "screens/index";
	}

}
