package eis.com.alarmservice.restcontroller;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import eis.com.alarmservice.dto.TblAlarmDTO;
import eis.com.alarmservice.service.SrvMonitorAlarm;

@RestController
public class GetDataFromAlarmName {
	
	private SrvMonitorAlarm srvMonitorAlarm;
	
    @Autowired
	public GetDataFromAlarmName(SrvMonitorAlarm srvMonitorAlarm) {
		super();
		this.srvMonitorAlarm = srvMonitorAlarm;
	}

	@GetMapping(value="/user/AlarmName/dateFrom/{dateFrom}/dateTo/{dateTo}")
	public ResponseEntity <List<TblAlarmDTO>> recordsFromAlarmName(@PathVariable("dateFrom") String dateFrom, 
			                                                       @PathVariable("dateTo")   String dateTo){
		
		return ResponseEntity.status(OK).body(srvMonitorAlarm.getQueryAlarmDto(dateFrom, dateTo));
	}

}
