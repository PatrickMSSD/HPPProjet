package producers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{
	private BlockingQueue<String> liste = null;
	private String file = null;

	public Producer(BlockingQueue<String> liste, String file) {
		super();
		this.liste = liste;
		this.file = file;
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

	public void readFile() throws IOException, InterruptedException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		/*for (int i = 0 ; i < n ; i ++) {
			line = br.readLine();
			this.liste.put(line);
		}*/
		while ((line = br.readLine()) != null)
		{
			line = br.readLine();
			this.liste.put(line);
		}
		br.close();

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
