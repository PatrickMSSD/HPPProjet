package tse.hppproject.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Test;

import producers.Producer;
import tse.hppproject.Comment;
import tse.hppproject.ConsumerQueue2Sort;
import tse.hppproject.Consumers;
import tse.hppproject.Post;

public class testcase_2 {
	
	
	//temps actuel correspondant au dernier post ou commentaire traité
	Long total_time = null;

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
	
	
	List<Post> result = new ArrayList<Post>();
	
	Producer prod = new Producer(post_queue,comm_queue,"../HPPProjet/resourses/posts_test2.dat","../HPPProjet/resourses/comments_test2.dat");
	Thread prodpost = new Thread(prod);
	
	
	Consumers my_consumers = new Consumers(post_queue, comm_queue, total_queue, total_time,producer_end,consumers_end, IDPost2Com);
	Thread consumers_thread= new Thread(my_consumers);
	
	ConsumerQueue2Sort my_consumersQueue = new ConsumerQueue2Sort(IDPost2Com,ID2Post,IDCom2IDPost,total_queue,result);
	Thread consumersQueue_thread= new Thread(my_consumersQueue);
	
	@Test
	public void test_2_query1() {
		Post post = new Post("2010-05-31T00:01:01.943+0000|10|1|A5|A B", total_time, IDPost2Com);
		List<Post> result_expected = new ArrayList<Post>();
		result_expected.add(post);
		try {
			
			prodpost.start();
			consumers_thread.start();
			consumersQueue_thread.start();
			System.out.println(result_expected.get(0).getTs() + result.get(0).getTs());
			assertEquals(result_expected.get(0).getTs(), result.get(0).getTs());
			assertEquals(result_expected.get(0).getUser(), result.get(0).getUser());
			assertEquals(result_expected.get(0).getUser_id(), result.get(0).getUser_id());
			assertEquals(result_expected.get(0).getScore_total(), result.get(0).getScore_total());
			assertEquals(result_expected.get(0).getPost_id(), result.get(0).getPost_id());
			consumersQueue_thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
