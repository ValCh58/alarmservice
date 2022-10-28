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
@Table(indexes={@Index(name="AlarmGroup_id_group_IX", columnList="id_group", unique=true)})
public class AlarmGroup implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3577727359163232817L;

	/** Primary key. */
    protected static final String PK = "idAlarmGroup";

    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_alarm_group", unique=true, nullable=false, precision=2000000000, scale=10)
    private int idAlarmGroup;
    
    @Column(name="id_group", unique=true, nullable=false, precision=2000000000, scale=10)
    private int idGroup;
    
    @Column(name="name_group", nullable=false, length=2000000000)
    private String nameGroup;

    /** Default constructor. */
    public AlarmGroup() {
        super();
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
     * Access method for idGroup.
     *
     * @return the current value of idGroup
     */
    public int getIdGroup() {
        return idGroup;
    }

    /**
     * Setter method for idGroup.
     *
     * @param aIdGroup the new value for idGroup
     */
    public void setIdGroup(int aIdGroup) {
        idGroup = aIdGroup;
    }

    /**
     * Access method for nameGroup.
     *
     * @return the current value of nameGroup
     */
    public String getNameGroup() {
        return nameGroup;
    }

    /**
     * Setter method for nameGroup.
     *
     * @param aNameGroup the new value for nameGroup
     */
    public void setNameGroup(String aNameGroup) {
        nameGroup = aNameGroup;
    }

    /**
     * Compares the key for this instance with another AlarmGroup.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class AlarmGroup and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof AlarmGroup)) {
            return false;
        }
        AlarmGroup that = (AlarmGroup) other;
        if (this.getIdAlarmGroup() != that.getIdAlarmGroup()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another AlarmGroup.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof AlarmGroup)) return false;
        return this.equalKeys(other) && ((AlarmGroup)other).equalKeys(this);
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
        i = getIdAlarmGroup();
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
        StringBuffer sb = new StringBuffer("[AlarmGroup |");
        sb.append(" idAlarmGroup=").append(getIdAlarmGroup());
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
        ret.put("idAlarmGroup", Integer.valueOf(getIdAlarmGroup()));
        return ret;
    }

}
