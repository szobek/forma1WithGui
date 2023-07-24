package forma1;

import java.util.ArrayList;
import java.util.List;

public class Main {
	List<Pilota> pilots = new ArrayList<Pilota>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
new Main().start();
	}
	public void start() {
		FileHandler.readCsv(pilots);
		for(Pilota pilot : pilots) {
			System.out.println(pilot.getName());
		}
	}

}
