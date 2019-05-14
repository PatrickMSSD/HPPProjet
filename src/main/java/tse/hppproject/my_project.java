package tse.hppproject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import producers.Producer;

public class my_project {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		Timestamp total_time = new Timestamp(0);
		
		BlockingQueue<String> post_queue = new ArrayBlockingQueue<String>(110);
		BlockingQueue<String> comm_queue = new ArrayBlockingQueue<String>(110);
		BlockingQueue<Object> total_queue = new ArrayBlockingQueue<Object>(110);
		
		Producer prod_post = new Producer(post_queue,"C:\\Users\\kenza\\git\\HPPProjet\\resourses\\posts.dat");
		Producer prod_comm = new Producer(comm_queue,"C:\\Users\\kenza\\git\\HPPProjet\\resourses\\comments.dat");
		
		
		prod_post.readFile(50);
		prod_comm.readFile(50);
		
		Comment com1 = new Comment(comm_queue.poll());
		Post post1 = new Post(post_queue.poll());
		
		while(!comm_queue.isEmpty()&& !post_queue.isEmpty()) {
			
			if(post1.getTs().before(com1.getTs())) {
				total_queue.add(post1);
				total_time=post1.getTs();
				System.out.println("c'est un post");
				post1 = new Post(post_queue.poll());
			}else {
				total_queue.add(com1);
				total_time=com1.getTs();
				com1 = new Comment(comm_queue.poll());
				System.out.println("c'est un comm");
			}
		}
		
		System.out.println(total_queue.toString());
		System.out.println(total_time.toString());
		
		
		
		
	}


}
