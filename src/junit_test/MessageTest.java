package junit_test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import application.model.Message;

class MessageTesten {
	private Message message;
	
	@Test
	public void constructorMessage() {
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		message = new Message("This is message content", "Aymen", "Tekari", date);
		assertEquals("This is message content", message.getContent());
		assertEquals("Aymen", message.getTo());
		assertEquals("Tekari", message.getFrom());
		assertEquals(date, message.getDate());
	}
	
	@Test
	public void constructorFailExpected() {
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		message = new Message("This is message content", "Aymen", "Tekari", date);
		assertNotEquals("This is message content", message.getContent());
		assertEquals("Aymen", message.getTo());
		assertEquals("Tekari", message.getFrom());
		assertEquals(date, message.getDate());
	}
}
