package producers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

	private BlockingQueue<String> liste = null;
	private BlockingQueue<String> liste2 = null;
	private String file = null;
	private String file2 = null;
	private Boolean producer_end;

	public Producer(BlockingQueue<String> liste, BlockingQueue<String> liste2, String file, String file2,
			Boolean producer_end) {
		super();
		this.liste = liste;
		this.liste2 = liste2;
		this.file = file;
		this.file2 = file2;
		this.producer_end = producer_end;
	}

	public Queue<String> getListe() {
		return liste;
	}

	public void setListe(BlockingQueue<String> liste) {
		this.liste = liste;
	}

	public Boolean getProducer_end() {
		return producer_end;
	}

	public void setProducer_end(Boolean producer_end) {
		this.producer_end = producer_end;
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
		String line =br.readLine(), line2= br2.readLine();
		this.liste.put(line);
		this.liste2.put(line2);
		while (((line = br.readLine()) != null) || (line2 = br2.readLine()) != null) {
			System.out.println("on produit");
			this.liste.put(line);
			this.liste2.put(line2);
			Thread.sleep(5);
			
		}
		br.close();
		br2.close();
		liste.put("*");

		producer_end = true;

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
