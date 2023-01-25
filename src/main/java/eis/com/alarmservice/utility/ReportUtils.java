package eis.com.alarmservice.utility;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import eis.com.alarmservice.exceptions.ResourceNotFoundException;

@Component
public class ReportUtils {
	private Environment env;
	
	public ReportUtils(Environment env) {
		super();
		this.env = env;
	}

	/**
	 * Making a URL to call the report
	 * @param pathReport
	 * @param numUspd
	 * @param dateFrom
	 * @param dateTo
	 * @return URL
	 */
	private String makeUrlReport(String pathReport, String numUspd, LocalDate dateFrom, LocalDate dateTo) {
		
		StringBuilder sbUrl = new StringBuilder("http://");
		sbUrl.append(env.getProperty("jasperreport.ip_port"));//application.properties
		sbUrl.append("/jasperserver/flow.html?_flowId=viewReportFlow&reportUnit=");
		sbUrl.append(pathReport); 
		sbUrl.append(env.getProperty("jasperreport.type.report"));//application.properties
		sbUrl.append("&j_username=");
		sbUrl.append(env.getProperty("jasperreport.username"));//application.properties
		sbUrl.append("&j_password=");
		sbUrl.append(env.getProperty("jasperreport.password"));//application.properties
		
		StringBuilder sbParam = new StringBuilder();
		sbParam.append("&dateBegin=");
		sbParam.append(dateFrom.toString());
		sbParam.append("&dateEnd=");
		sbParam.append(dateTo.toString());
		
		if(!numUspd.equals("0")) {
		  sbParam.append("&numUspd=");
		  sbParam.append(numUspd);
		}
		
		return sbUrl.append(sbParam).toString();
	}
	
	/**
	 * Resource URL preparation
	 * 
	 */
	public String prepUrl(String pathReport, String numUspd, LocalDate dateFrom, LocalDate dateTo) {
				
		String url = Optional.ofNullable(makeUrlReport(pathReport, numUspd, dateFrom, dateTo))
				      .orElseThrow(()->new ResourceNotFoundException("Url is invalid"));
		return url;
	}
	

private String makeUrlReport(String pathReport, String numUspd, String dateFrom, String dateTo) {
		
		StringBuilder sbUrl = new StringBuilder("http://");
		sbUrl.append(env.getProperty("jasperreport.ip_port"));//From application.properties
		sbUrl.append("/jasperserver/flow.html?_flowId=viewReportFlow&reportUnit=");
		sbUrl.append(pathReport); 
		sbUrl.append(env.getProperty("jasperreport.type.report"));//From application.properties
		sbUrl.append("&j_username=");
		sbUrl.append(env.getProperty("jasperreport.username"));//From application.properties
		sbUrl.append("&j_password=");
		sbUrl.append(env.getProperty("jasperreport.password"));//From application.properties
		
		StringBuilder sbParam = new StringBuilder();
		sbParam.append("&dateBegin=");
		sbParam.append(dateFrom);
		sbParam.append("&dateEnd=");
		sbParam.append(dateTo);
		
		if(!numUspd.equals("0")) {
		  sbParam.append("&numUspd=");
		  sbParam.append(numUspd);
		}
		
		return sbUrl.append(sbParam).toString();
	}

/**
 * Resource URL preparation
 * 
 */

public String prepUrl(String pathReport, String numUspd, String dateFrom, String dateTo) {
			
	String url = Optional.ofNullable(makeUrlReport(pathReport, numUspd, dateFrom, dateTo))
			      .orElseThrow(()->new ResourceNotFoundException("Url is invalid"));
	return url;
}    

/**
 * To make format date 'YYYY-mm-dd' for sorting
 * @return StringBuilder
 */
public StringBuilder ReversDate(StringBuilder sb) {
   char err[] = {'0','0','0','0','-','0','0','-','0','0',' ','0','0',':','0','0',':','0','0'};
   char[] str = sb.reverse().toString().toCharArray();
   if(str.length > 0) {
	   swap(str, 5, 6);
	   swap(str, 8, 9);
	   swap(str, 0, 3);
	   swap(str, 1, 2);
   }else {
	   return (new StringBuilder()).append(err);
   }
   return (new StringBuilder()).append(str);
}

private void swap(char[] src, int beg, int end) {
	char a = src[end];
    src[end] = src[beg];
    src[beg] = a;
}

}
