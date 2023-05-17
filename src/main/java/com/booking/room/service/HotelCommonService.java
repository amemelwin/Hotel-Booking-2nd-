package com.booking.room.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.booking.room.form.CancelBookingForm;
import com.booking.room.form.CreateBookingForm;
import com.booking.room.model.AlertModal;

import jakarta.servlet.http.HttpSession;

@Service
public class HotelCommonService {

	public void msgDelivery(Model model, HttpSession session) {
		AlertModal alertModal = (AlertModal) session.getAttribute("message");
		if(alertModal != null) {
			model.addAttribute("msg_title", alertModal.getTitle());
			model.addAttribute("msg_body", alertModal.getBody());
			session.removeAttribute("message");
		}else {
			model.addAttribute("msg_title", "");
			model.addAttribute("msg_body", "");
		}
	}
	
	public boolean bookingFormValidate(CreateBookingForm form,HttpSession session) {
		try {
			Integer.parseInt(form.getRoomId());
			return true;			
		}catch(Exception e) {
			session.setAttribute("message", new AlertModal("データ不整合エラー!", "申し訳ございません。予約をできませんでした！"));
			return false;
		}		
	}
	
	public boolean isPasswordSatisfy(String password,String confirmPassword,Model model) {
		if(password.equals(confirmPassword)) {
			return true;
		}else {
			model.addAttribute("confirm_error","Password and Confirm Password are invalid");
			return false;
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean cancelBookingValidator(CancelBookingForm form,HttpSession session) {
		try {
			Integer.parseInt(form.getBookingId());
			Integer.parseInt(form.getCancelRoomId());
			return true;
		}catch(Exception e) {
			session.setAttribute("message", new AlertModal("データ不整合エラー!", "申し訳ございません。予約キャンセルをできませんでした！"));
			return false;
		}
	}
	
}
