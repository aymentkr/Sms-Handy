package application.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Eine abstrakte Basisklasse SmSHandy
 */
public abstract class SmSHandy {
	
	private StringProperty number;
	private Provider provider;
	
	private List<Message> sent;
	private List<Message> received;

	/**
	 * Ein Konstruktor fuer Objekte der Klasse SmSHandy
	 * @param number - die Handynummer
	 * @param provider - die Providerinstanz
	 */
	
	public SmSHandy (String number,Provider provider) {
		if (provider != null) {
		this.number = new SimpleStringProperty(number.trim());
		this.provider = provider;
		this.sent = new ArrayList<>();
		this.received = new ArrayList<>();
		provider.register(this);
		}
	}
	
	/**
	 * Schickt eine SMSueber den Provider an den Empaenger
	 * @param to - der Empaenger der SMS
	 * @param content - der Inhalt der Message
	 */
	public void sendSms(String to,String content) {
		if (to.equals("*101#"))
			System.out.printf("Das Guthaben auf Ihrem Gutschein: ", provider.getCreditForSmsHandy(getNumber())); 
            else if (canSendSms()) {//content, to, from, date
			Message msg = new Message(content, to, getNumber(), new Date());
			if (provider.send(msg))System.out.println("Ihre Nachricht wurde erfolgreich gesendet!");
			else System.out.println("Es ist ein Fehler aufgetreten. Ihre Nachricht wurde nicht gesendet!");
		} else
		    System.out.println("Das Guthaben auf Ihrem Gutschein reicht nicht aus!");
	}
	/**
	 * Abstrakte Methode zur Pr�fung, ob der Versand der SMS noch bezahlt werden kann
	 * @return - ist der Versand der SMS noch m�glich
	 */
	public abstract boolean canSendSms();
	
	/**
	 * Abstrakte Methode zum Bezahlen des SMS-Versand
	 */
	public abstract void payForSms();
	
	/**
	 * Legt fest, wie viel Geld sich auf Ihrem Konto befindet
	 * @param balance - Geldsumme auf der Konto
	 */
	
	/*
	 * public void setBalance(int balance) { *********
		this.balance = balance;
	}
	 */
	
	/**
	 * Schickt eine SMS ohne den Provider an den Empfaenger
	 * @param peer - das empfangende Handy
	 * @param content - der Inhalt der SMS
	 */
	public void SendSmsDirect (SmSHandy peer,String content) {
		if (peer != null) {
			Message msg = new Message(content,peer.getNumber(),getNumber(), new Date());
			sent.add(msg);
			peer.receiveSms(msg);
		}
	}
	
	/**
	 * Empfaengt eine SMS und speichert diese in den empfangenen SMS
	 * @param msg - das Message-Objekt, welches an das zweite Handy gesendet werden soll
	 */
	public void receiveSms(Message msg) {
		received.add(msg);
	}
	
	/**
	 * Gibt eine Liste aller empfangenen SMS auf der Konsole aus
	 */
	public void listReceived() {
		if (received.size() == 0) System.out.println("Received list ist leer!");
		else {
		System.out.println("Received messages: ");
		for (Message msg : received) {
			System.out.println(msg);
			}
		}
	}
	
	/**
	 * Gibt eine Liste aller gesendete SMS auf der Konsole aus.
	 */
	public void listSent() {
		if (sent.size() == 0) {
			System.out.println("Sent list ist leer!");
			return;
		}
		System.out.println("Sent messages: ");
		for (Message msg : sent) {
			System.out.println(msg);
		}
	}
	
	/**
	 * Gibt die Handynummer zurueck
	 * @return - die Handynummer
	 */
	public String getNumber() {
		return number.get();
	}
	
	/**
	 * Gibt den aktuellen Provider zur�ck.
	 * @return - aktueller Provider des Handys
	 */
	public Provider getProvider() {
		return provider;
	}
	
	/**
	 * Setzt den Provider
	 * @param provider - ProviderInstanz
	 */
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	
	public StringProperty NumProperty() {
		return number;
	}
	public String getArtHandy() {
		return (this instanceof TariffPlanSmsHandy) ? "Tariff Plan" : "Prepaid Plan";
	}

	public List<Message> getSent() {
		return sent;
	}
	public List<Message> getReceived() {
		return received;
	}
	public void addSent(Message message) {
		sent.add(message);
	}
	
}