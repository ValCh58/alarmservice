package eis.com.alarmservice.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eis.com.alarmservice.modeladmin.AlarmName;

@Repository
public interface IAlarmName extends JpaRepository<AlarmName, Integer> {
	
	public AlarmName findByIdAlarmName(Integer idAlarm);

	public List<AlarmName> findByIdAlarmGroup(Integer idGroupName, Sort sort);
	
	public boolean existsByIdAlarmGroup(Integer idAlarmGroup);
}
