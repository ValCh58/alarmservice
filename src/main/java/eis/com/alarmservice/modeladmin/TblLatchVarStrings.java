// Generated with g9.

package eis.com.alarmservice.modeladmin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(indexes={@Index(name="TblLatchVarStrings_Value_IX", columnList="Value", unique=true)})
public class TblLatchVarStrings implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3318994620604770883L;
	/**
     * The optimistic lock. Available via standard bean get/set operations.
     */
    @Version
    @Column(name="LOCK_FLAG")
    private Integer lockFlag;

    /**
     * Access method for the lockFlag property.
     *
     * @return the current value of the lockFlag property
     */
    public Integer getLockFlag() {
        return lockFlag;
    }

    /**
     * Sets the value of the lockFlag property.
     *
     * @param aLockFlag the new value of the lockFlag property
     */
    public void setLockFlag(Integer aLockFlag) {
        lockFlag = aLockFlag;
    }

    @Id
    @Column(name="Value", unique=true, length=2000000000)
    private String value;
    @Column(name="Type", precision=2000000000, scale=10)
    private int type;

    /** Default constructor. */
    public TblLatchVarStrings() {
        super();
    }

    /**
     * Access method for value.
     *
     * @return the current value of value
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter method for value.
     *
     * @param aValue the new value for value
     */
    public void setValue(String aValue) {
        value = aValue;
    }

    /**
     * Access method for type.
     *
     * @return the current value of type
     */
    public int getType() {
        return type;
    }

    /**
     * Setter method for type.
     *
     * @param aType the new value for type
     */
    public void setType(int aType) {
        type = aType;
    }

}
