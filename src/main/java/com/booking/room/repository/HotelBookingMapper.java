package com.booking.room.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.booking.room.entity.RoomBooking;

@Mapper
public interface HotelBookingMapper {
	
	public List<RoomBooking> getRoomBooking ();
	

}
