package eis.com.alarmservice.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import eis.com.alarmservice.dto.DiagAlarmGroupDTO;
import eis.com.alarmservice.dto.DiagGroupDTO;
import eis.com.alarmservice.service.SrvMonitorAlarm;

@Controller
public class ChartDiagramAlarm {
	
	//Component
	private SrvMonitorAlarm srvMonitorAlarm;

	public ChartDiagramAlarm(SrvMonitorAlarm srvMonitorAlarm) {
		super();
		this.srvMonitorAlarm = srvMonitorAlarm;
	}

	/**
	 * Диаграмма  по авариям в группе
	 * 
	 * @return
	 */
	@GetMapping(value="/user/chart_group_alarms")//
	public ModelAndView getViewChartGroupAlarms() {
		ModelAndView modelandview = new ModelAndView();
		List<DiagAlarmGroupDTO> list = srvMonitorAlarm.getQueryAlarmGroupDto("01-07-2022", "05-07-2022", 5);
		modelandview.addObject("list", list);
		modelandview.setViewName("user/chartgroup");
		return modelandview;
	}
	
	/**
	 * Диаграмма по группам аварий
	 * 
	 * @return
	 */
	@GetMapping(value="/user/chart_groups")//
	public ModelAndView getViewChartGroup() {
		ModelAndView modelandview = new ModelAndView();
		List<DiagGroupDTO> list = srvMonitorAlarm.getQueryGroupDto("01-07-2022", "05-07-2022");
		modelandview.addObject("list", list);
		modelandview.setViewName("user/chartgroup");
		return modelandview;
	}

}
