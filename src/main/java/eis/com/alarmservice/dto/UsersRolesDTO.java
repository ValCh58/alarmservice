package eis.com.alarmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UsersRolesDTO {
	public UsersRolesDTO() {
		super();
	}
	private Integer id;
	private String last_name;
	private String name;
	private String user_name;
	private Boolean active;
	private String email;
	private String role;

}
