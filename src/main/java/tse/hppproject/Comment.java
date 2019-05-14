package tse.hppproject;

import java.sql.Timestamp;
import java.util.Date;

public class Comment {
	Integer id;
	Integer id_replied;
	Integer id_post;
	Integer id_user;
	Timestamp ts;
	Integer score;
	
	public Comment(String comment) {
		super();
		// TODO Auto-generated constructor stub
		String[] coupe = comment.replace("|", ",").split(",");
		coupe[0] = coupe[0].replace("T", " ").substring(0, coupe[0].indexOf(".")+1);
		this.ts = Timestamp.valueOf(coupe[0]);
		this.id = Integer.parseInt(coupe[1]);
		this.id_post = Integer.parseInt(coupe[6]);
		this.id_replied = Integer.parseInt(coupe[5]);
		this.id_user = Integer.parseInt(coupe[2]);
		this.score = 10;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId_replied() {
		return id_replied;
	}
	public void setId_replied(Integer id_replied) {
		this.id_replied = id_replied;
	}
	public Integer getId_post() {
		return id_post;
	}
	public void setId_post(Integer id_post) {
		this.id_post = id_post;
	}
	public Integer getId_user() {
		return id_user;
	}
	public void setId_user(Integer id_user) {
		this.id_user = id_user;
	}

	
	
}
