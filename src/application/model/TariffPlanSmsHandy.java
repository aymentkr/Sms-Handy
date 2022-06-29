package application.model;

/**
 * Klasse TariffPlanSmsHandy. Ein Vertragshandy, das �ber eine bestimmte Menge an Frei-SMS verf�gt. 
 * In einer sp�teren Version k�nnten diese nach einer bestimmten Zeit wieder zur�ckgesetzt werden. 
 * Dies wird vorerst noch nicht ber�cksichtigt.
 */
public class TariffPlanSmsHandy extends SmSHandy{
	
	private int remainingFreeSms = 100;

	/**
	 * Konstruktor zum Erstellen eines neuen TariffPlanHandy
	 * @param number - die Handynummer
	 * @param provider - die Providerinstanz
	 */
	public TariffPlanSmsHandy(String number, Provider provider) {
		super(number, provider);
	}
	
	/**
	 * Pr�ft, ob Frei-SMS noch zum Senden ausreichen.
	 * Es ist specified by canSendSmS in der Klasse SmSHandy
	 * @return noch Frei-SMS vorhaben?
	 */
	@Override
    public boolean canSendSms() {
		return remainingFreeSms > 0;
    }
	
	/**
	 * Reduziert die Frei-SMS.
	 * Specified by payForSmS in der Klasse SmSHandy
	 */
	@Override
    public void payForSms() {
		 remainingFreeSms--;
    }
	
	/**
	 * Liefert Anzahl der verbliebenen Frei-SMS.
	 * @return Anzahl der Frei-SmS
	 */
	public String getRemainingFreeSms() {
		return String.valueOf(remainingFreeSms);
	}
	
	
}