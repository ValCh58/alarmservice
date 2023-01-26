package eis.com.alarmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class TblAlarmGroupDTO {

	private Integer countRow;
	private String  nameAlarm;
	private String  nameGroup;
	private Integer groupId;
	
}
