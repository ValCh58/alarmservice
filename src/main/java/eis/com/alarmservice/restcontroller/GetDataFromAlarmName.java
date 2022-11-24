package eis.com.alarmservice.restcontroller;

import static org.springframework.http.HttpStatus.OK;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import eis.com.alarmservice.dto.TblAlarmDTO;
import eis.com.alarmservice.service.SrvMonitorAlarm;

@RestController
public class GetDataFromAlarmName {
	private Environment env;
	private SrvMonitorAlarm srvMonitorAlarm;
	
    @Autowired
	public GetDataFromAlarmName(SrvMonitorAlarm srvMonitorAlarm, Environment env) {
		super();
		this.srvMonitorAlarm = srvMonitorAlarm;
		this.env = env;
	}

	@GetMapping(value="/user/AlarmName/dateFrom/{dateFrom}/dateTo/{dateTo}")
	public ResponseEntity <List<TblAlarmDTO>> recordsFromAlarmName(@PathVariable("dateFrom") String dateFrom, 
			                                                       @PathVariable("dateTo")   String dateTo){
		
		return ResponseEntity.status(OK).body(srvMonitorAlarm.getQueryAlarmDto(dateFrom, dateTo));
	}
	
	@PostMapping("/user/upload_data") 
	  public ResponseEntity<?> handleFileUpload( @RequestParam("file") MultipartFile file) {
		  String fileName = file.getOriginalFilename();
		  String envDir = env.getProperty("path.upload.files");
		  File uploadDir = new File(envDir);
		  
		  try {
			if(!uploadDir.exists()) {uploadDir.mkdir();}  
			file.transferTo( new File(envDir+fileName));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
		return ResponseEntity.ok("File uploaded successfully.");
		  
	  }

}
