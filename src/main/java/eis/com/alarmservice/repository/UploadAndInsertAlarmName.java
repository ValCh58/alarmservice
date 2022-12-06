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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

@Service
public class UploadAndInsertAlarmName {
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	@SuppressWarnings("unused")
	private Environment env;
	
	private String envDir = null;
	private File uploadDir = null;
	
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
	public UploadAndInsertAlarmName(@Qualifier("myJdbcConnect")DataSource dataSource, 
			                                                   JdbcTemplate jdbcTemplate, Environment env) {
		super();
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
		this.env = env;
		envDir = env.getProperty("path.upload.files");
		jdbcTemplate = new JdbcTemplate();		
		jdbcTemplate.setDataSource(dataSource);
	}
 	
	
	/**
	 * Import from TblAlarm table
	 * @return Boolean
	 */
	public boolean exportTblAlarmToCsv(){
		boolean ret = false;
		CSVPrinter csvPrinter = null;
		
		try 
		{
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
	
	/** Из БД alarmstorage0 !!!!!!!!!!!
	 * Export into TblAlarmUpload table
	 * @return Boolean
	 */
	private boolean ImportCsvToTblAlarmUpload() {
		boolean ret;
		Reader reader;
		
		if(!uploadDir.exists()) {
		    return (ret = false);	
		}
		
		try {
			reader = Files.newBufferedReader(Paths.get(uploadDir.getPath()));
			@SuppressWarnings("deprecation")
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader); //builder().setHeader().setSkipHeaderRecord(false);
		} catch (IOException e) {
				e.printStackTrace();
		}
        

		
		
		return false;
	}
	
	

}
