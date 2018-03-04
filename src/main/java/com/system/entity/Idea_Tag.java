package com.system.entity;

public class Idea_Tag {
	private int id;
	private Idea idea;
	private Tag tag;

	public Idea_Tag() {
		super();
	}

	public Idea_Tag(int id, Idea idea, Tag tag) {
		super();
		this.id = id;
		this.idea = idea;
		this.tag = tag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

}
