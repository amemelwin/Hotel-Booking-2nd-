package com.booking.room.form;

import lombok.Data;

@Data
public class CancelBookingForm {
	private int cancelRoomId;
	private int bookingId;
}
