package tse.hppproject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import producers.Producer;

public class my_project {

	public static void main(String[] args) throws IOException, InterruptedException {

		Timestamp total_time = new Timestamp(0);

		BlockingQueue<String> post_queue = new ArrayBlockingQueue<String>(110);
		BlockingQueue<String> comm_queue = new ArrayBlockingQueue<String>(110);
		BlockingQueue<Object> total_queue = new ArrayBlockingQueue<Object>(110);

		Producer prod_post = new Producer(post_queue, "../HPPProjet/resourses/posts.dat");
		Producer prod_comm = new Producer(comm_queue, "../HPPProjet/resourses/comments.dat");

		prod_post.readFile(50);
		prod_comm.readFile(50);

		Comment com1 = new Comment(comm_queue.poll());
		Post post1 = new Post(post_queue.poll());

		while (!comm_queue.isEmpty() && !post_queue.isEmpty()) {

			if (post1.getTs().before(com1.getTs())) {
				total_queue.add(post1);
				total_time = post1.getTs();
				post1 = new Post(post_queue.poll());
			} else {
				total_queue.add(com1);
				total_time = com1.getTs();
				com1 = new Comment(comm_queue.poll());
			}
		}

		// HASH MAP

		Map<Integer, ArrayList<Comment>> IDPost2Com = new HashMap<Integer, ArrayList<Comment>>();
		Map<Integer, Post> ID2Post = new HashMap<Integer, Post>();
		Map<Integer, Integer> IDCom2IDPost = new HashMap<Integer, Integer>();
		while (!total_queue.isEmpty()) {
			if (total_queue.peek().getClass().equals(Post.class)) {
				Post patchPost = (Post) total_queue.take();
				IDPost2Com.put(patchPost.post_id, new ArrayList<Comment>());
				ID2Post.put(patchPost.post_id, patchPost);
			} else {
				Comment patchComment = (Comment) total_queue.take();
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

		for (Integer post_id : ID2Post.keySet()) {

			ID2Post.get(post_id).change_score(total_time);
			for (int i = 0; i < IDPost2Com.get(post_id).size(); i++) {
				IDPost2Com.get(post_id).get(i).change_score(total_time);
			}
			ID2Post.get(post_id).change_total_score(IDPost2Com.get(post_id));

		}
		
		// Comparator pour sort la liste de post et avoir les trois premiers dans les trois premieres cases de result
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

		System.out.println(result.toString());

	}
}
