package producers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{
	private BlockingQueue<String> liste = null;
	private BlockingQueue<String> liste2 = null;
	private String file = null;
	private String file2 = null;

	

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

	public void readFile() throws IOException, InterruptedException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		BufferedReader br2 = new BufferedReader(new FileReader(file2));
		String line;
		String line2;
		/*for (int i = 0 ; i < n ; i ++) {
			line = br.readLine();
			this.liste.put(line);
		}*/
		while (((line = br.readLine()) != null)||(line2 = br2.readLine()) != null)
		{
				line = br.readLine();
				this.liste.put(line);
			
			
				line2 = br2.readLine();
				this.liste2.put(line2);
		}
		br.close();
		br2.close();
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
