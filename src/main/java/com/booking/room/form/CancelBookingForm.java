package com.booking.room.form;

import lombok.Data;

@Data
public class CancelBookingForm {
	private String cancelRoomId;
	private String bookingId;
}
