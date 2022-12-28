package eis.com.alarmservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import eis.com.alarmservice.modeladmin.AlarmGroup;

public interface IAlarmGroup extends JpaRepository<AlarmGroup, Integer> {
	
	public AlarmGroup findByIdGroup(Integer idGroup);
	
	public List<AlarmGroup> findAll(); 

}
