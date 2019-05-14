package producers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Producer {
	private List<String> liste = null;
	private String file = null;

	public Producer(List<String> liste, String file) {
		super();
		this.liste = liste;
		this.file = file;
	}

	public List<String> getListe() {
		return liste;
	}

	public void setListe(List<String> liste) {
		this.liste = liste;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void readFile(int n) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		for (int i = 0 ; i < n ; i ++) {
			line = br.readLine();
			this.liste.add(line);
		}
		br.close();

	}

}
