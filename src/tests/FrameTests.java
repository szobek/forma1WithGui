package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import forma1.Forma1MainFrame;


class FrameTests {

	@Test
	void frameTitleTest() {
		Forma1MainFrame app = new Forma1MainFrame();
		
		assertEquals("Forma-1 pilóták", app.getFrameTitle());
	}

	

	
}
