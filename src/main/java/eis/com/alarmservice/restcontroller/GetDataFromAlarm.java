package eis.com.alarmservice.restcontroller;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import eis.com.alarmservice.modeladmin.AlarmGroup;
import eis.com.alarmservice.modeladmin.AlarmName;
import eis.com.alarmservice.service.SrvAlarmGroup;


@RestController
public class GetDataFromAlarm {

	private SrvAlarmGroup srvAlarmGroup;
	
	@Autowired
	public GetDataFromAlarm(SrvAlarmGroup srvAlarmGroup) {
		super();
		this.srvAlarmGroup = srvAlarmGroup;
	}


    /**
     * Заполнение таблицы Аварии в группе по клику на кнопку в столбце Выбрать - 'Таблица группы аварий'.
     * @param idGroup
     * @return данные для таблицы Аварии в группе
     */
	@GetMapping(value="/admin/fromAlarmName/idGroup/{idGroup}")
	public ResponseEntity <List<AlarmName>> dataFromAlarmName(@PathVariable("idGroup") Integer idGroup) {
		
		return ResponseEntity.status(OK).body(srvAlarmGroup.getResAlarmName(idGroup));
	}
	
	/**
	 * Получение записи по ID из таблицы AlarmGroup для заполнения формы modaleditgroup/modalFormEditGroup
	 * @param idGroupAlarm 
	 * @return запись из таблицы AlarmGroup
	 */
	@GetMapping(value="/admin/fromGroupAlarm/idGroupAlarm/{idGroupAlarm}")
	public ResponseEntity <AlarmGroup> recordFromAlarmGroup(@PathVariable("idGroupAlarm") Integer idGroupAlarm){
			
		return ResponseEntity.status(OK).body(srvAlarmGroup.getRowAlarmGroup(idGroupAlarm));
	}
	
	/**
	 * Получение записи по ID из таблицы AlarmName для заполнения формы modaleditnamealarm/modalFormEditAlarmName
	 * @param idNameAlarm номер аварии!!! не ID key 
	 * @return запись из таблицы AlarmName
	 */
	@GetMapping(value="/admin/rowFromAlarmName/idNameAlarm/{idNameAlarm}")
	public ResponseEntity <AlarmName> recordFromAlarmName(@PathVariable("idNameAlarm") Integer idNameAlarm){
		AlarmName an = srvAlarmGroup.getRowAlarmName(idNameAlarm);
		return ResponseEntity.status(OK).body(an);
	}
	
	/**
	 * Обновление или вставка новой записи в таблицу AlarmGroup
	 * 
	 */
	 @PostMapping(value="/admin/UpdOrInsAlarmGroup")
	 public ResponseEntity<AlarmGroup> updOrInsAlarmGroup(AlarmGroup ag){
		 AlarmGroup alarmGroup = null;
		 
		 if(ag.getIdAlarmGroup() > 0) {//update
			 alarmGroup = srvAlarmGroup.srvUpdAlarmGroup(ag);
		 }
		 else {//insert
			 alarmGroup = srvAlarmGroup.srvInsAlarmGroup(ag);
		 }
		 return ResponseEntity.status(OK).body(alarmGroup);
	 }
	 
	 /**
	  * Удаление записи из таблицы AlarmGroup idAlarmGroup 
	  * 
	  */
	 @PostMapping(value="/admin/delRowAlarmGroup/idAlarmGroup/{idAlarmGroup}/idGroup/{idGroup}")
	 public ResponseEntity<Boolean> delRowAlarmGroup(@PathVariable("idAlarmGroup") Integer idAlarmGroup,
			                                         @PathVariable("idGroup") Integer idGroup) {
		 boolean retCheck;
		 //** Проверим, есть ли подчиненные записи в таблице AlarmName		 
         retCheck = srvAlarmGroup.checkAlarmNameEntity(idGroup);
         
         return  retCheck ? ResponseEntity.status(OK).body(false) : 
        	                ResponseEntity.status(OK).body(srvAlarmGroup.deleteRowAlarmGroup(idAlarmGroup));
	 }
	 
	    /**
		 * Обновление или вставка новой записи в таблицу AlarmName
		 * 
		 */
		 @PostMapping(value="/admin/UpdOrInsAlarmName")
		 public ResponseEntity<AlarmName> updOrInsAlarmName(AlarmName an){
			 AlarmName alarmName = null;
		
			 if(an.getIdAlarmName() > 0) {//update
				alarmName = srvAlarmGroup.srvUpdAlarmName(an);
			 }
			 else {//insert
				 alarmName = srvAlarmGroup.srvInsAlarmName(an);
			 }
			 return ResponseEntity.status(OK).body(alarmName);
		 }
		 
		 /**
		  * Удаление записи из таблицы AlarmName idGroupName 
		  * 
		  */
		 @PostMapping(value="/admin/delRowAlarmName/idGroupName/{idGroupName}")
		 public ResponseEntity<Boolean> delRowAlarmName(@PathVariable("idGroupName") Integer idGroupName) {
			 boolean retCheck = srvAlarmGroup.deleteRowAlarmName(idGroupName);
	         
	         return  retCheck ? ResponseEntity.status(OK).body(true) : ResponseEntity.status(OK).body(false); 
	     }
	
}
