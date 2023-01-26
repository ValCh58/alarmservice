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
import eis.com.alarmservice.utility.ReportUtils;

@Component
public class QueryAlarmsRange {
	
	//Component
	private ReportUtils reportUtils;
		
	public QueryAlarmsRange(ReportUtils reportUtils) {
		super();
		this.reportUtils = reportUtils;
	}

	@PersistenceContext
	private EntityManager em;
	private List<TblAlarmDTO> listTblAlarmDTO = new ArrayList<>();
	
	/**
	 * Выполнение native SQL запроса
	 * 
	 * @param dateStart формат dd-mm-YYYYY
	 * @param dateEnd   формат dd-mm-YYYYY
	 * @param idGroup   номер группы
	 * @param idAlarm   номер аварии
	 * @return List<TblAlarmDTO> data of query
	 */
    public List<TblAlarmDTO> getTblAlarmDTO(String dateStart, String dateEnd, Integer idGroup, Integer idAlarm){
		listTblAlarmDTO.clear();
		String strSql = prepearQuery(idGroup, idAlarm);
		StringBuilder dateSt = reportUtils.ReversDate(new StringBuilder(dateStart));
		dateSt.append(" 00:00:00");
		StringBuilder dateEn = reportUtils.ReversDate(new StringBuilder(dateEnd));
		dateEn.append(" 23:59:59");
		
		Query query = em.createNativeQuery(strSql, Tuple.class)
				        .setParameter("dateSt", dateSt.toString())  //date format YYYY-mm-dd
				        .setParameter("dateEn", dateEn.toString()); //date format YYYY-mm-dd
		if(idGroup != NoAlrm.NO_ID_GROUP.getCod()/*99999*/) {
		   query.setParameter("idGroup", idGroup);
		}
		if(idAlarm != NoAlrm.NO_ID_ALARM.getCod()/*99998*/) {
		   query.setParameter("idAlarm", idAlarm);
		}
		
		@SuppressWarnings("unchecked")
		List<Tuple> list = query.getResultList();
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
	    	
	/**
	 * Prepared native SQL query    
	 * @param idGroup номер группы
	 * @param idAlarm номер аварии
	 * @return
	 */
    private String prepearQuery(Integer idGroup, Integer idAlarm) {
    	
    	StringBuilder queryStringBuilder = new StringBuilder(
	              " select alarms.tsactive, "
	            + " iiF(tsinactive=\"01-01-1601 00:00:00\", \"\", tsinactive) as tsinactive, "
	            + " alarms.nameAlarm, "
	            + " alarms.nameGroup, "
	            + " alarms.groupId, "
	            + " alarms.alarmId "
	            + " from ( select "
	            + " strftime('%Y-%m-%d %H:%M:%S', datetime(((TSActive/10000000) - 11644473600), 'unixepoch')) as tsact_order,"
	            + " iFNULL(strftime('%d-%m-%Y %H:%M:%S', datetime(((TSActive/10000000) - 11644473600), 'unixepoch')), \"\") as TSActive, "
	    		+ " iFNULL(strftime('%d-%m-%Y %H:%M:%S', datetime(((TSInactive/10000000) - 11644473600), 'unixepoch')), \"\") as TSInactive, "
	    		+ " iFNULL((select alarm_name from AlarmName an where an.id_alarm = AlarmId and an.id_alarm_group = GroupId), \"\") as NameAlarm, "
	    		+ " iFNULL((select name_group from AlarmGroup ag where ag.id_group = GroupId), \"\") as NameGroup, GroupId, AlarmId "
	    		+ " from TblAlarm ) as alarms "
	    		+ " where (alarms.tsact_order between :dateSt and :dateEn)");
    	
    	if (idGroup != NoAlrm.NO_ID_GROUP.getCod()) {
    		queryStringBuilder.append("and(alarms.groupId=:idGroup)").toString();
		}
    	if (idAlarm != NoAlrm.NO_ID_ALARM.getCod()) {
			queryStringBuilder.append("and(alarms.alarmId=:idAlarm)").toString();
		}
    	
    	return queryStringBuilder.toString();
    }
    
    
    
    
}
