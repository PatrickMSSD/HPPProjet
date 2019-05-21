package tse.hppproject;

import java.sql.Timestamp;

public class Comment{
	
	private Long id_comment;
	private Long id_replied;
	private Long id_post;
	private Long id_user;
	private Timestamp ts;
	private Timestamp actual_time;
	private Integer score;
	
	public Comment(String comment,Timestamp actual_time) {
		super();
		// TODO Auto-generated constructor stub
		String[] coupe = comment.split("[|]");
		this.ts = Timestamp.valueOf(coupe[0].replace("T", " ").substring(0, coupe[0].indexOf(".")));
		this.id_comment = Long.parseLong(coupe[1]);
		this.id_post = (coupe.length == 7 ) ? Long.parseLong(coupe[6]) : 0;
		this.id_replied=coupe[5].equals("")?0:Long.parseLong(coupe[5]);
		this.id_user = Long.parseLong(coupe[2]);
		this.score = 10;
		this.actual_time=actual_time;
	}
	
	//change le score d'un commentaire
	public void change_score() {
		this.score = 10 + this.actual_time.compareTo(this.ts);
	}	
	
	@Override
	public String toString() {
		return "Comment [id=" + id_comment + ", id_replied=" + id_replied + ", id_post=" + id_post + ", id_user=" + id_user
				+ ", ts=" + ts + ", score=" + score + "]";
	}
	
	
	public Long getId_comment() {
		return id_comment;
	}

	public void setId_comment(Long id_comment) {
		this.id_comment = id_comment;
	}

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	public Long getId() {
		return id_comment;
	}
	public void setId(Long id) {
		this.id_comment = id;
	}
	public Long getId_replied() {
		return id_replied;
	}
	public void setId_replied(Long id_replied) {
		this.id_replied = id_replied;
	}
	public Long getId_post() {
		return id_post;
	}
	public void setId_post(Long id_post) {
		this.id_post = id_post;
	}
	public Long getId_user() {
		return id_user;
	}
	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}
	
}
