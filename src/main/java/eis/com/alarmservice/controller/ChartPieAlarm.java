package eis.com.alarmservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChartPieAlarm {

	public ChartPieAlarm() {}
	
	@GetMapping(value="/user/chart_pie_alarms")
	public ModelAndView getViewChart() {
		ModelAndView modelandview = new ModelAndView();
		
		
		modelandview.setViewName("user/chart");
		return modelandview;
	}

}
