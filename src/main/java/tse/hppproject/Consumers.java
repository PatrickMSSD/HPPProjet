package tse.hppproject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Consumers implements Runnable{
    private Object syncObj =  new Object() ;
    
	BlockingQueue<String> post_queue = new ArrayBlockingQueue<String>(110);
	BlockingQueue<String> comm_queue = new ArrayBlockingQueue<String>(110);
	BlockingQueue<Object> total_queue = new ArrayBlockingQueue<Object>(110);
	Timestamp total_time;
	ArrayList<Comment> comment_list = new ArrayList<Comment>();
	ArrayList<Post> post_list = new ArrayList<Post>();
	
	public Consumers(BlockingQueue<String> post_queue, BlockingQueue<String> comm_queue,
			BlockingQueue<Object> total_queue, Timestamp total_time, ArrayList<Comment> comment_list,
			ArrayList<Post> post_list, Map<Integer, ArrayList<Comment>> iDPost2Com) {
		super();
		this.post_queue = post_queue;
		this.comm_queue = comm_queue;
		this.total_queue = total_queue;
		this.total_time = total_time;
		this.comment_list = comment_list;
		this.post_list = post_list;
		this.IDPost2Com = iDPost2Com;
	}

	Map<Integer, ArrayList<Comment>> IDPost2Com = new HashMap<Integer, ArrayList<Comment>>();
	Timestamp getTotalTime() {
		return total_time;
	}
	
	public void run() {
		// TODO Auto-generated method stub
	while(!post_queue.isEmpty() && !comm_queue.isEmpty()) {
		try {
		String[] string_post = post_queue.peek().split("[|]");
		String[] string_comment = comm_queue.peek().split("[|]");
		Timestamp post_time = Timestamp.valueOf(string_post[0].substring(0,string_post[0].indexOf(".")).replaceAll("T", " "));
		Timestamp comment_time = Timestamp.valueOf(string_comment[0].substring(0,string_comment[0].indexOf(".")).replaceAll("T", " "));
		synchronized (syncObj) {
		if (post_time.before(comment_time)) {
				Post post;
				post = new Post(post_queue.take(),total_time,IDPost2Com);
				post_list.add(post);
				total_queue.add(post);
				total_time = post.getTs();
				System.out.println("date "+total_time+" "+post.getTs());
			} else {
				Comment com = new Comment(this.comm_queue.take(),total_time);
				comment_list.add(com);
				total_queue.add(com);
				total_time = com.getTs();					
				System.out.println("date "+total_time+" "+com.getTs());
			}
		}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

		

}
