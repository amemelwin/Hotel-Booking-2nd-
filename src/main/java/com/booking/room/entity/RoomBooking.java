package com.booking.room.entity;

import lombok.Data;

@Data
public class RoomBooking {
	private int id;
	private String roomNumber;
	private int lendFlag;
	private int userId;
	private int bookingId;

}
