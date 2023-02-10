package eis.com.alarmservice.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import eis.com.alarmservice.dto.AlarmGroupDTO;
import eis.com.alarmservice.dto.DiagAlarmGroupDTO;
import eis.com.alarmservice.dto.DiagGroupDTO;
import eis.com.alarmservice.service.SrvAlarmGroup;
import eis.com.alarmservice.service.SrvMonitorAlarm;

@Controller
public class ChartDiagramAlarm {
	
	//Component
	private SrvMonitorAlarm srvMonitorAlarm;
	private SrvAlarmGroup srvAlarmGroup;

	public ChartDiagramAlarm(SrvMonitorAlarm srvMonitorAlarm, SrvAlarmGroup srvAlarmGroup) {
		super();
		this.srvMonitorAlarm = srvMonitorAlarm;
		this.srvAlarmGroup = srvAlarmGroup;
	}

	/**
	 * Диаграмма  по авариям в разрезе групп
	 * 
	 * @return ModelAndView "user/chartgroup"
	 */
	@GetMapping(value="/user/chart_group_alarms")
	public ModelAndView getViewChartGroupAlarms() {
		var currentDate = LocalDateTime.now().toLocalDate().toString();
		
		ModelAndView modelandview = new ModelAndView();
		List<AlarmGroupDTO> listAlarmGroupDTO = srvAlarmGroup.getAlarmGroupDTO();
		List<DiagAlarmGroupDTO> listAlarmGroup = srvMonitorAlarm.getQueryAlarmGroupDto(currentDate, currentDate, 99999);
		modelandview.addObject("listAlarmGroupDTO", listAlarmGroupDTO);
		modelandview.addObject("listAlarmGroup", listAlarmGroup);
		modelandview.setViewName("user/chartalarmgroup");
		return modelandview;
	}
	
	/**
	 * Диаграмма по группам аварий
	 * 
	 * @return ModelAndView "user/chartgroup"
	 */
	@GetMapping(value="/user/chart_groups")
	public ModelAndView getViewChartGroup() {
		var currentDate = LocalDateTime.now().toLocalDate().toString();
		
		ModelAndView modelandview = new ModelAndView();
		List<DiagGroupDTO> listGroupDiagram = srvMonitorAlarm.getQueryGroupDto(currentDate, currentDate);
		modelandview.addObject("listGroupDiagram", listGroupDiagram);
		modelandview.setViewName("user/chartgroup");
		return modelandview;
	}

}
