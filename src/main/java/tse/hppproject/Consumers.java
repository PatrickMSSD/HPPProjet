package tse.hppproject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Consumers implements Runnable {
	private Object syncObj = new Object();

	private BlockingQueue<String> post_queue = new ArrayBlockingQueue<String>(1000000);
	private BlockingQueue<String> comm_queue = new ArrayBlockingQueue<String>(1000000);
	private BlockingQueue<Object> total_queue = new ArrayBlockingQueue<Object>(1000000);

	private long total_time;


	private Map<Long, ArrayList<Comment>> IDPost2Com = new HashMap<Long, ArrayList<Comment>>(1000000);

	public Consumers(BlockingQueue<String> post_queue, BlockingQueue<String> comm_queue,
			BlockingQueue<Object> total_queue, long total_time,
			Map<Long, ArrayList<Comment>> iDPost2Com) {
		super();
		this.post_queue = post_queue;
		this.comm_queue = comm_queue;
		this.total_queue = total_queue;
		this.total_time = total_time;
		IDPost2Com = iDPost2Com;
	}

	long getTotalTime() {
		return total_time;
	}

	public void treatment() {		
		while (post_queue.peek() != "*" && !comm_queue.isEmpty()) {
			
			if (total_queue.remainingCapacity() != 0) {
				try {
					
					if (!post_queue.isEmpty() && comm_queue.isEmpty()) {
						
						Post post = new Post(post_queue.take(), total_time, IDPost2Com);
						total_queue.put(post);
						total_time = post.getTs();
						//System.out.println("etape 2 " + post.getTs());
						continue;
						
					}
					if (post_queue.isEmpty() && !comm_queue.isEmpty()) {
						Comment com = new Comment(this.comm_queue.take(), total_time);
						total_queue.put(com);
						total_time = com.getTs();
						//System.out.println("etape 2 " + com.getTs());
						continue;
					}
					if (!post_queue.isEmpty() && !comm_queue.isEmpty()) {
						String[] string_post = post_queue.peek().split("[|]");
						String[] string_comment = comm_queue.peek().split("[|]");
						String post_time = string_post[0].substring(0, string_post[0].indexOf(".")).replaceAll("T",
								" ");
						String comment_time = string_comment[0].substring(0, string_comment[0].indexOf("."))
								.replaceAll("T", " ");
						if (post_time.compareTo(comment_time) < 0) {
							Post post = new Post(post_queue.take(), total_time, IDPost2Com);
							total_queue.put(post);
							total_time = post.getTs();
							//System.out.println("etape 2 " + post.getTs());

						} else {
							Comment com = new Comment(this.comm_queue.take(), total_time);
							total_queue.put(com);
							total_time = com.getTs();
							//System.out.println("etape 2 " + com.getTs());
						}
						
						continue;
					}
					
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
		}

		System.out.println("la conso est finie");
		try {
			total_queue.put("*");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(10);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		treatment();

	}

}
