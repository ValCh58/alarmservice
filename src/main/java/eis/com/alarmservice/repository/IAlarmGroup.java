package eis.com.alarmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eis.com.alarmservice.modeladmin.AlarmGroup;

@Repository
public interface IAlarmGroup extends JpaRepository<AlarmGroup, Integer> {
	
	public AlarmGroup findByIdGroup(Integer idGroup);

}
