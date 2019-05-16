package tse.hppproject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Post implements Runnable{
	
	Timestamp ts;
	Timestamp actual_time;
	Integer post_id;
	Integer user_id;
	String user;
	Integer score =10;
	Integer score_total =0;
	Comment lastCom;
	Map<Integer, ArrayList<Comment>> IDPost2Com = new HashMap<Integer, ArrayList<Comment>>();
	
	public Post(String str,Timestamp actual_time,Map<Integer, ArrayList<Comment>> IDPost2Com) {
		super();
		this.actual_time=actual_time;
		String[] string = str.split("[|]");
		this.ts=Timestamp.valueOf(string[0].substring(0,string[0].indexOf(".")).replaceAll("T", " "));
		this.post_id=Integer.parseInt(string[1]);
		this.user_id=Integer.parseInt(string[2]);
		this.user=string[4];
		this.IDPost2Com=IDPost2Com;
		
	}
	
	public void change_total_score() {
		this.score_total=this.score;
		for(int i=0;i<this.IDPost2Com.size();i++) {
			this.score_total+=this.IDPost2Com.get(post_id).get(i).score;
		}
	}
	
	public Comment getLastCom() {
		return lastCom;
	}

	public void setLastCom(Comment lastCom) {
		this.lastCom = lastCom;
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

	public void run() {
		// TODO Auto-generated method stub
		try {
			this.change_score();
			this.change_total_score();
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	

}


