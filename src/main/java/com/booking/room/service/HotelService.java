package com.booking.room.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.booking.room.entity.Room;
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

	public Room getRoomByFlag(int roomId, int lendFlag) {
		return this.hotelBookingMapper.getRoomByFlag(roomId, lendFlag);
	}
	
	@Transactional
	public void createBooking( int roomId, int userId) {
		try {			
			this.hotelBookingMapper.createBooking(roomId, userId);
			this.hotelBookingMapper.updateRoom(roomId, 1);
		}catch(Exception e) {
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
		}
	}
	
}
