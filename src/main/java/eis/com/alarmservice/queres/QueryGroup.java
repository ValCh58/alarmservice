package eis.com.alarmservice.queres;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;

import org.springframework.stereotype.Component;

import eis.com.alarmservice.dto.DiagGroupDTO;
import eis.com.alarmservice.utility.ReportUtils;


@Component
public class QueryGroup {

	//Component
	private ReportUtils reportUtils;
		
	public QueryGroup(ReportUtils reportUtils) {
	    super();
	    this.reportUtils = reportUtils;
	}
	
	private StringBuilder sqlString = new StringBuilder(
			"select count(group_tsact.groupId) as countRow, group_tsact.nameGroup, group_tsact.groupId \r\n"
			+ " from"
			+ " (select strftime('%Y-%m-%d %H:%M:%S', datetime(((TSActive/10000000) - 11644473600), 'unixepoch')) as tsact_order,\r\n"
			+ " iFNULL((select name_group from AlarmGroup ag where ag.id_group = GroupId), \"\") as nameGroup, \r\n"
			+ " groupId \r\n"
			+ " from TblAlarm \r\n"
			+ " where (tsact_order between  :dateSt and :dateEn) \r\n"
			+ " ) as group_tsact\r\n"
			+ " group by group_tsact.NameGroup");
			
	
	@PersistenceContext
	private EntityManager em;
	private List<DiagGroupDTO> listTblGroupDTO = new ArrayList<>();
	
	/**
	 * Выполнение native SQL запроса
	 * @param startDate формат dd-mm-YYYYY
	 * @param finDate   формат dd-mm-YYYYY
	 * @return List<TblAlarmGroupDTO> data of query
	 */
	public List<DiagGroupDTO> getTblGroupDTO(String dateStart, String dateEnd){
		StringBuilder dateSt = null;
		StringBuilder dateEn = null;
		
		listTblGroupDTO.clear();
		if(!isRevers(dateStart)) {
		   dateSt = reportUtils.ReversDate(new StringBuilder(dateStart));//Format YYYYY-mm-dd for sorting
		}else {
			dateSt = new StringBuilder(dateStart);
		}
		dateSt.append(" 00:00:00");
		
		if(!isRevers(dateEnd)) {
		   dateEn = reportUtils.ReversDate(new StringBuilder(dateEnd));//Format YYYYY-mm-dd for sorting
		}else {
			dateEn = new StringBuilder(dateEnd);	
		}
		dateEn.append(" 23:59:59");
		
		@SuppressWarnings("unchecked")
		List<Tuple> list = em.createNativeQuery(sqlString.toString(), Tuple.class)
		                     .setParameter("dateSt", dateSt.toString())
		                     .setParameter("dateEn", dateEn.toString())
		                     .getResultList();
		
		if(list.isEmpty()) {
			listTblGroupDTO.add(new DiagGroupDTO(0, "NoData", 0));
			return listTblGroupDTO;
		}
		return list.stream().map(this::convertToTblGroupDto).collect(Collectors.toList());
	}
	
	/**
	 * Convertion to DTO object
	 * @param t
	 * @return
	 */
	private DiagGroupDTO convertToTblGroupDto(Tuple t) {
		
	    DiagGroupDTO ta = new DiagGroupDTO(
	    		              (Integer)t.get("countRow"),
	    		              (String)t.get("nameGroup")==null?"":(String)t.get("nameGroup"),
				              (Integer)t.get("groupId")
				              );
	return ta;
	}
	
	/**
	 * Check date for reverse
	 * @retun true is not reverse 
	 */
	private boolean isRevers(String str) {
		return str.charAt(4) == '-';
	}

}
