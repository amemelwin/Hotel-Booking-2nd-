package com.booking.room.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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

}
