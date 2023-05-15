package com.booking.room.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.booking.room.entity.Room;
import com.booking.room.entity.RoomBooking;
import com.booking.room.entity.User;
import com.booking.room.form.CancelBookingForm;
import com.booking.room.form.CreateBookingForm;
import com.booking.room.model.AlertModal;
import com.booking.room.service.HotelCommonService;
import com.booking.room.service.HotelService;
import com.booking.room.form.LoginForm;
import com.booking.room.form.SignupForm;

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
			session.setAttribute("message", new AlertModal("データ不整合エラー!", "申し訳ございません。予約をできませんでした！"));
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
		RoomBooking booking = this.hotelService.getBookingById(cancelBookingForm.getBookingId());

		if (bookRoom == null || booking == null) {
			// error msg
			System.out.println(bookRoom);
			System.out.println(booking);
			session.setAttribute("message", new AlertModal("データ不整合エラー!", "申し訳ございません。予約キャンセルをできませんでした！"));
		} else {
			this.hotelService.cancelBooking(cancelBookingForm.getCancelRoomId(), cancelBookingForm.getBookingId());
			session.setAttribute("message", new AlertModal("ありがとう!","Booking Room をキャンセルしました."));
		}
		
		return "redirect:/";
	}
 
//	if (this.commonHelper.checkAuth(session) == null) {
//		LoginForm newForm = new LoginForm();
//		newForm.setEmail((String) session.getAttribute("email"));
//		session.removeAttribute("email");
//		model.addAttribute("loginForm", newForm);
	@GetMapping("/login")
	public String login(Model model) {
		LoginForm newForm = new LoginForm();
		model.addAttribute("loginForm",newForm);
		return "screens/login";
	}

	@PostMapping("/login")
	public String login(Model model, HttpSession session) {
		session.setAttribute("Auth", this.hotelService.getAuthUser("mrs.amie@gmail.com", "123456"));
		return "redirect:/";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		SignupForm form = new SignupForm();
		model.addAttribute("signUpForm",form);
		return "screens/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute SignupForm signUpForm,HttpSession session) {
		this.hotelService.createUser(signUpForm);
		return "redirect:/";
	}

}
