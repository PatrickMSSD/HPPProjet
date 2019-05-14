package tse.hppproject;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import producers.Producer;

public class my_project {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		BlockingQueue<String> post_queue = new ArrayBlockingQueue<String>(110);
		BlockingQueue<String> comm_queue = new ArrayBlockingQueue<String>(110);
		
		//Producer prod_post = new Producer(post_queue,"C:\\Users\\kenza\\git\\HPPProjet\\resourses\\posts.dat");
		Producer prod_comm = new Producer(comm_queue,"C:\\Users\\kenza\\git\\HPPProjet\\resourses\\comments.dat");
		
		//prod_post.readFile(1);
		prod_comm.readFile(2);
		
		Comment com1 = new Comment(comm_queue.poll());
		System.out.println(com1.toString());
		
	}


}
