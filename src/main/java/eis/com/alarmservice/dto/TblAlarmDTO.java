package eis.com.alarmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TblAlarmDTO {

	private String tsactive;
	private String tsinactive;
	private String nameAlarm;
	private String nameGroup;
	private Integer groupId;
	private Integer alarmId;
}
