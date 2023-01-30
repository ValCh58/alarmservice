package eis.com.alarmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Class dto for Диаграмма  по авариям в группе
 * 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagAlarmGroupDTO {

	private Integer countRow;
	private String  nameAlarm;
	private String  nameGroup;
	private Integer groupId;
	
}
