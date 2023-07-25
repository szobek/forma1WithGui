package forma1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class FileHandler {

	public static void readCsv(List<Pilota> pilots) {

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("rajtszamok.csv"), "UTF-8"));) {
			br.readLine();
			while (br.ready()) {
				String[] line = br.readLine().split(";");
				pilots.add(new Pilota(Integer.parseInt(line[0]), line[1], line[2]));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void writeCsvIfCount(List<Pilota> pilots) {
		File file = new File("orszagok.csv");

		try (FileOutputStream fos = new FileOutputStream(file);
				OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
				BufferedWriter writer = new BufferedWriter(osw)) {
			Map<String, Integer> count = new HashMap<String, Integer>();
			for (Pilota pilota : pilots) {
				if (count.containsKey(pilota.getNationCode())) {
					count.replace(pilota.getNationCode(), count.get(pilota.getNationCode()) + 1);
				} else {
					count.put(pilota.getNationCode(), 1);
				}
			}
			
			for(Map.Entry<String, Integer> elem:count.entrySet()) {
				if(elem.getValue()>1) {
					writer.append(elem.getKey()+" : "+elem.getValue()+"\n");
				}
			}
			JOptionPane.showMessageDialog(null, "fájlírás kész");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
