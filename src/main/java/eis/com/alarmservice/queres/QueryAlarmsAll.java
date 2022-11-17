package eis.com.alarmservice.queres;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;

import org.springframework.stereotype.Component;

import eis.com.alarmservice.dto.TblAlarmDTO;

@Component
public class QueryAlarmsAll {
	
	@PersistenceContext
	private EntityManager em;
	private List<TblAlarmDTO> listTblAlarmDTO = new ArrayList<>();
	
    private String qeryString = 
    		"select alarms.TSActive, alarms.TSInactive, alarms.NameAlarm, alarms.NameGroup, alarms.GroupId, alarms.AlarmId from( "
    			+ " select iFNULL(datetime(((TSActive/10000000) - 11644473600), 'unixepoch'), \"\") as TSActive, "
    			+ " iFNULL(datetime(((TSInactive/10000000) - 11644473600), 'unixepoch'), \"\") as TSInactive, "
    			+ " iFNULL((select alarm_name from AlarmName an where an.id_alarm = AlarmId and an.id_alarm_group = GroupId), \"\") as NameAlarm, "
    			+ " iFNULL((select name_group from AlarmGroup ag where ag.id_group = GroupId), \"\") as NameGroup, "
    			+ " GroupId, AlarmId " 
    			+ "	from TblAlarm ) as alarms " 
    			+ " where alarms.TSActive between :dateSt and :dateEn";
	
	
	public List<TblAlarmDTO> getTblAlarmDTO(String dateStart, String dateEnd){
		listTblAlarmDTO.clear();
		
		StringBuilder dateSt = new StringBuilder(dateStart);
		dateSt.append(" 00:00:00");
		StringBuilder dateEn = new StringBuilder(dateEnd);
		dateEn.append(" 23:59:59");
		
		@SuppressWarnings("unchecked")
		List<Tuple> list = em.createNativeQuery(qeryString, Tuple.class)
		                     .setParameter("dateSt", dateSt.toString())
		                     .setParameter("dateEn", dateEn.toString())
		                     .getResultList();
		
		
		if(list.isEmpty()) {
			listTblAlarmDTO.add(
				new TblAlarmDTO(dateSt.toString(),
						        dateEn.toString(),
					            "Нет данных",
					            "Нет данных",
					            0,
					            0
					           )	
					);
			return listTblAlarmDTO;}
		
		for (Tuple t : list) {
			TblAlarmDTO ta = new TblAlarmDTO(
					         (String)t.get("TSActive"),
					         (String)t.get("TSInactive")==null?"":(String)t.get("TSInactive"),
					         (String)t.get("NameAlarm"),
					         (String)t.get("NameGroup"),
					         (Integer)t.get("GroupId"),
					         (Integer)t.get("AlarmId")
					         );
			listTblAlarmDTO.add(ta);
		}
		
		
		
		return listTblAlarmDTO;
	}

	
}
