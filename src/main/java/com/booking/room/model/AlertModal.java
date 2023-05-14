package com.booking.room.model;

import lombok.Data;

@Data
public class AlertModal {
	private String title;
	private String body;

	public AlertModal(String title, String body) {
		this.title = title;
		this.body = body;
	}
}
