// Generated with g9.

package eis.com.alarmservice.modeladmin;

import java.io.Serializable;

import javax.persistence.Column;
//import javax.persistence.Entity;
import javax.persistence.Version;

//@Entity
public class TblMetaData implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1417653424855496808L;
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

    @Column(name="ByteOrder", precision=2000000000, scale=10)
    private int byteOrder;

    /** Default constructor. */
    public TblMetaData() {
        super();
    }

    /**
     * Access method for byteOrder.
     *
     * @return the current value of byteOrder
     */
    public int getByteOrder() {
        return byteOrder;
    }

    /**
     * Setter method for byteOrder.
     *
     * @param aByteOrder the new value for byteOrder
     */
    public void setByteOrder(int aByteOrder) {
        byteOrder = aByteOrder;
    }

}
