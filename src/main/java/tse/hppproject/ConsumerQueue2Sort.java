package tse.hppproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class ConsumerQueue2Sort implements Runnable {

	Map<Integer, ArrayList<Comment>> IDPost2Com = new HashMap<Integer, ArrayList<Comment>>();
	Map<Integer, Post> ID2Post = new HashMap<Integer, Post>();
	Map<Integer, Integer> IDCom2IDPost = new HashMap<Integer, Integer>();
	BlockingQueue<Object> total_queue;
	List<Post> result;

	public ConsumerQueue2Sort(BlockingQueue<Object> total_queue, List<Post> result) {
		super();
		this.total_queue = total_queue;
		this.result = result;
	}

	public ConsumerQueue2Sort(Map<Integer, ArrayList<Comment>> iDPost2Com, Map<Integer, Post> iD2Post,
			Map<Integer, Integer> iDCom2IDPost, BlockingQueue<Object> total_queue, List<Post> result) {
		super();
		IDPost2Com = iDPost2Com;
		ID2Post = iD2Post;
		IDCom2IDPost = iDCom2IDPost;
		this.total_queue = total_queue;
		this.result = result;
	}

	public Map<Integer, ArrayList<Comment>> getIDPost2Com() {
		return IDPost2Com;
	}

	public void setIDPost2Com(Map<Integer, ArrayList<Comment>> iDPost2Com) {
		IDPost2Com = iDPost2Com;
	}

	public Map<Integer, Post> getID2Post() {
		return ID2Post;
	}

	public void setID2Post(Map<Integer, Post> iD2Post) {
		ID2Post = iD2Post;
	}

	public Map<Integer, Integer> getIDCom2IDPost() {
		return IDCom2IDPost;
	}

	public void setIDCom2IDPost(Map<Integer, Integer> iDCom2IDPost) {
		IDCom2IDPost = iDCom2IDPost;
	}

	public BlockingQueue<Object> getTotal_queue() {
		return total_queue;
	}

	public void setTotal_queue(BlockingQueue<Object> total_queue) {
		this.total_queue = total_queue;
	}

	public List<Post> getResult() {
		return result;
	}

	public void setResult(List<Post> result) {
		this.result = result;
	}

	public void run() {
		while (!total_queue.isEmpty()) {
			if (total_queue.peek().getClass().equals(Post.class)) {
				Post patchPost = null;
				try {
					patchPost = (Post) total_queue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				IDPost2Com.put(patchPost.post_id, new ArrayList<Comment>());
				ID2Post.put(patchPost.post_id, patchPost);
			} else {
				Comment patchComment = null;
				try {
					patchComment = (Comment) total_queue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (patchComment.getId_post() > 0) {
					IDPost2Com.get(patchComment.getId_post()).add(patchComment);
					IDCom2IDPost.put(patchComment.id_comment, patchComment.id_post);
					ID2Post.get(patchComment.id_post).setLastCom(patchComment);
				} else {

					IDPost2Com.get(IDCom2IDPost.get(patchComment.id_replied)).add(patchComment);
					IDCom2IDPost.put(patchComment.id_comment, IDCom2IDPost.get(patchComment.id_replied));
					ID2Post.get(IDCom2IDPost.get(patchComment.id_replied)).setLastCom(patchComment);
				}
			}
		}

		List<Post> result = new ArrayList<Post>(ID2Post.values());
		Collections.sort(result, new Comparator<Post>() {
			public int compare(Post p1, Post p2) {
				if (p2.getScore_total() != p1.getScore_total()) {
					return p2.getScore_total().compareTo(p1.getScore_total());
				}
				if (p2.getTs() != p1.getTs()) {
					return p1.getTs().compareTo(p2.getTs());
				}
				return p1.getLastCom().getTs().compareTo(p2.getLastCom().getTs());
			}
		});

	}

}
