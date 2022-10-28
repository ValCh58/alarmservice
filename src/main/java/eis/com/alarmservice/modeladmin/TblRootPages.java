// Generated with g9.

package eis.com.alarmservice.modeladmin;

import java.io.Serializable;

import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Index;
//import javax.persistence.Table;
import javax.persistence.Version;

//@Entity
//@Table(indexes={@Index(name="TblRootPages_rootpage_IX", columnList="rootpage", unique=true)})
public class TblRootPages implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7598766351444779639L;
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

    @Column(length=2000000000)
    private String type;
    @Column(length=2000000000)
    private String name;
    @Column(name="tbl_name", length=2000000000)
    private String tblName;
    @Column(unique=true, precision=2000000000, scale=10)
    private int rootpage;

    /** Default constructor. */
    public TblRootPages() {
        super();
    }

    /**
     * Access method for type.
     *
     * @return the current value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for type.
     *
     * @param aType the new value for type
     */
    public void setType(String aType) {
        type = aType;
    }

    /**
     * Access method for name.
     *
     * @return the current value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for name.
     *
     * @param aName the new value for name
     */
    public void setName(String aName) {
        name = aName;
    }

    /**
     * Access method for tblName.
     *
     * @return the current value of tblName
     */
    public String getTblName() {
        return tblName;
    }

    /**
     * Setter method for tblName.
     *
     * @param aTblName the new value for tblName
     */
    public void setTblName(String aTblName) {
        tblName = aTblName;
    }

    /**
     * Access method for rootpage.
     *
     * @return the current value of rootpage
     */
    public int getRootpage() {
        return rootpage;
    }

    /**
     * Setter method for rootpage.
     *
     * @param aRootpage the new value for rootpage
     */
    public void setRootpage(int aRootpage) {
        rootpage = aRootpage;
    }

}
