package eis.com.alarmservice.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import eis.com.alarmservice.modeladmin.AlarmGroup;
import eis.com.alarmservice.service.SrvAlarmGroup;

@Controller
public class EditAlarm {
	
	private SrvAlarmGroup srvAlarmGroup;
		
	public EditAlarm(SrvAlarmGroup srvAlarmGroup) {
		super();
		this.srvAlarmGroup = srvAlarmGroup;
	}


	@GetMapping(value="/admin/editalarm")
	public ModelAndView getDataGroupAlarms() {
	    ModelAndView modelView = new ModelAndView();
	    List<AlarmGroup> listAlarmGroup = srvAlarmGroup.getResAlarmGroup();
	    modelView.addObject("listAlarmGroup", listAlarmGroup);
	    modelView.setViewName("admin/editalarm");
	    return modelView;
    }

}
