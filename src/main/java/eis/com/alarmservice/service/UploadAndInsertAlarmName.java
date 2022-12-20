package eis.com.alarmservice.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eis.com.alarmservice.modeladmin.TblAlarmUpload;


@Service
public class UploadAndInsertAlarmName {
	private DataSource dataSource;
	private DataSource dataSourcePrimary;
	private JdbcTemplate jdbcTemplate, jdbcTemlatePrimary;
	@SuppressWarnings("unused")
	private Environment env;
	private File uploadDir;
	private String envDir;
	
	private List<TblAlarmUpload> list = new ArrayList<>();
		
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
	
	final private String SQL_STR_INS = "INSERT INTO TblAlarmUpload ("
			                         + " TSLast,  TSActive, TSInactive, TSAckn, TValue, TType,"
			                         + " LValue1, LType1, LValue2, LType2, GroupId, AlarmId,"
			                         + " ClassId, Priority, State, OffsetLast, OffsetActive,"
			                         + " OffsetInactive, OffsetAckn"
			                         + " )"
			                         + " VALUES ("
			                         + " ?,"//'TSLast'
		                        	 + " ?,"//'TSActive'
			                         + " ?,"//'TSInactive'
			                         + " ?,"//'TSAckn'
			                         + " ?,"//'TValue'
			                         + " ?,"//'TType'
			                         + " ?,"//'LValue1',
			                         + " ?,"//'LType1',
			                         + " ?,"//'LValue2',
			                         + " ?,"//'LType2',
			                         + " ?,"//'GroupId',
			                         + " ?,"//'AlarmId',
			                         + " ?,"//'ClassId',
			                         + " ?,"//'Priority',
			                         + " ?,"//'State',
			                         + " ?,"//'OffsetLast',
			                         + " ?,"//'OffsetActive',
			                         + " ?,"//'OffsetInactive',
			                         + " ?"//'OffsetAckn'
			                         + " );";
	
