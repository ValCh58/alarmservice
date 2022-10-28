// Generated with g9.

package eis.com.alarmservice.modeladmin;

import java.io.Serializable;

import javax.persistence.Column;
//import javax.persistence.Entity;
import javax.persistence.Version;

//@Entity
public class TblVersion implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3290368792178062139L;
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

    @Column(name="Version", precision=2000000000, scale=10)
    private int version;

    /** Default constructor. */
    public TblVersion() {
        super();
    }

    /**
     * Access method for version.
     *
     * @return the current value of version
     */
    public int getVersion() {
        return version;
    }

    /**
     * Setter method for version.
     *
     * @param aVersion the new value for version
     */
    public void setVersion(int aVersion) {
        version = aVersion;
    }

}
