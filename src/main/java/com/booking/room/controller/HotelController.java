package com.booking.room.controller;

import java.util.Map;

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
import com.booking.room.form.CancelBookingForm;
import com.booking.room.form.CreateBookingForm;
import com.booking.room.model.AlertModal;
import com.booking.room.service.HotelCommonService;
import com.booking.room.service.HotelService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HotelController {
	
	@Autowired
	HotelCommonService commonService;
	
	@Autowired
	HotelService hotelService;

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		this.commonService.msgDelivery(model, session);
		model.addAttribute("Auth", session.getAttribute("Auth"));
		model.addAttribute("rooms", this.hotelService.getRoomBooking());
		return "screens/index";
	}

	@PostMapping("/booking/create")
	public String createBooking(@ModelAttribute CreateBookingForm bookingForm, HttpSession session) {
		User authUser = (User) session.getAttribute("Auth");
		if (authUser == null) {
			return "redirect:/login";
		}

		// checkInRoom
		Room bookRoom = this.hotelService.getRoomByFlag(bookingForm.getRoomId(), 0);
		if (bookRoom == null) {
			// error msg
		} else {
			this.hotelService.createBooking(bookingForm.getRoomId(), authUser.getId());
			session.setAttribute("message", new AlertModal("ありがとう!", bookingForm.getRoomNumber()+" を予約しました."));
		}
		
		return "redirect:/";
	}

	@PostMapping("/booking/cancel")
	public String cancelBooking(@ModelAttribute CancelBookingForm cancelBookingForm, HttpSession session) {
		User authUser = (User) session.getAttribute("Auth");
		if (authUser == null) {
			return "redirect:/login";
		}
		
		// checkOutRoom
		Room bookRoom = this.hotelService.getRoomByFlag(cancelBookingForm.getCancelRoomId(), 1);
		if (bookRoom == null) {
			// error msg
		} else {
			System.out.println(cancelBookingForm.getBookingId());
			this.hotelService.cancelBooking(cancelBookingForm.getCancelRoomId(), cancelBookingForm.getBookingId());
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
