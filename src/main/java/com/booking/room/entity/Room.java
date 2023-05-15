 package com.booking.room.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Room {
	private int id;
	private String roomNumber;
	private int lendFlag;
	private Date createdAt;
	private Date updatedAt;
}
