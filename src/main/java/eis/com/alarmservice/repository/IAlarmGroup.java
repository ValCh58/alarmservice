package eis.com.alarmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eis.com.alarmservice.modeladmin.AlarmGroup;

public interface IAlarmGroup extends JpaRepository<AlarmGroup, Integer> {
	
	public AlarmGroup findByIdGroup(Integer idGroup);

}
