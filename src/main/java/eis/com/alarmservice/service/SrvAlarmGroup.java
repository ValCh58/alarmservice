package eis.com.alarmservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eis.com.alarmservice.configuration.CnfModelMapper;
import eis.com.alarmservice.dto.AlarmGroupDTO;
import eis.com.alarmservice.dto.AlarmNameDTO;
import eis.com.alarmservice.exceptions.ResourceNotFoundException;
import eis.com.alarmservice.modeladmin.AlarmGroup;
import eis.com.alarmservice.modeladmin.AlarmName;
import eis.com.alarmservice.repository.IAlarmGroup;
import eis.com.alarmservice.repository.IAlarmName;

@Service
public class SrvAlarmGroup {
	
	private IAlarmGroup iAlarmGroup;
	private IAlarmName iAlarmName;
	private CnfModelMapper cnfModelMapper;
		
	public SrvAlarmGroup(IAlarmGroup iAlarmGroup, IAlarmName iAlarmName, CnfModelMapper cnfModelMapper) {
		super();
		this.iAlarmGroup = iAlarmGroup;
		this.iAlarmName = iAlarmName;
		this.cnfModelMapper = cnfModelMapper; 
	}
	
	/**
	 * Data from AlarmGroup table
	 * @return List<AlarmGroup>
	 */
    @Transactional(readOnly=true)
	public  List<AlarmGroup> getResAlarmGroup(){
		List<AlarmGroup> list = Optional.ofNullable(iAlarmGroup.findAll()).
				                orElseThrow(()->new ResourceNotFoundException("Object list AlarmGroup Not found!"));
		return list;
	}
    
    /**
     * Getting data from AlarmGroup table and converting it to DTO AlarmGroupDTO table
     * @return List<AlarmGroupDTO>
     */
    public List<AlarmGroupDTO> getAlarmGroupDTO(){
    	
    	List<AlarmGroup> list = getResAlarmGroup();
       	return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    /**
     * converting it to DTO 
     * @param alarmGroup
     * @return AlarmGroupDTO
     */
    private AlarmGroupDTO convertToDto(AlarmGroup alarmGroup) {
    	
    	AlarmGroupDTO alarmGroupDTO = cnfModelMapper.modelMapper().map(alarmGroup, AlarmGroupDTO.class);
    	return alarmGroupDTO;
    }
    
    
    public List<AlarmNameDTO> getAlarmName(Integer idGroup){
    	
    	List<AlarmName> list = getResAlarmName(idGroup);
    	return list.stream().map(this::convertToAlarmNameDto).collect(Collectors.toList());
    	
    }
    
    
    /**
     * converting it to DTO 
     * @param alarmName
     * @return AlarmNameDTO
     */
    private AlarmNameDTO convertToAlarmNameDto(AlarmName alarmName) {
    	
    	AlarmNameDTO alarmNameDTO = cnfModelMapper.modelMapper().map(alarmName, AlarmNameDTO.class);
    	return alarmNameDTO;
    }
    
    
    
    /**
     * Getting the alarm name from the alarm group
     * @param idGroupName
     * @return List<AlarmName>
     */
    @Transactional(readOnly=true)
    public List<AlarmName> getResAlarmName(Integer idGroupName){
    	Sort sortIdNameAlarm = Sort.by("idAlarm").ascending();
    	List<AlarmName> list = Optional.ofNullable(iAlarmName.findByIdAlarmGroup(idGroupName, sortIdNameAlarm)).
    			               orElseThrow(()->new ResourceNotFoundException("Object list AlarmName Not found!"));
    	return list;
    }
    
    @Transactional(readOnly=true)
    public AlarmGroup getRowAlarmGroup(Integer idGroup){
    	AlarmGroup ag = Optional.ofNullable(iAlarmGroup.findByIdGroup(idGroup)).
	               orElseThrow(()->new ResourceNotFoundException("Object AlarmGroup Not found!"));
    	return ag;
    }
    
    @Transactional(readOnly=true)
    public AlarmName getRowAlarmName(Integer idAlarm) {
    	
    	AlarmName an = Optional.ofNullable(iAlarmName.findByIdAlarmName(idAlarm)).
	               orElseThrow(()->new ResourceNotFoundException("Object AlarmName Not found!"));
    	return an;
    }
    
    @Transactional
    public AlarmGroup srvUpdAlarmGroup(AlarmGroup ag) {
    	Optional<AlarmGroup> optAg = iAlarmGroup.findById(ag.getIdAlarmGroup());
    	AlarmGroup alGroup = optAg.orElseThrow(()->new ResourceNotFoundException("Object AlarmGroup Not found"));
    	alGroup.setIdGroup(ag.getIdGroup());
    	alGroup.setNameGroup(ag.getNameGroup());
    	return Optional.ofNullable(iAlarmGroup.save(alGroup)).
    			orElseThrow(()->new ResourceNotFoundException("Error update object AlarmGroup!"));
    }
    
    @Transactional
    public AlarmGroup srvInsAlarmGroup(AlarmGroup ag) {
    	AlarmGroup alarmGroup = Optional.ofNullable(iAlarmGroup.save(ag)).
    			orElseThrow(()->new ResourceNotFoundException("Error saving object AlarmGroup!"));
    	return alarmGroup;
    }
    
    @Transactional
    public boolean deleteRowAlarmGroup(Integer idAlarmGroup) {
    	if(idAlarmGroup == null) { 
    		return false;
    	}	
    	iAlarmGroup.deleteById(idAlarmGroup);
    	return true;
    }
    
    @Transactional
    public boolean checkAlarmNameEntity(Integer idAlarmGroup) {
         return iAlarmName.existsByIdAlarmGroup(idAlarmGroup);
    }
    
    @Transactional
    public AlarmName srvUpdAlarmName(AlarmName an) {
    	Optional<AlarmName> optAn = iAlarmName.findById(an.getIdAlarmName());
    	AlarmName alName = optAn.orElseThrow(()->new ResourceNotFoundException("Object AlarmName Not found"));
    	alName.setIdAlarm(an.getIdAlarm());
    	alName.setAlName(an.getAlName());
    	return Optional.ofNullable(iAlarmName.save(alName)).
    			orElseThrow(()->new ResourceNotFoundException("Error update object AlarmName!"));
    }
    
    @Transactional
    public AlarmName srvInsAlarmName(AlarmName an) {
           AlarmName alarmName = Optional.ofNullable(iAlarmName.save(an)).
    			orElseThrow(()->new ResourceNotFoundException("Error saving object AlarmName!"));
    	return alarmName;
    }
    
    @Transactional
    public boolean deleteRowAlarmName(Integer idGroupName) {
    	if(idGroupName == null) { 
    		return false;
    	}	
    	iAlarmName.deleteById(idGroupName);
    	return true;
    }

}
