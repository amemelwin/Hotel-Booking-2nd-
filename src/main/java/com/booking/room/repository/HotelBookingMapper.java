package com.booking.room.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.booking.room.entity.Room;
import com.booking.room.entity.RoomBooking;
import com.booking.room.entity.User;
import com.booking.room.form.SignupForm;

@Mapper
public interface HotelBookingMapper {

	public List<RoomBooking> getRoomBooking();
	
	public void createUser(SignupForm user);

	public User getAuthUser(@Param("email") String email, @Param("password") String password);

	public Room getRoomByFlag(@Param("roomId") int roomId, @Param("lendFlag") int lendFlag);

	public RoomBooking getBookingById(@Param("bookingId") int bookingId);
	
	public void createBooking(@Param("roomId") int roomId, @Param("userId") int userId);

	public void updateRoom(@Param("roomId") int roomId, @Param("lendFlag") int lendFlag);

	public void cancelBooking(@Param("bookingId") int bookingId);
	
	public int checkOutRoom(@Param("roomId") int roomId,@Param("bookingId") int bookingId,@Param("userId") int userId);
	
	public int checkEmail(@Param("email") String email);
}
