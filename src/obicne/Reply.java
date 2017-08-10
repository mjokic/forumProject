package obicne;

import java.util.Date;

public class Reply {

	private int id;
	private String content;
	private User owner;
	private Date date;
	private int topicId;
	
	
	// using when creating new reply
	public Reply(String sadrzaj, User owner, int topicId) {
		this.content = sadrzaj;
		this.owner = owner;
		this.date = new Date();
		this.topicId = topicId;
	}
	

	// using when already having reply in database
	public Reply(int id, String sadrzaj, User owner, Date date, int topicId) {
		this.id = id;
		this.content = sadrzaj;
		this.owner = owner;
		this.date = date;
		this.topicId = topicId;
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


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getTopicId() {
		return topicId;
	}


	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	
	
}
