package eis.com.alarmservice.restcontroller;

import static org.springframework.http.HttpStatus.OK;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import eis.com.alarmservice.dto.TblAlarmDTO;
import eis.com.alarmservice.service.SrvMonitorAlarm;
import eis.com.alarmservice.service.UploadAndInsertAlarmName;

@RestController
public class GetDataFromAlarmName {
	private Environment env;
	private SrvMonitorAlarm srvMonitorAlarm;
	private UploadAndInsertAlarmName uploadAndInsertAlarmName;
	
    @Autowired
	public GetDataFromAlarmName(SrvMonitorAlarm srvMonitorAlarm, 
			                    Environment env, 
			                    UploadAndInsertAlarmName uploadAndInsertAlarmName) {
		super();
		this.srvMonitorAlarm = srvMonitorAlarm;
		this.env = env;
		this.uploadAndInsertAlarmName = uploadAndInsertAlarmName;
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
	 * To upload file from clients
	 * @param file
	 * @return file upload results
	 */
	@PostMapping("/user/upload_data") 
	  public ResponseEntity<?> handleFileUpload( @RequestParam("file") MultipartFile file) {
		  String fileName = file.getOriginalFilename();
		  String envDir = null;
		  
		  if (SystemUtils.IS_OS_WINDOWS) {envDir = env.getProperty("path.win.upload.files");}
	      else if (SystemUtils.IS_OS_LINUX) {envDir = env.getProperty("path.linux.upload.files");}
		  try {
			File uploadDir = new File(envDir);  
			if(!uploadDir.exists()) {uploadDir.mkdir();} 
			String upfile = envDir+fileName;
			file.transferTo( new File(upfile));
			uploadAndInsertAlarmName.mergeTables();
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при чтении или записи в файл CSV.");//("Errors when reading or writing a CSV file.");
		} catch(SQLException | InvalidResultSetAccessException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errors when executing a sql requests.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errors when executing.");
		}
		return ResponseEntity.ok("Файл загружен успешно."); //("File uploaded successfully.");
	}

}
