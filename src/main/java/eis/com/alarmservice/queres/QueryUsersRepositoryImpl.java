package eis.com.alarmservice.queres;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;

import org.springframework.stereotype.Component;

import eis.com.alarmservice.dto.UsersRolesDTO;
import eis.com.alarmservice.repository.QueryUsersRepository;

@Component
public class QueryUsersRepositoryImpl implements QueryUsersRepository{
	
	public QueryUsersRepositoryImpl() {}

	@PersistenceContext
    private EntityManager em;
	private List<UsersRolesDTO> usersrolesdto = new ArrayList<>(); 
	private String qeryString = "SELECT users.user_id, users.last_name, "
                              + "users.name, user_name, users.active, "
         	                  + "users.email, roles.role FROM users, user_role, roles "
                              + "where users.user_id = user_role.user_id "
                              + "and user_role.role_id = roles.role_id order by users.last_name";
	
	/**
     * The query on tables users + user_role + roles
     * @param dateStart
     * @param dateEnd
     * @return List<TblAlarmDTO>
     */
	public List<UsersRolesDTO> queryUsersRoles(){
		usersrolesdto.clear();
		
		@SuppressWarnings("unchecked")
		List<Tuple> list = em.createNativeQuery(qeryString, Tuple.class).getResultList();
		
		if(list.isEmpty()) {
			usersrolesdto.add(new UsersRolesDTO());
			return usersrolesdto;
		}
		
		return list.stream().map(this::convertToUsersRolesDTO).collect(Collectors.toList());
	}
	
	/**
	 * Convertion to DTO object
	 * @param t
	 * @return
	 */
	private UsersRolesDTO convertToUsersRolesDTO(Tuple t) {
		
		UsersRolesDTO usersRolesDTO = new UsersRolesDTO((Integer) t.get("user_id"),
                                                        (String) t.get("last_name"),
                                                        (String) t.get("name"),
                                                        (String) t.get("user_name"),
                                                        (Boolean) t.get("active"),
                                                        (String) t.get("email"),
                                                        (String) t.get("role"));
		return usersRolesDTO;
	}
	
}
