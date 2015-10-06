package pack1;

import static org.junit.Assert.*;

import org.junit.Test;

public class QuoteTest {

	@Test
	public void testDefaultConstructor() {
		Quote quote1 = new Quote("Quote1", "Author1");
		assertEquals(quote1, new Quote("Quote1", "Author1"));
		assertEquals(quote1.getQuote(), "Quote1");
		assertEquals(quote1.getAuthor(), "Author1");
	}
	
	@Test
	public void testEquals() {
		Quote quote1 = new Quote("Quote1", "Author1");
		Quote quote2 = quote1;
		Quote quote3 = new Quote("Quote3", "Author3");
		assertTrue(quote1.equals(new Quote("Quote1", "Author1")));
		assertTrue(quote1.equals(quote2));
		assertFalse(quote1.equals(quote3));
	}

	@Test
	public void testToString() {
		Quote quote1 = new Quote("Quote1", "Author1");
		assertTrue(quote1.toString().equals("Quote1|Author1"));
	}
}
