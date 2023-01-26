package eis.com.alarmservice.queres;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import eis.com.alarmservice.dto.TblAlarmGroupDTO;
import eis.com.alarmservice.utility.ReportUtils;


@Component
public class QueryAlarmsGroup {

	//Component
	private ReportUtils reportUtils;
		
	public QueryAlarmsGroup(ReportUtils reportUtils) {
	    super();
	    this.reportUtils = reportUtils;
	}
	
	private StringBuilder sqlString = new StringBuilder(
			"select count(group_tsact.tsact_order) as countRow, group_tsact.nameAlarm, group_tsact.nameGroup, group_tsact.groupId \r\n"
			+ " from(select strftime('%Y-%m-%d %H:%M:%S', datetime(((TSActive/10000000) - 11644473600), 'unixepoch')) as tsact_order,\r\n"
			+ "             iFNULL((select alarm_name from AlarmName an where an.id_alarm = AlarmId and an.id_alarm_group = GroupId), \"\") as nameAlarm,\r\n"
			+ "             iFNULL((select name_group from AlarmGroup ag where ag.id_group = GroupId), \"\") as nameGroup, \r\n"
			+ "             groupId \r\n"
			+ "        from TblAlarm \r\n"
			+ "        where (tsact_order between : startDate and : finDate) \r\n"
			+ "        ) as group_tsact\r\n"
			+ " group by group_tsact.NameAlarm, group_tsact.NameGroup\r\n"
			+ " having  group_tsact.GroupId = :idGroup");
	
	@PersistenceContext
	private EntityManager em;
	private List<TblAlarmGroupDTO> listTblAlarmGroupDTO = new ArrayList<>();
	
	/**
	 * Выполнение native SQL запроса
	 * 
	 * @param startDate формат dd-mm-YYYYY
	 * @param finDate   формат dd-mm-YYYYY
	 * @param idGroup   номер группы
	 * @return List<TblAlarmGroupDTO> data of query
	 */

}
