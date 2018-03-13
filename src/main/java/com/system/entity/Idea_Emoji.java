package com.system.entity;

public class Idea_Emoji {
	private int id;
	private Emoji emoji;
	private Idea idea;
	private Person person;
	
	public Idea_Emoji() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Emoji getEmoji() {
		return emoji;
	}

	public void setEmoji(Emoji emoji) {
		this.emoji = emoji;
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

	public Idea_Emoji(int id, Emoji emoji, Idea idea, Person person) {
		super();
		this.id = id;
		this.emoji = emoji;
		this.idea = idea;
		this.person = person;
	}
	
}
