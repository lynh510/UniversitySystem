package com.system.entity;

import java.util.Date;

public class Notification {
	private int id;
	private int is_read;
	private Person sender;
	private Person recipient;
	private Activity activity;
	private String description;
	private String url;
	private Date time_sent;

	public Notification() {
		super();
	}

	public Notification(int id, int is_read, Person sender, Person recipient, Activity activity, String description,
			String url, Date time_sent) {
		super();
		this.id = id;
		this.is_read = is_read;
		this.sender = sender;
		this.recipient = recipient;
		this.activity = activity;
		this.description = description;
		this.url = url;
		this.time_sent = time_sent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIs_read() {
		return is_read;
	}

	public void setIs_read(int is_read) {
		this.is_read = is_read;
	}

	public Person getSender() {
		return sender;
	}

	public void setSender(Person sender) {
		this.sender = sender;
	}

	public Person getRecipient() {
		return recipient;
	}

	public void setRecipient(Person recipient) {
		this.recipient = recipient;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getTime_sent() {
		return time_sent;
	}

	public void setTime_sent(Date time_sent) {
		this.time_sent = time_sent;
	}

}
