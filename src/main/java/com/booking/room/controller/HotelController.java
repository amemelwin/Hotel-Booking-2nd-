package com.booking.room.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import jakarta.validation.Valid;

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
		// Booking Form Validate
		if (authUser == null) {
			return "redirect:/login";
		}
		if(!this.commonService.bookingFormValidate(bookingForm, session)) {
			return "redirect:/";
		}
		// checkInRoom
		Room bookRoom = this.hotelService.getRoomByFlag(Integer.parseInt(bookingForm.getRoomId()), 0);
		if (bookRoom == null) {
			// error msg
			session.setAttribute("message", new AlertModal("データ不整合エラー!", "申し訳ございません。予約をできませんでした！"));
		} else {
			this.hotelService.createBooking(Integer.parseInt(bookingForm.getRoomId()), authUser.getId());
			session.setAttribute("message", new AlertModal("ありがとう!", bookingForm.getRoomNumber() + " を予約しました."));
		}

		return "redirect:/";
	}

	@PostMapping("/booking/cancel")
	public String cancelBooking(@ModelAttribute CancelBookingForm cancelBookingForm, HttpSession session) {
		User authUser = (User) session.getAttribute("Auth");
		if (authUser == null) {
			return "redirect:/login";
		}
		
		if(!this.commonService.cancelBookingValidator(cancelBookingForm, session)) {
			return "redirect:/";
		}

		// checkOutRoom
		int validRoomCount = this.hotelService.checkOutRoom(Integer.parseInt(cancelBookingForm.getCancelRoomId()),
				Integer.parseInt(cancelBookingForm.getBookingId()), authUser.getId());
		if (validRoomCount != 1 ) {
			// error msg
			session.setAttribute("message", new AlertModal("データ不整合エラー!", "申し訳ございません。予約キャンセルをできませんでした！"));
		} else {
			this.hotelService.cancelBooking(Integer.parseInt(cancelBookingForm.getCancelRoomId()), Integer.parseInt(cancelBookingForm.getBookingId()));
			session.setAttribute("message", new AlertModal("ありがとう!", "Booking Room をキャンセルしました."));
		}

		return "redirect:/";
	}

	@GetMapping("/login")
	public String login(Model model) {
		LoginForm newForm = new LoginForm();
		model.addAttribute("loginForm", newForm);
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
		model.addAttribute("signUpForm", form);
		return "screens/signup";
	}

	@PostMapping("/signup")
	public String signup(@Valid @ModelAttribute SignupForm signUpForm, HttpSession session,BindingResult result,Model model) {
		if(result.hasErrors()){
			return "screens/signup";
		}
		// check password and confirm password
		if (!signUpForm.getConfirmPassword().equals(signUpForm.getPassword())) {
			model.addAttribute("confirm_error", "The password and confirmation password do not match.");
			return "screen/signup";
		}
		

		this.hotelService.createUser(signUpForm);
		return "redirect:/";
	}

}
