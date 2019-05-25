package tse.hppproject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Post {
	
	private String ts;
	private String actual_time;
	private long post_id;
	private long user_id;
	private String user;
	private int score =10;
	private int score_total =0;
	Comment lastCom;
	
	//utilisation de la table de correspondance post list de commentaire pour compter le score
	Map<Long, ArrayList<Comment>> IDPost2Com = new HashMap<Long, ArrayList<Comment>>(1000000);
	
	public Post(String str,String actual_time,Map<Long, ArrayList<Comment>> IDPost2Com) {
		super();
		this.actual_time=actual_time;
		String[] string = str.split("[|]");
		this.ts=string[0].substring(0,string[0].indexOf(".")).replaceAll("T", " ");
		this.post_id=Long.parseLong(string[1]);
		this.user_id=Long.parseLong(string[2]);
		this.user=string[4];
		this.IDPost2Com=IDPost2Com;
		
	}
	
	//calcul le score total d'un post (post + ses commentaires)
	public void change_total_score() {
		this.score_total=this.score;
		if(this.IDPost2Com.get(post_id)!= null) {
		for(int i=0;i<this.IDPost2Com.get(post_id).size();i++) {
			this.score_total+=this.IDPost2Com.get(post_id).get(i).getScore();
		}
		}
	}
	
	//calcul le score du post (post seulement)
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

	public String getTs() {
		return ts;
	}


	public Comment getLastCom() {
		return lastCom;
	}

	public void setLastCom(Comment lastCom) {
		this.lastCom = lastCom;
	}

	public void setTs(String ts) {
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


