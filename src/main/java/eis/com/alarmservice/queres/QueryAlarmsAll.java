package eis.com.alarmservice.queres;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;

import org.springframework.stereotype.Component;

import eis.com.alarmservice.dto.TblAlarmDTO;
import eis.com.alarmservice.utility.ReportUtils;

@Component

public class QueryAlarmsAll {
	
	//Component
	private ReportUtils reportUtils; 
	
	@PersistenceContext
	private EntityManager em;
	private List<TblAlarmDTO> listTblAlarmDTO = new ArrayList<>();
    private String qeryString = "select alarms.tsact_order, alarms.tsactive, iiF(tsinactive=\"01-01-1601 00:00:00\", \"\", tsinactive) as tsinactive, alarms.nameAlarm, alarms.nameGroup, alarms.groupId, alarms.alarmId\r\n"
    		+ " from(select "
    		+ " strftime('%Y-%m-%d %H:%M:%S', datetime(((TSActive/10000000) - 11644473600), 'unixepoch')) as tsact_order,"
    		+ " iFNULL(strftime('%d-%m-%Y %H:%M:%S', datetime(((TSActive/10000000) - 11644473600), 'unixepoch')), \"\") as TSActive,\r\n"
    		+ " iFNULL(strftime('%d-%m-%Y %H:%M:%S', datetime(((TSInactive/10000000) - 11644473600), 'unixepoch')), \"\") as TSInactive,  \r\n"
    		+ " iFNULL((select alarm_name from AlarmName an where an.id_alarm = AlarmId and an.id_alarm_group = GroupId), \"\") as NameAlarm,\r\n"
    		+ " iFNULL((select name_group from AlarmGroup ag where ag.id_group = GroupId), \"\") as NameGroup, GroupId, AlarmId from TblAlarm ) as alarms\r\n"
    		+ " where alarms.tsact_order between :dateSt and :dateEn";
        
    public QueryAlarmsAll(ReportUtils reportUtils) {
		super();
		this.reportUtils = reportUtils;
	}

	/**
     * The query on tables TblAlarm + NameAlarm + AlarmGroup
     * @param dateStart
     * @param dateEnd
     * @return List<TblAlarmDTO>
     */
	public List<TblAlarmDTO> getTblAlarmDTO(String dateStart, String dateEnd){
		listTblAlarmDTO.clear();
		
		StringBuilder dateSt = reportUtils.ReversDate(new StringBuilder(dateStart));
		dateSt.append(" 00:00:00");
		StringBuilder dateEn = reportUtils.ReversDate(new StringBuilder(dateEnd));
		dateEn.append(" 23:59:59");
		
		@SuppressWarnings("unchecked")
		List<Tuple> list = em.createNativeQuery(qeryString, Tuple.class)
		                     .setParameter("dateSt", dateSt.toString())
		                     .setParameter("dateEn", dateEn.toString())
		                     .getResultList();
		
		if(list.isEmpty()) {
			listTblAlarmDTO.add(new TblAlarmDTO(dateSt.toString(), dateEn.toString(), "Нет данных", "Нет данных", 0, 0));
			return listTblAlarmDTO;
		}
		
		return list.stream().map(this::convertToTblAlarmDto).collect(Collectors.toList());
	}
	
	/**
	 * Convertion to DTO object
	 * @param t
	 * @return
	 */
	private TblAlarmDTO convertToTblAlarmDto(Tuple t) {
		
	    TblAlarmDTO ta = new TblAlarmDTO(
				         (String)t.get("tsactive"),
				         (String)t.get("tsinactive")==null?"":(String)t.get("tsinactive"),
				         (String)t.get("nameAlarm"),
				         (String)t.get("nameGroup"),
				         (Integer)t.get("groupId"),
				         (Integer)t.get("alarmId")
				         );
	    return ta;
	}
}
