package com.booking.room.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import com.booking.room.entity.Room;
import com.booking.room.entity.User;
import com.booking.room.form.CreateBookingForm;
import com.booking.room.service.HotelService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HotelController {
	@Autowired
	HotelService hotelService;

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		model.addAttribute("Auth", session.getAttribute("Auth"));
		model.addAttribute("rooms", this.hotelService.getRoomBooking());
		return "screens/index";
	}

	@PostMapping("/create/booking")
	public String createBooking(@ModelAttribute CreateBookingForm bookingForm, HttpSession session) {
		User authUser = (User) session.getAttribute("Auth");
		if (authUser == null) {
			return "redirect:/login";
		}
		
		// checkInRoom
		Room bookRoom = this.hotelService.getRoomByFlag(bookingForm.getRoomId(), 0);
		if(bookRoom == null) {
			// error msg
		}else {
			this.hotelService.createBooking(bookingForm.getRoomId(), authUser.getId());
			// change room flag
		}
		
		return "redirect:/";
	}

	@GetMapping("/login")
	public String login() {
		return "screens/login";
	}

	@PostMapping("/login")
	public String login(Model model, HttpSession session) {
		System.out.println("Post mapp");
		session.setAttribute("Auth", this.hotelService.getAuthUser("mrs.amie@gmail.com", "123456"));
		return "redirect:/";
	}

}
