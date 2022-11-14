package eis.com.alarmservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eis.com.alarmservice.dto.TblAlarmDTO;
import eis.com.alarmservice.queres.QueryAlarmsAll;

@Service
public class SrvMonitorAlarm {
	private QueryAlarmsAll queryAlarmAll;

	public SrvMonitorAlarm(QueryAlarmsAll queryAlarmAll) {
		super();
		this.queryAlarmAll = queryAlarmAll;
	}

	@Transactional(readOnly=true)
	public List<TblAlarmDTO> getQueryAlarmDto(){
		
		queryAlarmAll.getTblAlarmDTO();
		
		return null;
	}

}
