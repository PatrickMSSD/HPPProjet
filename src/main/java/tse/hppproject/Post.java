package tse.hppproject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Post {
	
	Timestamp ts;
	Timestamp actual_time;
	Long post_id;
	Long user_id;
	String user;
	Integer score =10;
	Integer score_total =0;
	Comment lastCom;
	Map<Long, ArrayList<Comment>> IDPost2Com = new HashMap<Long, ArrayList<Comment>>();
	
	public Post(String str,Timestamp actual_time,Map<Long, ArrayList<Comment>> IDPost2Com) {
		super();
		this.actual_time=actual_time;
		String[] string = str.split("[|]");
		this.ts=Timestamp.valueOf(string[0].substring(0,string[0].indexOf(".")).replaceAll("T", " "));
		this.post_id=Long.parseLong(string[1]);
		this.user_id=Long.parseLong(string[2]);
		this.user=string[4];
		this.IDPost2Com=IDPost2Com;
		
	}
	
	public void change_total_score() {
		this.score_total=this.score;
		for(int i=0;i<this.IDPost2Com.size();i++) {
			this.score_total+=this.IDPost2Com.get(post_id).get(i).score;
		}
	}
	
	public void change_score() {
		this.score=this.actual_time.compareTo(this.ts)<-10?0:10+this.actual_time.compareTo(this.ts);
	}
	
	public Integer getScore_total() {
		return score_total;
	}


	public void setScore_total(Integer score_total) {
		this.score_total = score_total;
	}
	
	
	public Integer getScore() {
		return score;
	}


	public void setScore(Integer score) {
		this.score = score;
	}






	@Override
	public String toString() {
		return "Post [ts=" + ts + ", post_id=" + post_id + ", user_id=" + user_id + ", user=" + user + ", score="
				+ score + ", score_total=" + score_total + "]";
	}

	public Timestamp getTs() {
		return ts;
	}


	public Comment getLastCom() {
		return lastCom;
	}

	public void setLastCom(Comment lastCom) {
		this.lastCom = lastCom;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}


	public Long getPost_id() {
		return post_id;
	}


	public void setPost_id(Long post_id) {
		this.post_id = post_id;
	}


	public Long getUser_id() {
		return user_id;
	}


	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}
}


