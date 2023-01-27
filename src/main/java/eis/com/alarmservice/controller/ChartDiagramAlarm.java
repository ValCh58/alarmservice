package eis.com.alarmservice.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import eis.com.alarmservice.dto.TblAlarmGroupDTO;
import eis.com.alarmservice.service.SrvMonitorAlarm;

@Controller
public class ChartDiagramAlarm {
	
	//Component
	private SrvMonitorAlarm srvMonitorAlarm;

	public ChartDiagramAlarm(SrvMonitorAlarm srvMonitorAlarm) {
		super();
		this.srvMonitorAlarm = srvMonitorAlarm;
	}

		
	@GetMapping(value="/user/chart_group_alarms")
	public ModelAndView getViewChart() {
		ModelAndView modelandview = new ModelAndView();
		List<TblAlarmGroupDTO> list = srvMonitorAlarm.getQueryAlarmGroupDto("01-07-2022", "05-07-2022", 5);
		modelandview.addObject("list", list);
		modelandview.setViewName("user/chartgroup");
		return modelandview;
	}

}
