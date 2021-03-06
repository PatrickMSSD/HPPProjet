package tse.hppproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConsumerQueue2Sort implements Runnable {

	Map<Long, ArrayList<Comment>> IDPost2Com = new HashMap<Long, ArrayList<Comment>>(1000000);
	Map<Long, Post> ID2Post = new HashMap<Long, Post>(1000000);
	Map<Long, Long> IDCom2IDPost = new HashMap<Long, Long>(1000000);
	BlockingQueue<Object> total_queue = new ArrayBlockingQueue<Object>(1000000);
	List<Post> result;

	public ConsumerQueue2Sort(Map<Long, ArrayList<Comment>> iDPost2Com, Map<Long, Post> iD2Post,
			Map<Long, Long> iDCom2IDPost, BlockingQueue<Object> total_queue, List<Post> result) {
		super();
		IDPost2Com = iDPost2Com;
		ID2Post = iD2Post;
		IDCom2IDPost = iDCom2IDPost;
		this.total_queue = total_queue;
		this.result = result;
	}

	public Map<Long, ArrayList<Comment>> getIDPost2Com() {
		return IDPost2Com;
	}

	public void setIDPost2Com(Map<Long, ArrayList<Comment>> iDPost2Com) {
		IDPost2Com = iDPost2Com;
	}

	public Map<Long, Post> getID2Post() {
		return ID2Post;
	}

	public void setID2Post(Map<Long, Post> iD2Post) {
		ID2Post = iD2Post;
	}

	public Map<Long, Long> getIDCom2IDPost() {
		return IDCom2IDPost;
	}

	public void setIDCom2IDPost(Map<Long, Long> iDCom2IDPost) {
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

		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		treatment();
	}

	public void treatment() {
		while (total_queue.peek() != "*") {
			if (!total_queue.isEmpty()) {
				if (total_queue.peek().getClass().equals(Post.class)) {
					try {
						Post patchPost = (Post) total_queue.take();
						System.out.println("ETAPE 3" + patchPost.getTs());
						IDPost2Com.put(patchPost.getPost_id(), null);
						ID2Post.put(patchPost.getPost_id(), patchPost);
						result.add(patchPost);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				} else {
					try {
						Comment patchComment = (Comment) total_queue.take();
						// System.out.println("ETAPE 3 " + patchComment.getTs());
						if (patchComment.getId_post() > 0) {
							IDCom2IDPost.put(patchComment.getId_comment(), patchComment.getId_post());
							IDPost2Com.replace(patchComment.getId_post(), new ArrayList<Comment>());

							if (ID2Post.get(patchComment.getId_post()) != null) {
								IDPost2Com.get(patchComment.getId_post()).add(patchComment);
								ID2Post.get(patchComment.getId_post()).setLastCom(patchComment);
							} else {
								patchComment = null;
							}
						} else {

							if (ID2Post.get(patchComment.getId_post()) != null) {
								IDPost2Com.get(IDCom2IDPost.get(patchComment.getId_replied())).add(patchComment);
								IDCom2IDPost.put(patchComment.getId_comment(),
										IDCom2IDPost.get(patchComment.getId_replied()));
								ID2Post.get(IDCom2IDPost.get(patchComment.getId_replied())).setLastCom(patchComment);
							} else {
								patchComment = null;
							}
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

				calcul_score();

			}

			for (int i = 0; i < result.size() - 1; i++) {

				result.get(i).compareTo(result.get(i + 1));

			}

		}
		System.out.println("le resultat final est" + result.toString());
	}

	void calcul_score() {

		for (Long id_post : this.ID2Post.keySet()) {
			this.ID2Post.get(id_post).change_total_score();
			if (this.ID2Post.get(id_post).getScore_total() <= 0) {
				this.ID2Post.remove(id_post);
				if (this.IDPost2Com.get(id_post) != null) {
					for (Comment com : this.IDPost2Com.get(id_post)) {
						this.IDCom2IDPost.remove(com.getId_comment());
					}
				}
				this.IDPost2Com.remove(id_post);
			}

		}

		if (result.size() - 1 > 0) {
			while (result.get(result.size() - 1).getScore_total() == 0) {
				result.remove(result.size() - 1);
			}
		}

	}

}
