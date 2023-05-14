package com.booking.room.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.room.entity.RoomBooking;
import com.booking.room.entity.User;
import com.booking.room.repository.HotelBookingMapper;

@Service
public class HotelService {
	@Autowired
	HotelBookingMapper hotelBookingMapper;
	
	public List<RoomBooking> getRoomBooking(){
		return this.hotelBookingMapper.getRoomBooking();
	}
	
	public User getAuthUser(String email, String password) {
		return this.hotelBookingMapper.getAuthUser(email, password);
	}

}
