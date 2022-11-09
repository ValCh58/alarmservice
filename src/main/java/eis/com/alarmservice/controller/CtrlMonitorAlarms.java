package eis.com.alarmservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CtrlMonitorAlarms {

	public CtrlMonitorAlarms() {}

	@GetMapping(value="/user/view_alarms")
	public ModelAndView getViewAlarms() {
		ModelAndView modelandview = new ModelAndView();
		
		modelandview.setViewName("/user/viewalarms");
		return modelandview;
	}
}
