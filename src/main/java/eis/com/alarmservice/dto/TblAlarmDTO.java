package eis.com.alarmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class TblAlarmDTO {

	private String TSActive;
	private String TSInactive;
	private String name_alarm;
	private String name_group;
	private Integer GroupId;
	private Integer AlarmId;
}
