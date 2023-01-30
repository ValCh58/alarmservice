package eis.com.alarmservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eis.com.alarmservice.dto.TblAlarmDTO;
import eis.com.alarmservice.dto.DiagAlarmGroupDTO;
import eis.com.alarmservice.dto.DiagGroupDTO;
import eis.com.alarmservice.exceptions.ResourceNotFoundException;
import eis.com.alarmservice.queres.QueryAlarmsAll;
import eis.com.alarmservice.queres.QueryAlarmsGroup;
import eis.com.alarmservice.queres.QueryAlarmsRange;
import eis.com.alarmservice.queres.QueryGroup;

@Service
public class SrvMonitorAlarm {
	//Components
	private QueryGroup queryGroup;
	private QueryAlarmsAll queryAlarmAll;
	private QueryAlarmsRange queryAlarmsRange;
	private QueryAlarmsGroup queryAlarmsGroup;

	public SrvMonitorAlarm(QueryAlarmsAll queryAlarmAll, 
			               QueryAlarmsRange queryAlarmsRange, 
			               QueryAlarmsGroup queryAlarmsGroup,
			               QueryGroup queryGroup) {
		super();
		this.queryAlarmsRange = queryAlarmsRange;
		this.queryAlarmAll = queryAlarmAll;
		this.queryAlarmsGroup = queryAlarmsGroup;
		this.queryGroup = queryGroup;
	}
	
	/**
	 * Query objects 'TblAlarmDTO' from 'QueryAlarmsAll'
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<TblAlarmDTO> getQueryAlarmDto(String dateStart, String dateEnd){
		
		List<TblAlarmDTO> list = Optional.ofNullable(queryAlarmAll.getTblAlarmDTO(dateStart, dateEnd)).
                orElseThrow(()->new ResourceNotFoundException("Object list TblAlarmDTO Not found!"));
		return list;
	}
	
	/**
	 * Query objects 'TblAlarmDTO' from 'QueryAlarmsRange'
	 * @param dateStart
	 * @param dateEnd
	 * @param idGroup
	 * @param idAlarm
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<TblAlarmDTO> getQueryAlarmRangeDto(String dateStart, String dateEnd, Integer idGroup,Integer idAlarm){
		
		List<TblAlarmDTO> list = Optional.ofNullable(queryAlarmsRange.getTblAlarmDTO(dateStart, dateEnd, idGroup, idAlarm)).
                                          orElseThrow(()->new ResourceNotFoundException("Object list TblAlarmDTO Not found!"));
		return list;
	}
	
	
	/**
	 * Query objects 'DiagAlarmGroupDTO' from 'QueryAlarmsGroup'
	 * @param dateStart
	 * @param dateEnd
	 * @param idGroup
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DiagAlarmGroupDTO> getQueryAlarmGroupDto(String dateStart, String dateEnd, Integer idGroup){
		
		List<DiagAlarmGroupDTO> list = Optional.ofNullable(queryAlarmsGroup.getTblAlarmGroupDTO(dateStart, dateEnd, idGroup)).
                                          orElseThrow(()->new ResourceNotFoundException("Object list DiagAlarmGroupDTO Not found!"));
		return list;
	}
	
	/**
	 * Query objects 'DiagGroupDTO' from 'QueryGroup'
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DiagGroupDTO> getQueryGroupDto(String dateStart, String dateEnd){
		
		List<DiagGroupDTO> list = Optional.ofNullable(queryGroup.getTblGroupDTO(dateStart, dateEnd)).
                                          orElseThrow(()->new ResourceNotFoundException("Object list DiagGroupDTO Not found!"));
		return list;
	}

}
