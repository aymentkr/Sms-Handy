package application.model;

/**
 * Klasse PrepaidSmsHandy, eine Subklasse von SmSHandy. Ein Handy, das �ber ein beim Provider verwaltetes- 
 * -Guthaben verf�gt und dessen SMS-Versand �ber dieses Guthaben abgerechnet wird.
 * 
 */
public class PrepaidSmsHandy extends SmSHandy{
	
	public final int COST_PER_SMS = 10;
	
	/**
	 * Ein Konstruktor zum Erstellen eines neuen PrepaidHandy
	 * @param number - die Handynummer
	 * @param provider - die Providerinstantz
	 */
	public PrepaidSmsHandy(String number, Provider provider) {
		super(number, provider);
		deposit(100);
	}
	
	int getCredit() {
		return getProvider().getCreditForSmsHandy(getNumber());
	}

	/**
	 * Pr�ft, ob das Guthaben noch f�r das Versenden einer SMS reicht.
	 * Diese Methode ist specified by canSendSms in der SmsHandyklasse
	 * @return ist das Guthaben noch ausreichend
	 */
	@Override
	public boolean canSendSms() {
		return (getCredit() - COST_PER_SMS) >= 0;
	}

	/**
	 * Zieht die Kosten f�r eine SMS vom Guthaben ab
	 * Diese Methode ist specified by payForSms in der SmsHandyklasse
	 */
	@Override
	public void payForSms() {
		getProvider().getCredits().put(getNumber(), getCredit() - COST_PER_SMS);
	}
	
	/**
	 * L�dt das Guthaben fuer das SmsHandy-Objekt auf.
	 * Die Methode nimmt balance des Kontos und addiert amount, die wir aufladen wollen.
	 * @param amount - Menge die man aufladen will
	 */
	public void deposit(int amount) {
		if (amount>0) getProvider().deposit(getNumber(), amount);
	}
	
	public String getBalance() {
		if (getProvider() != null)
		return String.valueOf(getCredit());
		else return("0");
	}
	
}