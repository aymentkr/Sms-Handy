package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Date;


/**
 * Eine Klasse Message, die eine Nachricht �ber die SMSHandy Klasse verschickt kann.
 * Die Klasse erstellt am Anfang/als default ein leeres Konstruktor mit Werte gleich null und dem aktuellen Datum, dann
 * kann ein Benutzer ein Konstruktor mit Parametern content, to, from und date erstellen.
 */
public class Message {

	private StringProperty content;
	private StringProperty to;
	private StringProperty from;
	private Date date;
	

	
	/**
	 * Konstruktor mit Parametern
	 * @param content - Inhalt der Nachricht
	 * @param to - Empf�nger, an wen ust diese Nachricht gerichtet
	 * @param from - Absender, also wer hat die Nachricht geschickt
	 * @param date - Datum, wann ist die Nachricht geschickt
	 */
	public Message(String content, String to, String from, Date date) { 
		this.content = new SimpleStringProperty(content);
		this.to = new SimpleStringProperty(to);
		this.from = new SimpleStringProperty(from);
		this.date = date;
	}
	
	/**
	 * Gibt den content/Inhalt der Nachricht zur�ck
	 * @return aktueller Inhalt der Nachricht
	 */
	public String getContent() { 
		return content.get();
	}
	
	/**
	 * Gibt das Datum der Nachricht zur�ck
	 * @return Erstellungsdatum der SMS
	 */
	public Date getDate() { 
		return date;
	}
	
	/**
	 * Gibt den Absender der Nachricht zur�ck
	 * @return aktueller Absender der Nachricht
	 */
	public String getFrom() { 
		return from.get();
	}
	
	/**
	 * Gibt den Empf�nger zur�ck
	 * @return aktueller Empf�nger f�r die Nachricht
	 */
	public String getTo() { 
		return to.get();
	}
	 public void setDate(Date date) {
	        this.date = date;
	    }
	/**
	 * Gibt die vollst�ndige Nachricht als String zur�ck
	 * @return formatiert String mit allen Daten
	 */
	
    public StringProperty contentProperty() {
        return content;
    }

    public StringProperty fromProperty() {
        return from;
    }

    public StringProperty toProperty() {
        return to;
    }
    public void setContent(String content) {
        this.content = new SimpleStringProperty(content);
    }
    public void setFrom(String from) {
        this.from = new SimpleStringProperty(from);
    }
    public void setTo(String to) {
        this.to = new SimpleStringProperty(to);
    }
    
}