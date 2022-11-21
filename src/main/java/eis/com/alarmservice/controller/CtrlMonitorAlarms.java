package eis.com.alarmservice.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import eis.com.alarmservice.dto.TblAlarmDTO;
import eis.com.alarmservice.service.SrvMonitorAlarm;

@Controller
public class CtrlMonitorAlarms {

	private SrvMonitorAlarm srvMonitorAlarm;

	public CtrlMonitorAlarms(SrvMonitorAlarm srvMonitorAlarm) {
		super();
		this.srvMonitorAlarm = srvMonitorAlarm;
	}

	/**
	 * Create table id="table-alarm-monitor"
	 * @return ModelAndView
	 */
	@GetMapping(value="/user/view_alarms")
	public ModelAndView getViewAlarms() {
		//Current Dates by default////////////////////////////////////////////// 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dateStartCurr = LocalDate.now();
		LocalDate dateEndCurr = LocalDate.now();
		
		ModelAndView modelandview = new ModelAndView();
		List<TblAlarmDTO> listTblAlarmDTO = srvMonitorAlarm.getQueryAlarmDto(dateStartCurr.format(formatter),
				                                                               dateEndCurr.format(formatter));
		modelandview.addObject("listTblAlarmDTO", listTblAlarmDTO);
		modelandview.setViewName("user/viewalarms");
		return modelandview;
	}
	
	/**
	 * Modal window for upload of data file
	 * 
	 */
	@GetMapping(value="/user/modalUpload")
	public ModelAndView uploadDataFromFile() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user/upload_data");
		return modelAndView;
	}
}