	final private String SQL_STR_DELETE = "DELETE FROM TblAlarmUpload;";
	final private String SQL_STR_VACUUM = "VACUUM";
	final private String SQL_STR_MERGE = "INSERT INTO TblAlarm "
			                           + " select * from TblAlarmUpload b "
			                           + " where NOT EXISTS(select a.TSLast from TblAlarm a where "
			                           + " a.TSLast = b.TSLast and a.AlarmId = b.AlarmId and a.GroupId = b.GroupId);";
   			
		
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
		this.envDir = null;
		if (SystemUtils.IS_OS_WINDOWS)    {envDir = env.getProperty("path.win.upload.files");}
	    else if (SystemUtils.IS_OS_LINUX) {envDir = env.getProperty("path.linux.upload.files");}
		this.uploadDir = new File(envDir + "importalarm.csv");
	}
	
	/**
	 * Wrapper for Merge data
	 * @throws IOException
	 * @throws SQLException
	 * @throws InvalidResultSetAccessException
	 */
	public void mergeTables() throws IOException, 
	                                 SQLException, 
	                                 InvalidResultSetAccessException{
		
		exportTblAlarmToCsv();//export to csv file//
		importCsvToTblAlarmUpload();//export from csv file to table 'TblAlarmUpload'//
		int[] cntRec = batchUpdateTblAlarmUpload(list);//Batch update table 'TblAlarmUpload'//
		if(cntRec.length > 0) { 
		   merge2Tables();	//Merge data tables 'TblAlarm' and 'TblAlarmUpload'//
		}
	}
 	
	
	/**
	 * Export from TblAlarm table
	 * @return Boolean
	 */
	@Transactional(readOnly = true)
	public boolean exportTblAlarmToCsv() throws InvalidResultSetAccessException, IOException {
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
		}finally {
		 try {
			    if(csvPrinter != null) {csvPrinter.close();}
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
	@SuppressWarnings("deprecation")
	private boolean importCsvToTblAlarmUpload() throws IOException {
		boolean ret = false;
		Reader reader = null;
				
		if(!uploadDir.exists()) {
		    return (ret = false);	
		}
		
		try {
			jdbcTemlatePrimary = new JdbcTemplate();		
			jdbcTemlatePrimary.setDataSource(dataSourcePrimary);
			reader = Files.newBufferedReader(Paths.get(uploadDir.getPath()));
			@SuppressWarnings("resource")
			Iterable<CSVRecord> records = new CSVParser(reader, CSVFormat.RFC4180.withFirstRecordAsHeader());
			for(CSVRecord record : records){
				TblAlarmUpload tau = new TblAlarmUpload
					   (
						new BigInteger(record.get("TSLast").isEmpty()?"0":record.get("TSLast")),
						new BigInteger(record.get("TSActive").isEmpty()?"0":record.get("TSActive")),
						new BigInteger(record.get("TSInactive").isEmpty()?"0":record.get("TSInactive")),
						new BigInteger(record.get("TSAckn").isEmpty()?"0":record.get("TSAckn")),
						Double.valueOf(record.get("TValue").isEmpty()?"0":record.get("TValue")),
						new BigInteger(record.get("TType").isEmpty()?"0":record.get("TType")),
						Double.valueOf(record.get("LValue1").isEmpty()?"0":record.get("LValue1")),
						new BigInteger(record.get("LType1").isEmpty()?"0":record.get("LType1")),
						Double.valueOf(record.get("LValue2").isEmpty()?"0":record.get("LValue2")),
						new BigInteger(record.get("LType2").isEmpty()?"0":record.get("LType2")),
						new BigInteger(record.get("GroupId").isEmpty()?"0":record.get("GroupId")),
						new BigInteger(record.get("AlarmId").isEmpty()?"0":record.get("AlarmId")),
						new BigInteger(record.get("ClassId").isEmpty()?"0":record.get("ClassId")),
						new BigInteger(record.get("Priority").isEmpty()?"0":record.get("Priority")),
						new BigInteger(record.get("State").isEmpty()?"0":record.get("State")),
						new BigInteger(record.get("OffsetLast").isEmpty()?"0":record.get("OffsetLast")),
						new BigInteger(record.get("OffsetActive").isEmpty()?"0":record.get("OffsetActive")),
						new BigInteger(record.get("OffsetInactive").isEmpty()?"0":record.get("OffsetInactive")),
						new BigInteger(record.get("OffsetAckn").isEmpty()?"0":record.get("OffsetAckn"))
					   );
				 list.add(tau);
			}
		}
		finally {
			try {
				if(reader != null) {reader.close();}
			} catch (IOException e) {e.printStackTrace();}	
		}
   	    return ret;
	}
	
	/**
	 * Batch Update 'TblAlarmUpload' 
	 * @param list
	 * @return int[]
	 * @throws SQLException
	 */
	@Transactional 
	private int[] batchUpdateTblAlarmUpload(final List<TblAlarmUpload> list) throws SQLException {
		jdbcTemlatePrimary.execute(SQL_STR_DELETE);//Delete all records from table 'TblAlarmUpload'
		jdbcTemlatePrimary.execute(SQL_STR_VACUUM);//Database cleanup
		return jdbcTemlatePrimary.batchUpdate(SQL_STR_INS, new BatchPreparedStatementSetter() {
		            @Override
		            public void setValues(PreparedStatement ps, int i) throws SQLException {
		                ps.setObject(1, list.get(i).getTsLast());
		                ps.setObject(2, list.get(i).getTsActive());
		                ps.setObject(3, list.get(i).getTsInactive());
		                ps.setObject(4, list.get(i).getTsAckn());
		                ps.setObject(5, list.get(i).getTValue());
		                ps.setObject(6, list.get(i).getTType());
		                ps.setObject(7, list.get(i).getLValue1());
		                ps.setObject(8, list.get(i).getLType1());
		                ps.setObject(9, list.get(i).getLValue2());
		                ps.setObject(10, list.get(i).getLType2());
		                ps.setObject(11, list.get(i).getGroupId());
		                ps.setObject(12, list.get(i).getAlarmId());
		                ps.setObject(13, list.get(i).getClassId());
		                ps.setObject(14, list.get(i).getPriority());
		                ps.setObject(15, list.get(i).getState());
		                ps.setObject(16, list.get(i).getOffsetLast());
		                ps.setObject(17, list.get(i).getOffsetActive());
		                ps.setObject(18, list.get(i).getOffsetInactive());
		                ps.setObject(19, list.get(i).getOffsetAckn());
		            }
		            @Override
		            public int getBatchSize() {
		                return list.size();
		            }
		       });
	}
	
	/**
	 * Request for merge data
	 */
	@Transactional
	private void merge2Tables() {
		
		jdbcTemlatePrimary.execute(SQL_STR_MERGE);
	}
}
