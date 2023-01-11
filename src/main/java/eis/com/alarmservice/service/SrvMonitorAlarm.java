package eis.com.alarmservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eis.com.alarmservice.dto.TblAlarmDTO;
import eis.com.alarmservice.exceptions.ResourceNotFoundException;
import eis.com.alarmservice.queres.QueryAlarmsAll;
import eis.com.alarmservice.queres.QueryAlarmsRange;

@Service
public class SrvMonitorAlarm {
	private QueryAlarmsAll queryAlarmAll;
	private QueryAlarmsRange queryAlarmsRange;

	public SrvMonitorAlarm(QueryAlarmsAll queryAlarmAll, QueryAlarmsRange queryAlarmsRange) {
		super();
		this.queryAlarmsRange = queryAlarmsRange;
		this.queryAlarmAll = queryAlarmAll;
	}

	@Transactional(readOnly=true)
	public List<TblAlarmDTO> getQueryAlarmDto(String dateStart, String dateEnd){
		
		List<TblAlarmDTO> list = Optional.ofNullable(queryAlarmAll.getTblAlarmDTO(dateStart, dateEnd)).
                orElseThrow(()->new ResourceNotFoundException("Object list TblAlarmDTO Not found!"));
		
		return list;
	}
	
	
	@Transactional(readOnly=true)
	public List<TblAlarmDTO> getQueryAlarmRangeDto(String dateStart, String dateEnd, Integer idGroup,Integer idAlarm){
		
		List<TblAlarmDTO> list = Optional.ofNullable(queryAlarmsRange.getTblAlarmDTO(dateStart, dateEnd, idGroup, idAlarm)).
                                          orElseThrow(()->new ResourceNotFoundException("Object list TblAlarmDTO Not found!"));
		
		return list;
	}

}
