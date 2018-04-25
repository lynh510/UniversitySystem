package com.system.entity;

import java.util.Date;

public class Comment {
	private int comment_id;
	private Idea idea;
	private Person person;
	private Date comment_time;
	private String comment_text;
	private int mode;

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public Comment() {
		super();
	}

	public Comment(Idea idea, Person person, String comment_text) {
		super();
		this.idea = idea;
		this.person = person;
		this.comment_text = comment_text;
	}

	public Comment(int comment_id, Idea idea, Person person, Date comment_time, String comment_text) {
		super();
		this.comment_id = comment_id;
		this.idea = idea;
		this.person = person;
		this.comment_time = comment_time;
		this.comment_text = comment_text;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Date getComment_time() {
		return comment_time;
	}

	public void setComment_time(Date comment_time) {
		this.comment_time = comment_time;
	}

	public String getComment_text() {
		return comment_text;
	}

	public void setComment_text(String comment_text) {
		this.comment_text = comment_text;
	}

}
