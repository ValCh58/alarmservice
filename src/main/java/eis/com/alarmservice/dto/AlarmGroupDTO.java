package eis.com.alarmservice.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class AlarmGroupDTO {
	
	public AlarmGroupDTO() {
		super();
		
	}
	private int idAlarmGroup;
	private int idGroup;
	private String nameGroup;
}
