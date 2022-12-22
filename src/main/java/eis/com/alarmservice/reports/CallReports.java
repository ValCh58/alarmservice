package eis.com.alarmservice.reports;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallReports {

	public CallReports() {}
	
	@GetMapping(value = "/user/alarm_report_pdf/dateFrom/{dateFrom}/dateTo/{dateTo}")
	public ResponseEntity<String> makeReportAlarms(@PathVariable("dateFrom") String dateFrom,
			                                       @PathVariable("dateTo") String dateTo){
		
		String url = null;
		
		return ResponseEntity.status(OK).body(url);
	}

}
