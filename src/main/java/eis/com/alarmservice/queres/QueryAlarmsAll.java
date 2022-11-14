package eis.com.alarmservice.queres;

import java.time.LocalDateTime;
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
    		"select TSActive, TSInactive, name_alarm, name_group, GroupId, AlarmId from( "
    			+ " select iFNULL(datetime(((TSActive/10000000) - 11644473600), 'unixepoch'), \"\") as TSActive, "
    			+ " iFNULL(datetime(((TSInactive/10000000) - 11644473600), 'unixepoch'), \"\") as TSInactive, "
    			+ " iFNULL((select alarm_name from AlarmName an where an.id_alarm = AlarmId and an.id_alarm_group = GroupId), \"\") as name_alarm, "
    			+ " iFNULL((select name_group from AlarmGroup ag where ag.id_group = GroupId), \"\") as name_group, "
    			+ " GroupId, AlarmId " 
    			+ "	from TblAlarm ) as alarms " 
    			+ " where alarms.TSActive between '2022-07-05 00:00:00' and '2022-07-05 23:59:59'";
	
	
	public List<TblAlarmDTO> getTblAlarmDTO(){
		listTblAlarmDTO.clear();
		
		@SuppressWarnings("unchecked")
		List<Tuple> list = em.createNativeQuery(qeryString, Tuple.class).getResultList();
		
		
		
		
		return listTblAlarmDTO;
	}

	
}
