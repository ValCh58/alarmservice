package eis.com.alarmservice.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import eis.com.alarmservice.dto.AlarmGroupDTO;
import eis.com.alarmservice.dto.TblAlarmDTO;
import eis.com.alarmservice.service.SrvAlarmGroup;
import eis.com.alarmservice.service.SrvMonitorAlarm;

@Controller
public class CtrlMonitorAlarms {

	private SrvMonitorAlarm srvMonitorAlarm;
    private SrvAlarmGroup srvAlarmGroup;
	
	public CtrlMonitorAlarms(SrvMonitorAlarm srvMonitorAlarm, SrvAlarmGroup srvAlarmGroup) {
		super();
		this.srvMonitorAlarm = srvMonitorAlarm;
		this.srvAlarmGroup = srvAlarmGroup;
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
	 * @return ModelAndView
	 */
	@GetMapping(value="/user/modalUpload")
	public ModelAndView uploadDataFromFile() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user/upload_data");
		return modelAndView;
	}
	
	/**
	 * Create table id="table-report-alarm"
	 * @return ModelAndView
	 */
	@GetMapping(value="/user/reportalarms")
	public ModelAndView getViewReportAlarms() {
		//Current Dates by default////////////////////////////////////////////// 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dateStartCurr = LocalDate.now();
		LocalDate dateEndCurr = LocalDate.now();
		
		ModelAndView modelandview = new ModelAndView();
		List<AlarmGroupDTO> listAlarmGroupDTO = srvAlarmGroup.getAlarmGroupDTO();
		List<TblAlarmDTO> listTblAlarmDTO = srvMonitorAlarm.getQueryAlarmDto(dateStartCurr.format(formatter),
				                                                               dateEndCurr.format(formatter));
		//Filling out the list of groupNames. id="listGroupName"//
		modelandview.addObject("listTblAlarmDTO", listTblAlarmDTO);
		modelandview.addObject("listAlarmGroupDTO", listAlarmGroupDTO);
		modelandview.setViewName("user/reportalarms");
		return modelandview;
	}
	
	 
	
}
