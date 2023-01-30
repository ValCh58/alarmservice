package eis.com.alarmservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmGroupDTO {
	
	private int idAlarmGroup;
	private int idGroup;
	private String nameGroup;
}
