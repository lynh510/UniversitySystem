package com.system.entity;

public class Idea_attachfiles {
	private int id;
	private Idea idea;
	private String old_name;
	private String new_name;
	private String file_type;
	private String link;

	public Idea_attachfiles() {

	}

	public Idea_attachfiles(int id, Idea idea, String old_name, String new_name, String file_type, String link) {
		super();
		this.id = id;
		this.idea = idea;
		this.old_name = old_name;
		this.new_name = new_name;
		this.file_type = file_type;
		this.link = link;
	}

	public String getOld_name() {
		return old_name;
	}

	public void setOld_name(String old_name) {
		this.old_name = old_name;
	}

	public String getNew_name() {
		return new_name;
	}

	public void setNew_name(String new_name) {
		this.new_name = new_name;
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

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
