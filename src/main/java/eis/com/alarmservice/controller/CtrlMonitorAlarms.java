package eis.com.alarmservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import eis.com.alarmservice.service.SrvMonitorAlarm;

@Controller
public class CtrlMonitorAlarms {

	private SrvMonitorAlarm srvMonitorAlarm;

	public CtrlMonitorAlarms(SrvMonitorAlarm srvMonitorAlarm) {
		super();
		this.srvMonitorAlarm = srvMonitorAlarm;
	}

	@GetMapping(value="/user/view_alarms")
	public ModelAndView getViewAlarms() {
		ModelAndView modelandview = new ModelAndView();
		srvMonitorAlarm.getQueryAlarmDto();
		modelandview.setViewName("/user/viewalarms");
		return modelandview;
	}
}
