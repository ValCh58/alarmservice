package eis.com.alarmservice.restcontroller;

import static org.springframework.http.HttpStatus.OK;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import eis.com.alarmservice.dto.AlarmNameDTO;
import eis.com.alarmservice.dto.DiagAlarmGroupDTO;
import eis.com.alarmservice.dto.DiagGroupDTO;
import eis.com.alarmservice.dto.TblAlarmDTO;
import eis.com.alarmservice.service.SrvAlarmGroup;
import eis.com.alarmservice.service.SrvMonitorAlarm;
import eis.com.alarmservice.service.UploadAndInsertAlarmName;

@RestController
public class GetDataFromAlarmName {
	private Environment env;
	private SrvMonitorAlarm srvMonitorAlarm;
	private UploadAndInsertAlarmName uploadAndInsertAlarmName;
	private SrvAlarmGroup srvAlarmGroup;
	
	
    public GetDataFromAlarmName(SrvMonitorAlarm srvMonitorAlarm, Environment env, 
			                    UploadAndInsertAlarmName uploadAndInsertAlarmName, 
			                    SrvAlarmGroup srvAlarmGroup) {
		super();
		this.srvMonitorAlarm = srvMonitorAlarm;
		this.srvAlarmGroup = srvAlarmGroup;
		this.env = env;
		this.uploadAndInsertAlarmName = uploadAndInsertAlarmName;
		
	}
    
    /**
     * Filling out the list of alarmNames. id="listAlarmName"
     * @param idGroup
     * @return List<AlarmNameDTO>
     */
    @GetMapping(value="/user/GroupAlarmName/idGroup/{idGroup}")
    public ResponseEntity <List<AlarmNameDTO>> dataSelectForAlarmNameDropdown(
    		                                                         @PathVariable("idGroup") Integer idGroup){
    	
    	List<AlarmNameDTO> list = srvAlarmGroup.getAlarmName(idGroup);
   		return ResponseEntity.status(OK).body(list);
   }
    

    /**
     * result off query by dateFrom and dateTo
     * @param dateFrom
     * @param dateTo
     * @return query results
     */
	@GetMapping(value="/user/AlarmName/dateFrom/{dateFrom}/dateTo/{dateTo}")
	public ResponseEntity <List<TblAlarmDTO>> recordsFromAlarmName(@PathVariable("dateFrom") String dateFrom, 
			                                                       @PathVariable("dateTo")   String dateTo){
		
		return ResponseEntity.status(OK).body(srvMonitorAlarm.getQueryAlarmDto(dateFrom, dateTo));
	}
	
	
	/**
     * result off query by dateFrom and dateTo in filter data
     * @param dateFrom
     * @param dateTo
     * @return query results
     */
	@GetMapping(value="/user/chart_group_param/dateFrom/{dateFrom}/dateTo/{dateTo}")
	public ResponseEntity <List<DiagGroupDTO>> recordsFromAlarmGroup(@PathVariable("dateFrom") String dateFrom, 
			                                                        @PathVariable("dateTo")   String dateTo){
		
		return ResponseEntity.status(OK).body(srvMonitorAlarm.getQueryGroupDto(dateFrom, dateTo));
	}
	
	
	/**
     * result off query by dateFrom, dateTo, idGroup, idAlarm
     * @param dateFrom
     * @param dateTo
     * @param idGroup
     * @param idAlarm
     * @return query results
     */
	@GetMapping(value="/user/AlarmName/dateFrom/{dateFrom}/dateTo/{dateTo}/idGroup/{idGroup}/idAlarm/{idAlarm}")
	public ResponseEntity <List<TblAlarmDTO>> recordsFromAlarmNameRange(@PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo")   String dateTo,
			                                                             @PathVariable("idGroup") Integer idGroup, @PathVariable("idAlarm") Integer idAlarm){
		
		return ResponseEntity.status(OK).body(srvMonitorAlarm.getQueryAlarmRangeDto(dateFrom, dateTo, idGroup, idAlarm));
	}
	
	
	/**
	 * Create Diagram alarms in group
     * result off query by dateFrom, dateTo, idGroup
     * 
     * @param dateFrom
     * @param dateTo
     * @param idGroup
     * @param idAlarm
     * 
     * @return query results
     */
	@GetMapping(value="/user/chart_alarms_group_param/dateFrom/{dateFrom}/dateTo/{dateTo}/idGroup/{idGroup}")
	public ResponseEntity <List<DiagAlarmGroupDTO>> recordsFromAlarmNameRange(@PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo")   String dateTo,
			                                                            @PathVariable("idGroup") Integer idGroup){
		
		return ResponseEntity.status(OK).body(srvMonitorAlarm.getQueryAlarmGroupDto(dateFrom, dateTo, idGroup));
		//List<DiagAlarmGroupDTO> listAlarmGroup = srvMonitorAlarm.getQueryAlarmGroupDto("01-07-2022", "15-07-2022", 10);
	}
	
	
	/**
	 * To upload file from clients
	 * @param file
	 * @return file upload results
	 */
	@PostMapping(value="/user/upload_data") 
	  public ResponseEntity<?> handleFileUpload( @RequestParam("file") MultipartFile file) {
		  String fileName = file.getOriginalFilename();
		  String envDir = null;
		  String upfile = null;
		  
		  if (SystemUtils.IS_OS_WINDOWS) {envDir = env.getProperty("path.win.upload.files");}
	      else if (SystemUtils.IS_OS_LINUX) {envDir = env.getProperty("path.linux.upload.files");}
		  try {
			File uploadDir = new File(envDir);  
			if(!uploadDir.exists()) {uploadDir.mkdir();} 
			   upfile = envDir+fileName;
			file.transferTo( new File(upfile));
			uploadAndInsertAlarmName.mergeTables();
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при чтении или записи в файл CSV.");//("Errors when reading or writing a CSV file.");
		} catch(SQLException | InvalidResultSetAccessException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errors when executing a sql requests.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errors when executing.");
		}finally {
			try {
				Files.deleteIfExists(Paths.get(upfile));
			} catch (IOException e) {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при удалении файла CSV.");
			}
		}
		return ResponseEntity.ok("Файл загружен успешно."); 
	}

}
