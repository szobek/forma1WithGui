package forma1;

import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

import java.util.List;


public class FileHandler {

	public static void readCsv(List<Pilota> pilots) {
	
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("pilotak.csv"),"UTF-8"));) {
			br.readLine();
			while(br.ready()) {
				String[] line = br.readLine().split(";");

				if(line.length==3) {
					pilots.add(new Pilota(line[0], LocalDate.parse(line[1].replace(".", "-")), line[2]));
				}else {
					pilots.add(new Pilota(line[0], LocalDate.parse(line[1].replace(".", "-")), line[2], Integer.parseInt(line[3])));	
				}
				

				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
 