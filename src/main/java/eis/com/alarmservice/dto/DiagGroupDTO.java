package eis.com.alarmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Class dto for Диаграмма по группам аварий
 * 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagGroupDTO {

	private Integer countRow;
	private String  nameGroup;
	private Integer groupId;

}
