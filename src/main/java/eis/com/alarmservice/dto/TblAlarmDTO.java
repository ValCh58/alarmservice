package eis.com.alarmservice.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class TblAlarmDTO {

	public TblAlarmDTO() {}
	
	private LocalDateTime TSActive;
	private LocalDateTime TSInactive;
	private String name_alarm;
	private String name_group;
	private Integer GroupId;
	private Integer AlarmId;
}
