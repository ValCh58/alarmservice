package eis.com.alarmservice.queres;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.stereotype.Component;

import eis.com.alarmservice.dto.TblAlarmDTO;

@Component
public class QueryAlarmsRange {
	
	
	
	public QueryAlarmsRange() {
		super();
	}



	@PersistenceContext
	private EntityManager em;
	private List<TblAlarmDTO> listTblAlarmDTO = new ArrayList<>();
	    
    public List<TblAlarmDTO> getTblAlarmDTO(String dateStart, String dateEnd, Integer idGroup, Integer idAlarm){
		listTblAlarmDTO.clear();
		String strSql = prepearQuery(idGroup, idAlarm);
		StringBuilder dateSt = new StringBuilder(dateStart);
		dateSt.append(" 00:00:00");
		StringBuilder dateEn = new StringBuilder(dateEnd);
		dateEn.append(" 23:59:59");
		
		Query query = em.createNativeQuery(strSql, Tuple.class).setParameter("dateSt", dateSt.toString()).setParameter("dateEn", dateEn.toString());
		if(idGroup != 99999) {
		   query.setParameter("idGroup", idGroup);
		}
		if(idAlarm != 99998) {
		   query.setParameter("idAlarm", idAlarm);
		}
		
		@SuppressWarnings("unchecked")
		List<Tuple> list = query.getResultList();
		
		if(list.isEmpty()) {
		   listTblAlarmDTO.add(new TblAlarmDTO(dateSt.toString(), dateEn.toString(), "Нет данных", "Нет данных", 0, 0));
		   return listTblAlarmDTO;
		}
		
		List<TblAlarmDTO> listDto = list.stream().map(this::convertToTblAlarmDto).collect(Collectors.toList());
	
		return listDto;
    }
    
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
	
    	
	    
    private String prepearQuery(Integer idGroup, Integer idAlarm) {
    	
    	StringBuilder queryStringBuilder = new StringBuilder(
	              "select alarms.tsactive, iiF(tsinactive=\"01-01-1601 00:00:00\", \"\", tsinactive) as tsinactive, alarms.nameAlarm, alarms.nameGroup, alarms.groupId, alarms.alarmId\r\n"
	            + " from(select iFNULL(strftime('%d-%m-%Y %H:%M:%S', datetime(((TSActive/10000000) - 11644473600), 'unixepoch')), \"\") as TSActive,\r\n"
	    		+ " iFNULL(strftime('%d-%m-%Y %H:%M:%S', datetime(((TSInactive/10000000) - 11644473600), 'unixepoch')), \"\") as TSInactive,\r\n"
	    		+ " iFNULL((select alarm_name from AlarmName an where an.id_alarm = AlarmId and an.id_alarm_group = GroupId), \"\") as NameAlarm,\r\n"
	    		+ " iFNULL((select name_group from AlarmGroup ag where ag.id_group = GroupId), \"\") as NameGroup, GroupId, AlarmId from TblAlarm ) as alarms\r\n"
	    		+ " where (alarms.TSActive between :dateSt and :dateEn)");
    	
    	if (idGroup != 99999) {
    		queryStringBuilder.append("and(alarms.groupId=:idGroup)").toString();
			
		}
    	if (idAlarm != 99998) {
			queryStringBuilder.append("and(alarms.alarmId=:idAlarm)").toString();
		}
    	
    	return queryStringBuilder.toString();
    }
    
    
    
    
}
