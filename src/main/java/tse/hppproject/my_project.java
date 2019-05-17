package tse.hppproject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import javax.management.MXBean;

import producers.Producer;

public class my_project {

	public static void main(String[] args) throws IOException, InterruptedException {

		Timestamp total_time = new Timestamp(0);

		BlockingQueue<String> post_queue = new ArrayBlockingQueue<String>(110);
		BlockingQueue<String> comm_queue = new ArrayBlockingQueue<String>(110);
		BlockingQueue<Object> total_queue = new ArrayBlockingQueue<Object>(110);
		Boolean producer_end=false;
		Boolean consumers_end=false;
		Producer prod = new Producer(post_queue,comm_queue,"../HPPProjet/resourses/posts.dat","../HPPProjet/resourses/comments.dat");
		//Producer comm = new Producer(comm_queue,"../HPPProjet/resourses/comments.dat");
		
		ArrayList<Comment> comment_list = new ArrayList<Comment>();
		ArrayList<Post> post_list = new ArrayList<Post>();
		
		Map<Long, ArrayList<Comment>> IDPost2Com = new HashMap<Long, ArrayList<Comment>>();
		Map<Long, Post> ID2Post = new HashMap<Long, Post>();
		Map<Long, Long> IDCom2IDPost = new HashMap<Long, Long>();
		
		List<Post> result = new ArrayList<Post>(ID2Post.values());
		
		Thread prodpost = new Thread(prod);
		prodpost.start();
		//Thread prodcomm = new Thread(comm);
		//prodcomm.start();
		
		Consumers my_consumers = new Consumers(post_queue, comm_queue, total_queue, total_time, comment_list, post_list,producer_end,consumers_end, IDPost2Com);
		Thread consumers_thread= new Thread(my_consumers);
		ConsumerQueue2Sort my_consumersQueue = new ConsumerQueue2Sort(IDPost2Com,ID2Post,IDCom2IDPost,total_queue,result,consumers_end);
		Thread consumersQueue_thread= new Thread(my_consumersQueue);
		consumers_thread.start();
		consumersQueue_thread.start();
		
		consumers_thread.join();
		consumersQueue_thread.join();
		
		System.out.println(total_queue.toString());
		System.out.println(result.toString());
		

	
	}
}
