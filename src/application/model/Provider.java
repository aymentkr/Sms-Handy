package application.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * Die Klasse Provider. 
 */
public class Provider {
	
	private final StringProperty name;
	private Map<String, Integer> credits;
	static List<Provider> providers = new ArrayList<>();
	private Map<String, SmSHandy> subscriber;
	
	/**
	 * Konstruktor f�r Objekte der Klasse Provider.
	 * Es erstellt zwei HashMap credits und subscriber und fuegt die Listen zu eine ArrayList hinzu.   
	 */
	public Provider(String name){
		this.name = new SimpleStringProperty(name);
		credits = new HashMap<>(); 
		subscriber = new HashMap<>();
		providers.add(this);
	}
	
	/**
	 * Sendet die SMS an den Empfaenger, wenn dieser bekannt ist.
	 * @param message - die zu sendete SMS
	 * @return - true, wenn SMS gesendet werden konnte
	 */
	public boolean send(Message message) {
		String sendMsg = message.getFrom();
		String receiveMsg = message.getTo();
		
		SmSHandy sender = subscriber.get(sendMsg);
		Provider provider = findProviderFor(receiveMsg);
		SmSHandy receiver = provider.subscriber.get(receiveMsg);		
		
		if (message != null && provider != null && sender.canSendSms()) {
			sender.addSent(message);
			sender.payForSms();
			receiver.receiveSms(message);
			return true;
		}
		return false;
	}
	
	/**
	 * Registriert ein neues Handy bei diesem ProSvider.
	 * @param smsHandy - das neue SmsHandy
	 */
	public void register(SmSHandy smsHandy) {
		if (smsHandy != null) {
			subscriber.put(smsHandy.getNumber(), smsHandy);
		}
	}
	
	/**
	 * Laedt Guthaben f�r ein Handy auf. 
	 * Das ist noetig, weil das Handy sein Guthaben nicht selbst aendern kann, sondern nur der Provider. 
	 * Negative Geldmengen werden hier erlaubt, um ueber diese Funktion auch die Kosten fuer eine Nachricht abziehen zu koennen. 
	 * Negative Werte beim haendischen Aufladen werden in der Klasse SmsHandy verhindert.
	 * @param number - Nummer des Handys
	 * @param amount - Hoehe des Geldbetrages
	 */
	public void deposit(String number, int amount) {
		if(credits.containsKey(number)) {
			credits.put(number.trim(), credits.get(number) + amount);
		}
		else credits.put(number, amount);
		System.out.println("You put " + amount + " credits on your account");
	}
	
	/**
	 * //Gibt das aktuelle Guthaben des betreffenden Handys zurueck
	 * @param number - Nummer des gewuenschten Handys
	 * @return - aktuelles Guthaben des Handys
	 */
	public int getCreditForSmsHandy(String number) {
		return credits.getOrDefault(number, 0);
	}
	
	/**
	 * 
	 * @param receiver
	 * @return - true, wenn HashMap  contains receiver
	 */
	private boolean canSendTo(String receiver) {	
		return subscriber.containsKey(receiver);
	}

	/**
	 * Eine findProvider Methode, die eine Provider sucht.
	 * @param receiver
	 * @return - gibt provider zurueck wenn true und null wenn false
	 */
	private static Provider findProviderFor(String receiver) {
		for (Provider provider : providers) {
			if (provider.canSendTo(receiver)) {
				return provider;
			}
		}
		return null;
	}
	
	public Map<String, Integer> getCredits() {
		return credits;
	}
	public void setCredits(Map<String, Integer> credits) {
		this.credits = credits;
	}

	public Map<String, SmSHandy> getSubscriber() {
		return subscriber;
	}
	public StringProperty NameProperty() {
		return name;
	}
	public String getName() {
		return name.get();
	}
	public static List<Provider> getProviderList() {
		return providers;
	}

	public void delete(SmSHandy smsHandy) {
		if (smsHandy != null) {
			subscriber.remove(smsHandy.getNumber(), smsHandy);
		}
	}
	
}