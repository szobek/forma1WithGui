package forma1;

import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

import java.util.List;


public class FileHandler {

	public static void readCsv(List<Pilota> pilots) {
	
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("rajtszamok.csv"),"UTF-8"));) {
			br.readLine();
			while(br.ready()) {
				String[] line = br.readLine().split(";");
				pilots.add(new Pilota(Integer.parseInt(line[0]) ,  line[1], line[2]));
								
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
 