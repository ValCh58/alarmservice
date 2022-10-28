// Generated with g9.

package eis.com.alarmservice.modeladmin;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes={@Index(name="AlarmName_id_alarm_IX", columnList="id_alarm", unique=true)})
public class AlarmName implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5916431316467025201L;

	/** Primary key. */
    protected static final String PK = "idAlarmName";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_alarm_name", unique=true, nullable=false, precision=2000000000, scale=10)
    private int idAlarmName;
    
    @Column(name="id_alarm", nullable=false, precision=2000000000, scale=10)
    private int idAlarm;
    
    @Column(name="alarm_name", nullable=false, length=2000000000)
    private String alName;
    
    @Column(name="id_alarm_group", nullable=false, precision=2000000000, scale=10)
    private int idAlarmGroup;

    /** Default constructor. */
    public AlarmName() {
        super();
    }

    /**
     * Access method for idAlarmName.
     *
     * @return the current value of idAlarmName
     */
    public int getIdAlarmName() {
        return idAlarmName;
    }

    /**
     * Setter method for idAlarmName.
     *
     * @param aIdAlarmName the new value for idAlarmName
     */
    public void setIdAlarmName(int aIdAlarmName) {
        idAlarmName = aIdAlarmName;
    }

    /**
     * Access method for idAlarm.
     *
     * @return the current value of idAlarm
     */
    public int getIdAlarm() {
        return idAlarm;
    }

    /**
     * Setter method for idAlarm.
     *
     * @param aIdAlarm the new value for idAlarm
     */
    public void setIdAlarm(int aIdAlarm) {
        idAlarm = aIdAlarm;
    }

    /**
     * Access method for alName.
     *
     * @return the current value of alName
     */
    public String getAlName() {
        return alName;
    }

    /**
     * Setter method for alarmName.
     *
     * @param aAlName the new value for alarmName
     */
    public void setAlName(String aAlName) {
        alName = aAlName;
    }

    /**
     * Access method for idAlarmGroup.
     *
     * @return the current value of idAlarmGroup
     */
    public int getIdAlarmGroup() {
        return idAlarmGroup;
    }

    /**
     * Setter method for idAlarmGroup.
     *
     * @param aIdAlarmGroup the new value for idAlarmGroup
     */
    public void setIdAlarmGroup(int aIdAlarmGroup) {
        idAlarmGroup = aIdAlarmGroup;
    }

    /**
     * Compares the key for this instance with another AlarmName.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class AlarmName and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof AlarmName)) {
            return false;
        }
        AlarmName that = (AlarmName) other;
        if (this.getIdAlarmName() != that.getIdAlarmName()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another AlarmName.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof AlarmName)) return false;
        return this.equalKeys(other) && ((AlarmName)other).equalKeys(this);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return Hash code
     */
    @Override
    public int hashCode() {
        int i;
        int result = 17;
        i = getIdAlarmName();
        result = 37*result + i;
        return result;
    }

    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[AlarmName |");
        sb.append(" idAlarmName=").append(getIdAlarmName());
        sb.append("]");
        return sb.toString();
    }

    /**
     * Return all elements of the primary key.
     *
     * @return Map of key names to values
     */
    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
        ret.put("idAlarmName", Integer.valueOf(getIdAlarmName()));
        return ret;
    }

}
