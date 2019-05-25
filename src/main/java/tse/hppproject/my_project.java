package tse.hppproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import producers.Producer;

public class my_project {

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println(new java.util.Date().getTime());

		//temps actuel correspondant au dernier post ou commentaire traité
		String total_time = "";

		//Lien entre Produceur et Consumers
		BlockingQueue<String> post_queue = new ArrayBlockingQueue<String>(1000000);
		BlockingQueue<String> comm_queue = new ArrayBlockingQueue<String>(1000000);
		
		//Lien entre Consumers et ConsumerQueue2Sort
		BlockingQueue<Object> total_queue = new ArrayBlockingQueue<Object>(1000000);
		
		//Indique si la production est terminée
		Boolean producer_end=false;
		//Indique si la consomation est terminée
		Boolean consumers_end=false;
		
		//Tables de correspondances 
		Map<Long, ArrayList<Comment>> IDPost2Com = new HashMap<Long, ArrayList<Comment>>();
		Map<Long, Post> ID2Post = new HashMap<Long, Post>();
		Map<Long, Long> IDCom2IDPost = new HashMap<Long, Long>();
		
		
		List<Post> result = new ArrayList<Post>(ID2Post.values());
		
		Producer prod = new Producer(post_queue,comm_queue,"../HPPProjet/resourses/posts.dat","../HPPProjet/resourses/comments.dat");
		Thread prodpost = new Thread(prod);
		prodpost.start();
		
		Consumers my_consumers = new Consumers(post_queue, comm_queue, total_queue, total_time,producer_end,consumers_end, IDPost2Com);
		Thread consumers_thread= new Thread(my_consumers);
		
		ConsumerQueue2Sort my_consumersQueue = new ConsumerQueue2Sort(IDPost2Com,ID2Post,IDCom2IDPost,total_queue,result);
		Thread consumersQueue_thread= new Thread(my_consumersQueue);
		
		ConsumerQueue2Sort my_consumersQueue2 = new ConsumerQueue2Sort(IDPost2Com,ID2Post,IDCom2IDPost,total_queue,result);
		Thread consumersQueue_thread2= new Thread(my_consumersQueue);
		
		consumers_thread.start();
		consumersQueue_thread.start();
		
		//consumers_thread.join();
		//if(!prodpost.isAlive())
		//	consumersQueue_thread2.start();
		consumersQueue_thread.join();
	
		System.out.println(new java.util.Date().getTime());
	}
}
