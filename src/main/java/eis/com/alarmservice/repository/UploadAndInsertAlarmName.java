package eis.com.alarmservice.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service
public class UploadAndInsertAlarmName {
	private DataSource dataSource;
	private DataSource dataSourcePrimary;
	private JdbcTemplate jdbcTemplate, jdbcTemlatePrimary;
	@SuppressWarnings("unused")
	private Environment env;
	private File uploadDir;
	private String envDir;
		
	final private String SQL_STR_IMPORT = "SELECT TSLast,"
										+ " TSActive,"
										+ " TSInactive,"
										+ " TSAckn,"
										+ " TValue,"
										+ " TType,"
										+ " LValue1,"
										+ " LType1,"
										+ " LValue2,"
										+ " LType2,"
										+ " GroupId,"
										+ " AlarmId,"
										+ " ClassId,"
										+ " Priority,"
										+ " State,"
										+ " OffsetLast,"
										+ " OffsetActive,"
										+ " OffsetInactive,"
										+ " OffsetAckn"
										+ " FROM TblAlarm";
		
	@Autowired
	public UploadAndInsertAlarmName(@Qualifier("myJdbcConnectPrimary")DataSource dataSourcePrimary,
			                                      @Qualifier("myJdbcConnect")DataSource dataSource, 
			                                                             JdbcTemplate jdbcTemplate,
			                                                       JdbcTemplate jdbcTemlatePrimary,
			                                                                        Environment env) {
		super();
		this.dataSourcePrimary = dataSourcePrimary;
		this.jdbcTemlatePrimary = jdbcTemlatePrimary; 
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
		this.env = env;
		this.envDir = env.getProperty("path.upload.files");
		this.uploadDir = new File(envDir + "importalarm.csv");
	}
	
	public void mergeTables() {
		exportTblAlarmToCsv();
		importCsvToTblAlarmUpload();
	}
 	
	
	/**
	 * Import from TblAlarm table
	 * @return Boolean
	 */
	public boolean exportTblAlarmToCsv(){
		boolean ret = false;
		CSVPrinter csvPrinter = null;
		
		try 
		{  //url=jdbc:sqlite:c://alarmstorage/alarmstorage.sqlite// 
		   //@Qualifier("myJdbcConnect")DataSource//
		   jdbcTemplate = new JdbcTemplate();		
		   jdbcTemplate.setDataSource(dataSource);	
		   uploadDir = new File(envDir + "importalarm.csv");	
		   if(uploadDir.exists()) {uploadDir.delete();}
		   uploadDir.createNewFile();
		   SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(SQL_STR_IMPORT);
		   BufferedWriter writer = Files.newBufferedWriter(Paths.get(uploadDir.toString()));
		   Builder csvFormat = CSVFormat.Builder.create().setHeader("TSLast", "TSActive", "TSInactive", "TSAckn",
				                                                    "TValue", "TType", "LValue1", "LType1",
				                                                    "LValue2", "LType2", "GroupId", "AlarmId", 
				                                                    "ClassId", "Priority", "State", "OffsetLast",
				                                                    "OffsetActive", "OffsetInactive", "OffsetAckn");
		   csvPrinter = new CSVPrinter(writer, csvFormat.build());
		   while(sqlRowSet.next()) {
			  csvPrinter.printRecord(
				sqlRowSet.getBigDecimal("TSLast"),
				sqlRowSet.getBigDecimal("TSActive"),
				sqlRowSet.getBigDecimal("TSInactive"),
				sqlRowSet.getBigDecimal("TSAckn"),
				sqlRowSet.getInt       ("TValue"),
				sqlRowSet.getBigDecimal("TType"),
				sqlRowSet.getInt       ("LValue1"),
				sqlRowSet.getBigDecimal("LType1"),
				sqlRowSet.getInt       ("LValue2"),
				sqlRowSet.getBigDecimal("LType2"),
				sqlRowSet.getBigDecimal("GroupId"),
				sqlRowSet.getBigDecimal("AlarmId"),
				sqlRowSet.getBigDecimal("ClassId"),
				sqlRowSet.getBigDecimal("Priority"),
				sqlRowSet.getBigDecimal("State"),
				sqlRowSet.getBigDecimal("OffsetLast"),
				sqlRowSet.getBigDecimal("OffsetActive"),
				sqlRowSet.getBigDecimal("OffsetInactive"),
				sqlRowSet.getBigDecimal("OffsetAckn")
			  );
		  }
		   ret = true; 
		}
		catch(InvalidResultSetAccessException  irsa) {
			irsa.printStackTrace();
		}
		catch (IOException ioe) {
		   ioe.printStackTrace();
	    }
		catch(Exception e) {
 		   e.printStackTrace();
		}
		finally {
		  try {
			csvPrinter.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		  try {
			csvPrinter.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		}
		return ret;
	}
	
	/** 
	 * Import into TblAlarmUpload table
	 * @return Boolean
	 */
	
	private boolean importCsvToTblAlarmUpload() {
		boolean ret = false;
		Reader reader;
		
		if(!uploadDir.exists()) {
		    return (ret = false);	
		}
		
		try {
			jdbcTemlatePrimary = new JdbcTemplate();		
			jdbcTemlatePrimary.setDataSource(dataSourcePrimary);
			reader = Files.newBufferedReader(Paths.get(uploadDir.getPath()));
			@SuppressWarnings("deprecation")
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader); //builder().setHeader().setSkipHeaderRecord(false);
			for (CSVRecord record : records) {
				 System.out.println(record.get(1)); 	
			}
		} catch (IOException e) {
				e.printStackTrace();
		}
        

		
		
		return ret;
	}
	
	

}
