package com.booking.room.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.booking.room.entity.RoomBooking;
import com.booking.room.entity.User;

@Mapper
public interface HotelBookingMapper {
	
	public List<RoomBooking> getRoomBooking ();
	
	public User getAuthUser(@Param("email") String email,@Param("password") String password);
	

}
