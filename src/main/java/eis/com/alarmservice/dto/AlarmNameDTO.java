package eis.com.alarmservice.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class AlarmNameDTO {

	public AlarmNameDTO() {
		super();
	}
	 private int idAlarm;
	 private String alName;

}
