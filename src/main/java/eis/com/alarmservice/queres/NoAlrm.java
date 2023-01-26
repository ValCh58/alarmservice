package eis.com.alarmservice.queres;

/**
 *  Для сравнения при выборе групп и аварий. 
 * @author Valeriy
 */
public enum NoAlrm {
    NO_ID_GROUP(99999), NO_ID_ALARM(99998);

	private int cod;
	NoAlrm(int cod) {
		this.cod = cod;
	}
	
	public int getCod() {return cod;}
}
