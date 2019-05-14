package tse.hppproject;

import java.sql.Timestamp;
import java.util.Date;

public class Comment {
	@Override
	public String toString() {
		return "Comment [id=" + id_comment + ", id_replied=" + id_replied + ", id_post=" + id_post + ", id_user=" + id_user
				+ ", ts=" + ts + ", score=" + score + "]";
	}
	Integer id_comment;
	Integer id_replied;
	Integer id_post;
	Integer id_user;
	Timestamp ts;
	Integer score;
	
	public Comment(String comment) {
		super();
		// TODO Auto-generated constructor stub
		String[] coupe = comment.replace("|", "zx").split("zx");
		this.ts = Timestamp.valueOf(coupe[0].replace("T", " ").substring(0, coupe[0].indexOf(".")));
		this.id_comment = Integer.parseInt(coupe[1]);
		this.id_post = (coupe.length == 7 ) ? Integer.parseInt(coupe[6]) : 0;
		this.id_replied=coupe[5].equals("")?0:Integer.parseInt(coupe[5]);
		this.id_user = Integer.parseInt(coupe[2]);
		this.score = 10;
	}
	
	public Integer getId_comment() {
		return id_comment;
	}

	public void setId_comment(Integer id_comment) {
		this.id_comment = id_comment;
	}

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	public Integer getId() {
		return id_comment;
	}
	public void setId(Integer id) {
		this.id_comment = id;
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
