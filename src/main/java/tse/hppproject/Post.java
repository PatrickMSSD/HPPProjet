package tse.hppproject;

import java.sql.Timestamp;

public class Post {
	
	Timestamp ts;
	Integer post_id;
	Integer user_id;
	String user;
	
	
	public Post(String str) {
		super();
		String[] string = str.replace("|", ",").split(",");
		this.ts=Timestamp.valueOf(string[0].substring(0,string[0].indexOf(".")).replaceAll("T", " "));
		this.post_id=Integer.parseInt(string[1]);
		this.user_id=Integer.parseInt(string[2]);
		this.user=string[4];
		
	}


	@Override
	public String toString() {
		return "Post [ts=" + ts + ", post_id=" + post_id + ", user_id=" + user_id + ", user=" + user + "]";
	}


	public Timestamp getTs() {
		return ts;
	}


	public void setTs(Timestamp ts) {
		this.ts = ts;
	}


	public Integer getPost_id() {
		return post_id;
	}


	public void setPost_id(Integer post_id) {
		this.post_id = post_id;
	}


	public Integer getUser_id() {
		return user_id;
	}


	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}
		
	

}


