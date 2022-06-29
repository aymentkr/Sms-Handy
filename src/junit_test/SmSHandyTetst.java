package junit_test;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.model.Message;
import application.model.SmSHandy;

class SmSHandyTetst {
	private SmSHandy sms;
	private Message msg;
	private String to = "*101#";
	
	@Test
	void sendSmS101Check() {
		try {
		sms.sendSms("*101#", "I have a surprise for you");
		assertEquals(to,"*101");
		} catch(NullPointerException e) {
			System.out.println(e);
		}
	}
	
	@Test
	void sendSmS() {
		//sms.sendSms("Cousine", "I have a surprise for you");
		//sms.canSendSms();
		//Message message = new Message(msg.getContent(), msg.getTo(), msg.getFrom(), msg.getDate());
	}

}
