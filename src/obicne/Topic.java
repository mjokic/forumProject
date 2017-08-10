package obicne;

import java.util.Date;

public class Topic {

	private int id;
	private String name;
	private String description;
	private String content;
	private User owner;
	private Date date;
	private boolean important;
	private boolean locked;
	private int forumId;
	
	
	public Topic(String name, String description, String content, User owner, 
			boolean important, boolean locked, int forumId) {
		this.name = name;
		this.description = description;
		this.content = content;
		this.owner = owner;
		this.date = new Date();
		this.important = important;
		this.locked = locked;
		this.forumId = forumId;
	}
	
	
	public Topic(int id, String name, String description, String content, User owner, Date date, 
			boolean important, boolean locked, int forumId) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.content = content;
		this.owner = owner;
		this.date = date;
		this.important = important;
		this.locked = locked;
		this.forumId = forumId;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public User getOwner() {
		return owner;
	}


	public void setOwner(User owner) {
		this.owner = owner;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public boolean isImportant() {
		return important;
	}


	public void setImportant(boolean important) {
		this.important = important;
	}

	
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}



	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}


}
