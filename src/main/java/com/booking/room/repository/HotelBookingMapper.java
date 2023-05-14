package com.booking.room.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.booking.room.entity.Room;
import com.booking.room.entity.RoomBooking;
import com.booking.room.entity.User;

@Mapper
public interface HotelBookingMapper {

	public List<RoomBooking> getRoomBooking();

	public User getAuthUser(@Param("email") String email, @Param("password") String password);

	public Room getRoomByFlag(@Param("roomId") int roomId, @Param("lendFlag") int lendFlag);

	public void createBooking(@Param("roomId") int roomId, @Param("userId") int userId);

	public void updateRoom(@Param("roomId") int roomId, @Param("lendFlag") int lendFlag);

	public void cancelBooking(@Param("bookingId") int bookingId);

}
