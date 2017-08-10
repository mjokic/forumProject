package obicne;

import java.util.Date;

public class Forum {

	private int id;
	private String name;
	private String description;
	private User owner; // moze biti samo administrator
	private Date creationDate;
	private boolean locked;
	private ForumType type; // tip foruma, mozda neka klasa?!
	private int parentForumId;
	
	
	
	public Forum(String name, String description, User owner,
			ForumType type, int parentForumId) {
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.creationDate =  new Date();
		this.locked = false;
		this.type = type;
		this.parentForumId = parentForumId;
	}
	
	public Forum(int id, String name, String description, User owner, Date creationDate,
			boolean locked, ForumType type, int parentForumId) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.creationDate = creationDate;
		this.locked = locked;
		this.type = type;
		this.parentForumId = parentForumId;
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


	public User getOwner() {
		return owner;
	}


	public void setOwner(User owner) {
		this.owner = owner;
	}
	

	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public boolean isLocked() {
		return locked;
	}


	public void setLocked(boolean locked) {
		this.locked = locked;
	}


	public ForumType getType() {
		return type;
	}


	public void setType(ForumType type) {
		this.type = type;
	}


	public int getParentForumId() {
		return parentForumId;
	}


	public void setParentForumId(int parentForumId) {
		this.parentForumId = parentForumId;
	}



	@Override
	public String toString() {
		return "Forum [id=" + id + ", name=" + name + ", description=" + description + ", owner=" + owner
				+ ", creationDate=" + creationDate + ", locked=" + locked + ", type=" + type + ", parentForumId="
				+ parentForumId + "]";
	}
	
}
