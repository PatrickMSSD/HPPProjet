package producers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

	private BlockingQueue<String> liste =  new ArrayBlockingQueue<String>(1000000);
	private BlockingQueue<String> liste2 = new ArrayBlockingQueue<String>(1000000);
	private String file = new String();
	private String file2 = new String();
	
	public Producer(BlockingQueue<String> liste, BlockingQueue<String> liste2, String file, String file2) {
		super();
		this.liste = liste;
		this.liste2 = liste2;
		this.file = file;
		this.file2 = file2;
	}

	public Queue<String> getListe() {
		return liste;
	}

	public void setListe(BlockingQueue<String> liste) {
		this.liste = liste;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	// lecture des fichiers en alternance
	public void readFile() throws IOException, InterruptedException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		BufferedReader br2 = new BufferedReader(new FileReader(file2));
		String line = null, line2 = null;
		while ((((line = br.readLine()) != null) && (line2 = br2.readLine()) != null) || line!=null ||line2!=null)  {
			if(line !=null)
			this.liste.put(line);
			if(line2 !=null)
			this.liste2.put(line2);
		}
		br.close();
		br2.close();
		liste.put("*");
	}


	public void run() {
		// TODO Auto-generated method stub
		try {
			this.readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
