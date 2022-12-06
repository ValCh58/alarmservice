// Generated with g9.

package eis.com.alarmservice.modeladmin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class TblAlarmUpload implements Serializable {

    /**
	 * Upload Table
	 */
	private static final long serialVersionUID = -2731322620118718184L;
	
    @Id
    @Column(name="TSLast", precision=2000000000, scale=10)
    private int tsLast;
    @Column(name="TSActive", precision=2000000000, scale=10)
    private int tsActive;
    @Column(name="TSInactive", precision=2000000000, scale=10)
    private int tsInactive;
    @Column(name="TSAckn", precision=2000000000, scale=10)
    private int tsAckn;
    @Column(name="TValue", precision=2000000000, scale=10)
    private double tValue;
    @Column(name="TType", precision=2000000000, scale=10)
    private int tType;
    @Column(name="LValue1", precision=2000000000, scale=10)
    private double lValue1;
    @Column(name="LType1", precision=2000000000, scale=10)
    private int lType1;
    @Column(name="LValue2", precision=2000000000, scale=10)
    private double lValue2;
    @Column(name="LType2", precision=2000000000, scale=10)
    private int lType2;
    @Column(name="GroupId", precision=2000000000, scale=10)
    private int groupId;
    @Column(name="AlarmId", precision=2000000000, scale=10)
    private int alarmId;
    @Column(name="ClassId", precision=2000000000, scale=10)
    private int classId;
    @Column(name="Priority", precision=2000000000, scale=10)
    private int priority;
    @Column(name="State", precision=2000000000, scale=10)
    private int state;
    @Column(name="OffsetLast", precision=2000000000, scale=10)
    private int offsetLast;
    @Column(name="OffsetActive", precision=2000000000, scale=10)
    private int offsetActive;
    @Column(name="OffsetInactive", precision=2000000000, scale=10)
    private int offsetInactive;
    @Column(name="OffsetAckn", precision=2000000000, scale=10)
    private int offsetAckn;

    /** Default constructor. */
    public TblAlarmUpload() {
        super();
    }

    /**
     * Access method for tsLast.
     *
     * @return the current value of tsLast
     */
    public int getTsLast() {
        return tsLast;
    }

    /**
     * Setter method for tsLast.
     *
     * @param aTsLast the new value for tsLast
     */
    public void setTsLast(int aTsLast) {
        tsLast = aTsLast;
    }

    /**
     * Access method for tsActive.
     *
     * @return the current value of tsActive
     */
    public int getTsActive() {
        return tsActive;
    }

    /**
     * Setter method for tsActive.
     *
     * @param aTsActive the new value for tsActive
     */
    public void setTsActive(int aTsActive) {
        tsActive = aTsActive;
    }

    /**
     * Access method for tsInactive.
     *
     * @return the current value of tsInactive
     */
    public int getTsInactive() {
        return tsInactive;
    }

    /**
     * Setter method for tsInactive.
     *
     * @param aTsInactive the new value for tsInactive
     */
    public void setTsInactive(int aTsInactive) {
        tsInactive = aTsInactive;
    }

    /**
     * Access method for tsAckn.
     *
     * @return the current value of tsAckn
     */
    public int getTsAckn() {
        return tsAckn;
    }

    /**
     * Setter method for tsAckn.
     *
     * @param aTsAckn the new value for tsAckn
     */
    public void setTsAckn(int aTsAckn) {
        tsAckn = aTsAckn;
    }

    /**
     * Access method for tValue.
     *
     * @return the current value of tValue
     */
    public double getTValue() {
        return tValue;
    }

    /**
     * Setter method for tValue.
     *
     * @param aTValue the new value for tValue
     */
    public void setTValue(double aTValue) {
        tValue = aTValue;
    }

    /**
     * Access method for tType.
     *
     * @return the current value of tType
     */
    public int getTType() {
        return tType;
    }

    /**
     * Setter method for tType.
     *
     * @param aTType the new value for tType
     */
    public void setTType(int aTType) {
        tType = aTType;
    }

    /**
     * Access method for lValue1.
     *
     * @return the current value of lValue1
     */
    public double getLValue1() {
        return lValue1;
    }

    /**
     * Setter method for lValue1.
     *
     * @param aLValue1 the new value for lValue1
     */
    public void setLValue1(double aLValue1) {
        lValue1 = aLValue1;
    }

    /**
     * Access method for lType1.
     *
     * @return the current value of lType1
     */
    public int getLType1() {
        return lType1;
    }

    /**
     * Setter method for lType1.
     *
     * @param aLType1 the new value for lType1
     */
    public void setLType1(int aLType1) {
        lType1 = aLType1;
    }

    /**
     * Access method for lValue2.
     *
     * @return the current value of lValue2
     */
    public double getLValue2() {
        return lValue2;
    }

    /**
     * Setter method for lValue2.
     *
     * @param aLValue2 the new value for lValue2
     */
    public void setLValue2(double aLValue2) {
        lValue2 = aLValue2;
    }

    /**
     * Access method for lType2.
     *
     * @return the current value of lType2
     */
    public int getLType2() {
        return lType2;
    }

    /**
     * Setter method for lType2.
     *
     * @param aLType2 the new value for lType2
     */
    public void setLType2(int aLType2) {
        lType2 = aLType2;
    }

    /**
     * Access method for groupId.
     *
     * @return the current value of groupId
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Setter method for groupId.
     *
     * @param aGroupId the new value for groupId
     */
    public void setGroupId(int aGroupId) {
        groupId = aGroupId;
    }

    /**
     * Access method for alarmId.
     *
     * @return the current value of alarmId
     */
    public int getAlarmId() {
        return alarmId;
    }

    /**
     * Setter method for alarmId.
     *
     * @param aAlarmId the new value for alarmId
     */
    public void setAlarmId(int aAlarmId) {
        alarmId = aAlarmId;
    }

    /**
     * Access method for classId.
     *
     * @return the current value of classId
     */
    public int getClassId() {
        return classId;
    }

    /**
     * Setter method for classId.
     *
     * @param aClassId the new value for classId
     */
    public void setClassId(int aClassId) {
        classId = aClassId;
    }

    /**
     * Access method for priority.
     *
     * @return the current value of priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Setter method for priority.
     *
     * @param aPriority the new value for priority
     */
    public void setPriority(int aPriority) {
        priority = aPriority;
    }

    /**
     * Access method for state.
     *
     * @return the current value of state
     */
    public int getState() {
        return state;
    }

    /**
     * Setter method for state.
     *
     * @param aState the new value for state
     */
    public void setState(int aState) {
        state = aState;
    }

    /**
     * Access method for offsetLast.
     *
     * @return the current value of offsetLast
     */
    public int getOffsetLast() {
        return offsetLast;
    }

    /**
     * Setter method for offsetLast.
     *
     * @param aOffsetLast the new value for offsetLast
     */
    public void setOffsetLast(int aOffsetLast) {
        offsetLast = aOffsetLast;
    }

    /**
     * Access method for offsetActive.
     *
     * @return the current value of offsetActive
     */
    public int getOffsetActive() {
        return offsetActive;
    }

    /**
     * Setter method for offsetActive.
     *
     * @param aOffsetActive the new value for offsetActive
     */
    public void setOffsetActive(int aOffsetActive) {
        offsetActive = aOffsetActive;
    }

    /**
     * Access method for offsetInactive.
     *
     * @return the current value of offsetInactive
     */
    public int getOffsetInactive() {
        return offsetInactive;
    }

    /**
     * Setter method for offsetInactive.
     *
     * @param aOffsetInactive the new value for offsetInactive
     */
    public void setOffsetInactive(int aOffsetInactive) {
        offsetInactive = aOffsetInactive;
    }

    /**
     * Access method for offsetAckn.
     *
     * @return the current value of offsetAckn
     */
    public int getOffsetAckn() {
        return offsetAckn;
    }

    /**
     * Setter method for offsetAckn.
     *
     * @param aOffsetAckn the new value for offsetAckn
     */
    public void setOffsetAckn(int aOffsetAckn) {
        offsetAckn = aOffsetAckn;
    }

}
