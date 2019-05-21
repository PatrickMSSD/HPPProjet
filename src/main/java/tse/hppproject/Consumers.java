package tse.hppproject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Consumers implements Runnable {
	private Object syncObj = new Object();

	private BlockingQueue<String> post_queue = new ArrayBlockingQueue<String>(110);
	private BlockingQueue<String> comm_queue = new ArrayBlockingQueue<String>(110);
	private BlockingQueue<Object> total_queue = new ArrayBlockingQueue<Object>(110);
	
	private Timestamp total_time;
	
	private Boolean producer_end;
	private Boolean consumers_end;
	
	private Map<Long, ArrayList<Comment>> IDPost2Com = new HashMap<Long, ArrayList<Comment>>();

	public Consumers(BlockingQueue<String> post_queue, BlockingQueue<String> comm_queue,
			BlockingQueue<Object> total_queue, Timestamp total_time, Boolean producer_end, Boolean consumers_end,
			Map<Long, ArrayList<Comment>> iDPost2Com) {
		super();
		this.post_queue = post_queue;
		this.comm_queue = comm_queue;
		this.total_queue = total_queue;
		this.total_time = total_time;
		this.producer_end = producer_end;
		this.consumers_end = consumers_end;
		IDPost2Com = iDPost2Com;
	}

	Timestamp getTotalTime() {
		return total_time;
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (post_queue.peek() != "*") {
			if (total_queue.remainingCapacity() != 0) {
				try {
					if(!post_queue.isEmpty() && comm_queue.isEmpty()) {
						Post post;
						post = new Post(post_queue.take(), total_time, IDPost2Com);
						total_queue.put(post);
						total_time = post.getTs();
						System.out.println("date du post " + total_time + " " + post.getTs());
						continue;
					}
					if(post_queue.isEmpty() && !comm_queue.isEmpty()) {
						Comment com = new Comment(this.comm_queue.take(), total_time);
						total_queue.put(com);
						total_time = com.getTs();
						System.out.println("date du comm " + total_time + " " + com.getTs());
						continue;
					}
					String[] string_post = post_queue.peek().split("[|]");
					String[] string_comment = comm_queue.peek().split("[|]");
					Timestamp post_time = Timestamp
							.valueOf(string_post[0].substring(0, string_post[0].indexOf(".")).replaceAll("T", " "));
					Timestamp comment_time = Timestamp.valueOf(
							string_comment[0].substring(0, string_comment[0].indexOf(".")).replaceAll("T", " "));
					synchronized (syncObj) {
					if (post_time.before(comment_time)) {
						Post post;
						post = new Post(post_queue.take(), total_time, IDPost2Com);
						total_queue.put(post);
						total_time = post.getTs();
						//System.out.println("conumer post : "+post.toString());
						System.out.println("date " + total_time + " " + post.getTs());
					} else {
						Comment com = new Comment(this.comm_queue.take(), total_time);
						total_queue.put(com);
						total_time = com.getTs();
						//System.out.println("consumer comment : "+ com.toString());
						System.out.println("date " + total_time + " " + com.getTs());
					}
					 }
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println("la taille de post_queue est : "+ post_queue.size());
			//System.out.println("la taille de comm_queue est : "+ comm_queue.size());
		
		}
		//consumers_end=true;
		System.out.println("la production est finie");
		try {
			total_queue.put("*");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
