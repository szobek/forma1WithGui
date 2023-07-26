package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import forma1.Forma1MainFrame;

class DataTests {

	@Test
	void listSizeAfterReadtest() {
		Forma1MainFrame app = new Forma1MainFrame();
		assertTrue(app.listSize()>0);
	}

}
