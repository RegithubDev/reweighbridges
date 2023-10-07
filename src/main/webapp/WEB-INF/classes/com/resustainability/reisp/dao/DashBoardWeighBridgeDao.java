package com.resustainability.reisp.dao;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.resustainability.reisp.model.DashBoardWeighBridge;
import com.resustainability.reisp.model.ProjectLocation;

@Repository
public class DashBoardWeighBridgeDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	DataSource dataSource;

	@Autowired
	DataSourceTransactionManager transactionManager;

	public List<DashBoardWeighBridge> getDashboards(DashBoardWeighBridge obj) throws Exception{
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		List<DashBoardWeighBridge> objsList1 = new ArrayList<DashBoardWeighBridge>();
		List<DashBoardWeighBridge> objsList2 = new ArrayList<DashBoardWeighBridge>();
		try {
			String qry = "SELECT REPLACE(REPLACE([company], '\r', ''), '\n', '') AS company,REPLACE(REPLACE([sbu], '\r', ''), '\n', '') AS sbu,"
					+ "REPLACE(REPLACE([project], '\r', ''), '\n', '') AS project,REPLACE(REPLACE([project_name], '\r', ''), '\n', '') AS project_name,"
					+ "REPLACE(REPLACE([location], '\r', ''), '\n', '') AS location,REPLACE(REPLACE([project_status], '\r', ''), '\n', '') AS project_status,"
					+ "no_of_wb,wb_site_id,db_name,table_name ,api_status,api_consumed_by"
					+ ",developed_by FROM [MasterDB].[dbo].[master_table] mt "
					+ " where  mt.location is not null and  mt.location <> '' and  wb_site_id is not null  "; 
			int arrSize1 = 0;
			 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
					qry = qry + " AND sbu = ? ";
					arrSize1++;
				}
			 qry = qry + " order by sbu desc ";
			 Object[] pValues1 = new Object[arrSize1];
				int j = 0;
				 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
					 pValues1[j++] = obj.getSbu();
				}
			objsList = jdbcTemplate.query( qry,pValues1, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
			
			for(DashBoardWeighBridge siteIDs : objsList) {
				String siteID = siteIDs.getWb_site_id().replaceAll("'", "");
				if(siteID.contains(",")) {
					String [] siteIDss = siteID.split(",");
					siteID = siteIDss[0];
					if(siteID.equals("WB1")){
						siteID = "WB3";
					}
				}
				String all_sites = "'"+siteIDs.getWb_site_id().replace("'", "")+"'";
				String data = new String("");
				String setQry = "SET NOCOUNT ON;";
				if(siteIDs.getDb_name().equals("ALL_BMW_Sites")) {
					data = ""
							+"SELECT top 1 [id] as UID,'bmw' as dmNmae,(select indicator_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as indicator_name , "
							+ " (select top (1) (cast([visitDayTime] as time(0))) from [ALL_BMW_Sites].[dbo].bmw_detailed   where company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%' order by ServerDateTime desc ) as visitDayTime,(select top 1 count(*)   from [ALL_BMW_Sites].[dbo].bmw_detailed "
							+ "where  company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%' ) as count,"
							+ "   (select top 1 min(convert(varchar, ServerDateTime, 23))   from [ALL_BMW_Sites].[dbo].bmw_detailed "
							+ "where  company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%' ) as min,"
							+ " (select top 1  max(convert(varchar, ServerDateTime, 23)) from [ALL_BMW_Sites].[dbo].bmw_detailed where company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%' ) as DATEIN  "
							+ "      ,(select project_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_name ,"
									+ "(select project_status from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_status,"
											+ "(select sbu from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as sbu,"
													+ "(select project from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project, "
															+ "(select company from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as company,convert(varchar, getdate(), 23) as curDAte  "
							+ ",  (select count(*) from [ALL_BMW_Sites].[dbo].bmw_detailed where  convert(varchar, ServerDateTime, 23) = convert(varchar, getdate(), 23)"
							+ " and company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%' ) "
							+ "as dailyCount"
						
							+ "  FROM [ALL_BMW_Sites].[dbo].[bmw_detailed] ";
					objsList1 = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
					objsList2.addAll(objsList1);
				}else {
				data = ""
						//+ "BEGIN TRY   SET NOCOUNT ON; "
						+ "select top 1 UID,TRNO,"
						+ " (select project_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_name,"
						+ "VEHICLENO ,"+all_sites+" as all_sites,(select indicator_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as indicator_name ,"
						+ "(select min(CAST( CASE  WHEN DATEIN LIKE '%00:00:00%' THEN CONVERT(DATETIME,datein, 103)+' 12:00:00 AM'  ELSE DATEIN  END AS DATE)) from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" "
								+ "where DATEIN is not null and DATEIN <> '' and SITEID='"+siteID+"')  as min,"
										+ "(select max(CAST( CASE  WHEN DATEIN LIKE '%00:00:00%' THEN CONVERT(DATETIME,datein, 103)+' 12:00:00 AM'  ELSE DATEIN  END AS DATE)) from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()
										+ " where DATEIN is not null and DATEIN <> '' and SITEID='"+siteID+"')  as DATEIN,convert(varchar, getdate(), 23) as curDAte"
												+ ",(select top (1) (cast([TIMEIN] as time(0))) from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+"  "
												+ " where SITEID in ("+siteIDs.getWb_site_id()+") "
												+ "order by TRNO desc ) as TIMEIN"
										+ ",(select count(TRNO) from "+siteIDs.getDb_name()+""
										+ ".[dbo]."+siteIDs.getTable_name()+"  where SITEID='"+siteID+"' and NETWT <> '' and NETWT is not null and NETWT <> 0) as count,"
												+ "(select project from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project,"
												+ "(select project_status from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_status,"
								+ "MATERIAL,PARTY,TRANSPORTER,BILLDCNO,BILLWEIGHT,FIRSTWEIGHT,USER1,"
								+ "CONVERT(varchar(9), DATEOUT, 105) AS DATEOUT,TIMEOUT,SECONDWEIGHT,USER2,SITEID,STATUS,FIRSTFRONTPOTO"
						+ "		   ,FIRSTBACKPOTO,SECONDFRONTPOTO,SECONDBACKPOTO,NETWT,SW_SITEID,TRIPNO,SHIFTNO,TRANSFERWASTEIE ,TRANSFERWASTE,MANIFESTNUMBER ,MANIFESTWEIGHT,MEMBERSHIPCODE"
						+ "		   ,INGATEPASSNO ,INMETERREADING,OUTGATEPASSNO,OUTMETERREADING ,TRANSFERID,TYPEOFWASTE,TOTALKMSTRAVELLED ,BILLABLEWEIGHT,TOTALTRANSPORTCHARGES ,BARCODENUM"
						+ "		   ,REMARKS,CONTAINERID from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" tt "
								+ "where TRNO is not null and NETWT is not null and NETWT <> ''  ";
								
				int arrSize = 0;
			    if(!StringUtils.isEmpty(siteIDs) && !StringUtils.isEmpty(siteIDs.getWb_site_id())) {
			    	data = data + " and SITEID in ("+siteIDs.getWb_site_id()+") ";
					arrSize++;
				}
			    data = data + " order by CONVERT(varchar(9), DATEIN, 103) desc ";
			 
				Object[] pValues = new Object[arrSize];
				int i = 0;
				if(!StringUtils.isEmpty(siteIDs.getWb_site_id()) && !StringUtils.isEmpty(siteIDs.getWb_site_id())) {
					pValues[i++] = siteIDs.getWb_site_id();
				}
				objsList1 = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
				objsList2.addAll(objsList1);
			}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList2;
	}

	public List<DashBoardWeighBridge> getSBUsList(DashBoardWeighBridge obj) throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		try {
			String qry = "SELECT distinct sbu  FROM [MasterDB].[dbo].[master_table] mt "
					+ " where  mt.sbu is not null and  mt.sbu <> '' and  project_status <> 'InActive' and wb_site_id is not null order by sbu desc"; 
			objsList = jdbcTemplate.query( qry, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList;
	}

	public List<DashBoardWeighBridge> getDashboardsDaily(DashBoardWeighBridge obj) throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		List<DashBoardWeighBridge> objsList1 = new ArrayList<DashBoardWeighBridge>();
		List<DashBoardWeighBridge> objsList2 = new ArrayList<DashBoardWeighBridge>();
		List<DashBoardWeighBridge> objsList3 = new ArrayList<DashBoardWeighBridge>();

		try {
			String qry = "SELECT REPLACE(REPLACE([company], '\r', ''), '\n', '') AS company,REPLACE(REPLACE([sbu], '\r', ''), '\n', '') AS sbu,"
					+ "REPLACE(REPLACE([project], '\r', ''), '\n', '') AS project,REPLACE(REPLACE([project_name], '\r', ''), '\n', '') AS project_name,"
					+ "REPLACE(REPLACE([location], '\r', ''), '\n', '') AS location,REPLACE(REPLACE([project_status], '\r', ''), '\n', '') AS project_status,"
					+ "no_of_wb,wb_site_id,db_name,table_name ,api_status,api_consumed_by"
					+ ",developed_by FROM [MasterDB].[dbo].[master_table] mt "
					+ " where  mt.location is not null and  mt.location <> '' and  wb_site_id is not null order by sbu desc"; 
			int arrSize1 = 0;
			 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
					qry = qry + " AND sbu = ? ";
					arrSize1++;
				}
			 Object[] pValues1 = new Object[arrSize1];
				int j = 0;
				 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
					 pValues1[j++] = obj.getSbu();
				}
			objsList = jdbcTemplate.query( qry,pValues1, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
			int counts = 0;
			for(DashBoardWeighBridge siteIDs : objsList) {
				String siteID = siteIDs.getWb_site_id().replaceAll("'", "").trim();
				if(siteID.contains(",")) {
					String [] siteIDss = siteID.split(",");
					siteID = siteIDss[0];
					if(siteID.equals("WB1")){
						siteID = "WB3";
					}
				}
				String pattern = "MM-dd-yyyy";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				date = date+" 00:00:00";
				
				String pattern3 = "dd-MM-yyyy";
				SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat(pattern3);
				String date3 = simpleDateFormat3.format(new Date());
				date3 = date3+" 00:00:00";
				
				String pattern2 = "M/d/yyyy";
				SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
				String date2 = simpleDateFormat2.format(new Date());
				date2 = date2+" 12:00:00 AM";
				
				String pattern4 = "d/M/yyyy";
				SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat(pattern4);
				String date4 = simpleDateFormat4.format(new Date());
				date4 = date4+" 12:00:00 AM";
				
				String data = new String("");
				String setQry = "SET NOCOUNT ON;";
				String all_sites = "'"+siteIDs.getWb_site_id().replace("'", "")+"'";
				LocalDate today = LocalDate.now();

		        // Subtract one day from the current date to get yesterday's date
		        LocalDate yesterday = today.minusDays(1);

		        // Get the year, month, and day for yesterday
		        int year = yesterday.getYear();
		        int month = yesterday.getMonthValue();
		        int day = yesterday.getDayOfMonth();

		       String yesDAte = year + "-" + month + "-" + day;
				if(siteIDs.getDb_name().equals("ALL_BMW_Sites")) {
					data = ""
							+"SELECT top 1 [id] as UID,'bmw' as dmNmae,(select indicator_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as indicator_name , "
							+ "   (select top 1 convert(varchar, ServerDateTime, 23)   from [ALL_BMW_Sites].[dbo].bmw_detailed where  convert(varchar, ServerDateTime, 23) = convert(varchar, getdate(), 23)"
							+ " and company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%' ) as DATEIN  "
							+ "      ,(select project_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_name ,"
									+ "(select project_status from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_status,"
											+ "(select sbu from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as sbu,"
													+ "(select project from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project, "
															+ "(select company from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as company,convert(varchar, getdate(), 23) as curDAte  "
							+ ",  (select count(*) from [ALL_BMW_Sites].[dbo].bmw_detailed where  convert(varchar, ServerDateTime, 23) = convert(varchar, getdate(), 23)"
							+ " and company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%' ) "
							+ "as dailyCount   "
						
							+ "  FROM [ALL_BMW_Sites].[dbo].[bmw_detailed]";
					objsList1 = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
					objsList2.addAll(objsList1);
				}else {
					data = ""
							
							//+ "BEGIN TRY   SET NOCOUNT ON; "
							+ "select top 1 UID,TRNO,VEHICLENO ,"+all_sites+" as all_sites,"+siteIDs.getNo_of_wb()+" as no_of_wbR, (select project_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_name,"
							+ "(select min(CAST( CASE  WHEN DATEIN LIKE '%00:00:00%' THEN CONVERT(DATETIME,datein, 103)+' 12:00:00 AM'  ELSE DATEIN  END AS DATE)) from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" "
									+ "where DATEIN is not null and DATEIN <> '' and SITEID='"+siteID+"')  as min,(select indicator_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as indicator_name ,"
											+ "(Select count(*) from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" where "
											+ " datein = (select top (1) datein from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" where SITEID in ("+siteIDs.getWb_site_id()+") order by trno desc) "
											+ "	and  SITEID  in ("+siteIDs.getWb_site_id()+")  and NETWT is not null and NETWT <> '' ) as dailyCount,"
											+ "(select max(CAST( CASE  WHEN DATEIN LIKE '%00:00:00%' THEN CONVERT(DATETIME,datein, 103)+' 12:00:00 AM'  ELSE DATEIN  END AS DATE)) from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()
											+ " where DATEIN is not null and DATEIN <> '' and SITEID in ("+siteIDs.getWb_site_id()+") and datein = (select top (1) datein from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+"  where SITEID in ("+siteIDs.getWb_site_id()+") order by CAST( CASE  WHEN DATEIN LIKE '%00:00:00%' THEN CONVERT(DATETIME,datein, 103)+' 12:00:00 AM'"
													+ "ELSE DATEIN  END AS DATE) desc))  as DATEIN,convert(varchar, getdate(), 23) as curDAte"
											+ ",(select count(TRNO) from "+siteIDs.getDb_name()+""
											+ ".[dbo]."+siteIDs.getTable_name()+"  where SITEID='"+siteID+"' and NETWT <> '' and NETWT is not null and NETWT <> 0) as count,"
													+ "(select project from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project,"
													+ "(select project_status from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_status,"
													+ "(select sbu from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as sbu,"
									+ "MATERIAL,PARTY,TRANSPORTER,BILLDCNO,BILLWEIGHT,TIMEIN,FIRSTWEIGHT,USER1,"
									+ "(select count(*) from  [MasterDB].[dbo].[master_table] where sbu like '"+siteIDs.getSbu()+"' and project_status='Active') as all_wb,"
									+ "CONVERT(varchar(9), DATEOUT, 105) AS DATEOUT,TIMEOUT,SECONDWEIGHT,USER2,SITEID,STATUS,FIRSTFRONTPOTO"
							+ "		   ,FIRSTBACKPOTO,SECONDFRONTPOTO,SECONDBACKPOTO,NETWT,SW_SITEID,TRIPNO,SHIFTNO,TRANSFERWASTEIE ,TRANSFERWASTE,MANIFESTNUMBER ,MANIFESTWEIGHT,MEMBERSHIPCODE"
							+ "		   ,INGATEPASSNO ,INMETERREADING,OUTGATEPASSNO,OUTMETERREADING ,TRANSFERID,TYPEOFWASTE,TOTALKMSTRAVELLED ,BILLABLEWEIGHT,TOTALTRANSPORTCHARGES ,BARCODENUM"
							+ "		   ,REMARKS,CONTAINERID from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" tt "
									+ "where TRNO is not null and NETWT is not null and NETWT <> '' ";

					int arrSize = 0;
				    if(!StringUtils.isEmpty(siteIDs) && !StringUtils.isEmpty(siteIDs.getWb_site_id())) {
				    	data = data + " and SITEID in ("+siteIDs.getWb_site_id()+") ";
						arrSize++;
					}
				    data = data + " order by CONVERT(varchar(9), DATEIN, 103) desc ";
				 
					Object[] pValues = new Object[arrSize];
					int i = 0;
					if(!StringUtils.isEmpty(siteIDs.getWb_site_id()) && !StringUtils.isEmpty(siteIDs.getWb_site_id())) {
						pValues[i++] = siteIDs.getWb_site_id();
					}		
					
					objsList1 = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
					if(!siteIDs.getDb_name().equals("ALL_BMW_Sites")) {
					String WBQry = "SELECT SITEID as greenWB,(select no_of_wb from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as no_of_wb2 FROM  "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" t1 where SITEID in ("+siteIDs.getWb_site_id()+") and datein = (select top (1) datein from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" where SITEID in ("+siteIDs.getWb_site_id()+") order by trno desc) "
							+ "GROUP BY SITEID";
					objsList3 = jdbcTemplate.query( WBQry, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
					 String commaSeparatedString = objsList3.stream()
				                .map(DashBoardWeighBridge::getGreenWB)
				                .collect(Collectors.joining(","));
				
					objsList2.addAll(objsList1);
					if(!StringUtils.isEmpty(commaSeparatedString) && objsList3.size() != 0){
						if(counts == 14) {
							System.out.println("sfd");
						}
						objsList2.get(counts).setGreenWB(commaSeparatedString);
						 if(!StringUtils.isEmpty(objsList3.get(0).getNo_of_wb2())) {
								objsList2.get(counts).setNo_of_wb2(""+objsList3.size()+"");
						 }
					}
					counts++;
					}
				}
		
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList2;
	}

	public List<DashBoardWeighBridge> getMSWDataWithSiteID(DashBoardWeighBridge obj) throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		try {
			String pattern = "MM-dd-yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			date = date+" 00:00:00";
			

			String pattern3 = "dd-MM-yyyy";
			SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat(pattern3);
			String date3 = simpleDateFormat3.format(new Date());
			date3 = date3+" 00:00:00";
			
			String pattern2 = "M/d/yyyy";
			SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
			String date2 = simpleDateFormat2.format(new Date());
			date2 = date2+" 12:00:00 AM";
			
			String pattern4 = "d/M/yyyy";
			SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat(pattern4);
			String date4 = simpleDateFormat4.format(new Date());
			date4 = date4+" 12:00:00 AM";
			String data = new String("");
			String dbNAme = "All_MSW_SITES";
			if("CND".equalsIgnoreCase(obj.getSbu())) {
				dbNAme = "All_CnD_Sites";
			}
			data = ""
					+ "select TRNO,VEHICLENO ,CONVERT(varchar(9), DATEIN, 105) AS DATEIN,"
							+ "MATERIAL,PARTY,TRANSPORTER,BILLDCNO,BILLWEIGHT,TIMEIN,FIRSTWEIGHT,USER1,"
							+ "CONVERT(varchar(9), DATEOUT, 105) AS DATEOUT,TIMEOUT,SECONDWEIGHT,USER2,SITEID,STATUS,FIRSTFRONTPOTO"
					+ "		   ,FIRSTBACKPOTO,SECONDFRONTPOTO,SECONDBACKPOTO,NETWT,SW_SITEID,TRIPNO,SHIFTNO,TRANSFERWASTEIE ,TRANSFERWASTE,MANIFESTNUMBER ,MANIFESTWEIGHT,MEMBERSHIPCODE"
					+ "		   ,INGATEPASSNO ,INMETERREADING,OUTGATEPASSNO,OUTMETERREADING ,TRANSFERID,TYPEOFWASTE,TOTALKMSTRAVELLED ,BILLABLEWEIGHT,TOTALTRANSPORTCHARGES ,BARCODENUM"
					+ "		   ,REMARKS,CONTAINERID from ["+dbNAme+"].[dbo].[WEIGHT] tt "
							+ "where TRNO is not null and NETWT is not null and NETWT <> '' and ( datein = '"+date+ "' or datein = '"+date2+"' or  datein = '"+date3+ "' or datein = '"+date4+"' ) ";
							
			int arrSize = 0;
		    if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSITEID())) {
		    	data = data + " and SITEID in("+obj.getSITEID()+")";
				arrSize++;
			}
		    data = data + " order by TRNO ";
		 
			Object[] pValues = new Object[arrSize];
			int i = 0;
			if(!StringUtils.isEmpty(obj.getWb_site_id()) && !StringUtils.isEmpty(obj.getWb_site_id())) {
				pValues[i++] = obj.getWb_site_id();
			}
			objsList = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList;
	}

	public List<DashBoardWeighBridge> getCNDDataWithSiteID(DashBoardWeighBridge obj) throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		try {
			String data = new String("");
			data = ""
					+ "(select CONVERT(varchar(9), DATEIN, 105) AS DATEIN,"
							+ "MATERIAL,PARTY,TRANSPORTER,BILLDCNO,BILLWEIGHT,TIMEIN,FIRSTWEIGHT,USER1,"
							+ "CONVERT(varchar(9), DATEOUT, 105) AS DATEOUT,TIMEOUT,SECONDWEIGHT,USER2,SITEID,STATUS,FIRSTFRONTPOTO"
					+ "		   ,FIRSTBACKPOTO,SECONDFRONTPOTO,SECONDBACKPOTO,NETWT,SW_SITEID,TRIPNO,SHIFTNO,TRANSFERWASTEIE ,TRANSFERWASTE,MANIFESTNUMBER ,MANIFESTWEIGHT,MEMBERSHIPCODE"
					+ "		   ,INGATEPASSNO ,INMETERREADING,OUTGATEPASSNO,OUTMETERREADING ,TRANSFERID,TYPEOFWASTE,TOTALKMSTRAVELLED ,BILLABLEWEIGHT,TOTALTRANSPORTCHARGES ,BARCODENUM"
					+ "		   ,REMARKS,CONTAINERID from [All_CnD_Sites].[dbo].[WEIGHT] tt "
							+ "where TRNO is not null and NETWT is not null and NETWT <> '' ";
							
			int arrSize = 0;
		    if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getWb_site_id())) {
		    	data = data + " and SITEID = "+obj.getWb_site_id();
				arrSize++;
			}
		    data = data + " order by CONVERT(varchar(9), DATEIN, 103) desc ";
		 
			Object[] pValues = new Object[arrSize];
			int i = 0;
			if(!StringUtils.isEmpty(obj.getWb_site_id()) && !StringUtils.isEmpty(obj.getWb_site_id())) {
				pValues[i++] = obj.getWb_site_id();
			}
			objsList = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList;
	}

	public List<DashBoardWeighBridge> getProjectssList(DashBoardWeighBridge obj) throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		List<DashBoardWeighBridge> objsList1 = new ArrayList<DashBoardWeighBridge>();
		try {
			String pattern = "MM-dd-yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			date = date+" 00:00:00";
			

			String pattern3 = "dd-MM-yyyy";
			SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat(pattern3);
			String date3 = simpleDateFormat3.format(new Date());
			date3 = date3+" 00:00:00";
			
			String pattern2 = "M/d/yyyy";
			SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
			String date2 = simpleDateFormat2.format(new Date());
			date2 = date2+" 12:00:00 AM";
			
			String pattern4 = "d/M/yyyy";
			SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat(pattern4);
			String date4 = simpleDateFormat4.format(new Date());
			date4 = date4+" 12:00:00 AM";
			
			
			String qry = "SELECT REPLACE(REPLACE([company], '\r', ''), '\n', '') AS company,REPLACE(REPLACE([sbu], '\r', ''), '\n', '') AS sbu,"
					+ "REPLACE(REPLACE([project], '\r', ''), '\n', '') AS project,REPLACE(REPLACE([project_name], '\r', ''), '\n', '') AS project_name,"
					+ "REPLACE(REPLACE([location], '\r', ''), '\n', '') AS location,REPLACE(REPLACE([project_status], '\r', ''), '\n', '') AS project_status,"
					+ "no_of_wb,wb_site_id,db_name,table_name ,api_status,api_consumed_by"
					+ ",developed_by,convert(varchar, getdate(), 23) as curDAte FROM [MasterDB].[dbo].[master_table] mt "
					+ " where  mt.location is not null and  mt.location <> '' and  wb_site_id is not null and project_status <> 'Inactive' "; 
			int arrSize = 0;
		    if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
		    	qry = qry + " and sbu = ? ";
				arrSize++;
			}
			qry = qry + "order by project desc"; 
			Object[] pValues = new Object[arrSize];
			int i = 0;
			if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
				pValues[i++] = obj.getSbu();
			}
			String dbNAme = "All_MSW_SITES";
			if("CND".equalsIgnoreCase(obj.getSbu())) {
				dbNAme = "All_CnD_Sites";
			}
			objsList = jdbcTemplate.query( qry,pValues, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
			for (DashBoardWeighBridge siteIDs : objsList) {
				String data = "";
				String siteID = siteIDs.getWb_site_id().replaceAll("'", "").trim();
				if(siteID.contains(",")) {
					String [] siteIDss = siteID.split(",");
					siteID = siteIDss[0];
					if(siteID.equals("WB1")){
						siteID = "WB3";
					}
				}
				if(siteIDs.getDb_name().equals("ALL_BMW_Sites")) {
					data = ""
							+ "Select  id "
							+ "      ,company "
							+ "      ,plant "
							+ "      ,route "
							+ "      ,CustomerDistrict "
							+ "      ,CustomerTown as customerTown "
							+ "      ,VehicleRegNo as vehicleRegNo "
							+ "      ,CustomerName "
							+ "      ,CustomerCABSCode "
							+ "      ,CustomerSAPCode "
							+ "      ,TypeofEstablishment "
							+ "      ,ManifestNo "
							+ "      ,ActualVisitDate as actualVisitDate "
							+ "      ,ActualVisitMonth "
							+ "      ,ActualVisitYear "
							+ "      ,VisitDayTime "
							+ "      ,ServiceFrequency "
							+ "      ,BlueCount "
							+ "      ,BlueWeight "
							+ "      ,RedCount "
							+ "      ,RedWeight "
							+ "      ,YellowCount "
							+ "      ,YellowWeight "
							+ "      ,CytotoxicCount "
							+ "      ,CytotoxicWeight "
							+ "      ,WhitesCount "
							+ "      ,WhitesWeight "
							+ "      ,TotalCount "
							+ "      ,TotalWeight "
							+ "      ,CollectionLatitude "
							+ "      ,CollectionLongitude "
							+ "      ,ServerDateTime "
							+ "      ,APIType as aPIType "
							+ "      ,CabsTransID as cabsTransID "
							+ "      ,APIID as aPIID "
							+ "  FROM [ALL_BMW_Sites].[dbo].[bmw_detailed] where convert(varchar, ServerDateTime, 23) = convert(varchar, getdate(), 23) "
							+ " and company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%'";
					objsList1 = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
				}else {
					 data = ""
							+ "select TRNO,VEHICLENO ,CONVERT(varchar(10), DATEIN, 105) AS DATEIN,"
									+ "MATERIAL,PARTY,TRANSPORTER,BILLDCNO,BILLWEIGHT,TIMEIN,FIRSTWEIGHT,USER1,"
									+ "CONVERT(varchar(10), DATEOUT, 105) AS DATEOUT,TIMEOUT,SECONDWEIGHT,USER2,SITEID,STATUS,FIRSTFRONTPOTO"
							+ "		   ,FIRSTBACKPOTO,SECONDFRONTPOTO,SECONDBACKPOTO,NETWT,SW_SITEID,TRIPNO,SHIFTNO,TRANSFERWASTEIE ,TRANSFERWASTE,MANIFESTNUMBER ,MANIFESTWEIGHT,MEMBERSHIPCODE"
							+ "		   ,INGATEPASSNO ,INMETERREADING,OUTGATEPASSNO,OUTMETERREADING ,TRANSFERID,TYPEOFWASTE,TOTALKMSTRAVELLED ,BILLABLEWEIGHT,TOTALTRANSPORTCHARGES ,BARCODENUM"
							+ "		   ,REMARKS,CONTAINERID from ["+dbNAme+"].[dbo].[WEIGHT] tt "
									+ "where TRNO is not null and NETWT is not null and NETWT <> '' and ( datein = '"+date+ "' or datein = '"+date2+"' or  datein = '"+date3+ "' or datein = '"+date4+"' ) ";
									
					int arrSize1 = 0;
				    if(!StringUtils.isEmpty(siteIDs) && !StringUtils.isEmpty(siteIDs.getWb_site_id())) {
				    	data = data + " and SITEID in("+siteIDs.getWb_site_id()+")";
						arrSize1++;
					}
				    data = data + " order by TRNO desc  ";
				 
					Object[] pValues1 = new Object[arrSize1];
					int j = 0;
					if(!StringUtils.isEmpty(siteIDs) && !StringUtils.isEmpty(siteIDs.getWb_site_id())) {
						pValues[j++] = siteIDs.getWb_site_id();
					}
					objsList1 = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
				}

				siteIDs.setTransactionsList(objsList1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList;
	}

	public List<DashBoardWeighBridge> getLogsList(DashBoardWeighBridge obj) throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		List<DashBoardWeighBridge> objsList1 = new ArrayList<DashBoardWeighBridge>();
		try {
			String qry = "SELECT sbu,db_name,table_name,status,project , convert(varchar, getdate(), 106) as curDAte FROM MasterDB.dbo.log_master mt  "
					+ " where  mt.table_name is not null and  mt.table_name <> '' and  table_name is not null and status <> 'Inactive'  order by sbu desc"; 
			int arrSize = 0;
			 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
					qry = qry + " AND sbu = ? ";
					arrSize++;
				}
			 Object[] pValues1 = new Object[arrSize];
				int j = 0;
				 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
					 pValues1[j++] = obj.getSbu();
				}
			objsList = jdbcTemplate.query( qry,pValues1, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
			for (DashBoardWeighBridge list : objsList) {
				String data = ""
						+ "select TOP (1) convert(varchar, PTCDT, 0) as PTCDT, convert(varchar, getdate(), 106) as curDAte,DATEDIFF(day, [PTCDT], getdate()) AS days_diff from ["+list.getDb_name()+"].[dbo].["+list.getTable_name()+"] tt ";
			    data = data + " order by PTCDT desc  ";
				objsList1 = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
				list.setTransactionsList(objsList1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList;
	}

	public List<DashBoardWeighBridge> getWeighBridgeList(DashBoardWeighBridge obj) {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		String qry = "SELECT company,sbu,project,project_name,location,project_status,no_of_wb,wb_site_id,db_name,table_name ,api_status,api_consumed_by"
				+ ",developed_by FROM [MasterDB].[dbo].[master_table] mt "
				+ " where  mt.location is not null and  mt.location <> '' and  wb_site_id is not null  order by sbu desc"; 
		int arrSize1 = 0;
		 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
				qry = qry + " AND sbu = ? ";
				arrSize1++;
			}
		 Object[] pValues1 = new Object[arrSize1];
			int j = 0;
			 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
				 pValues1[j++] = obj.getSbu();
			}
		objsList = jdbcTemplate.query( qry,pValues1, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
		return objsList;
	}

	public List<DashBoardWeighBridge> getMSWManualDataWithSiteID(DashBoardWeighBridge obj) {
		 String  fromDateDash = null;
		 String  fromDateSlash = null;
		 String toDateSlash = null;
		 String toDateDash = null;
		 
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		 
		List<DashBoardWeighBridge> objsList2 = new ArrayList<DashBoardWeighBridge>();
		String data = new String("");
		String dbNAme = "All_MSW_SITES";
		if("CND".equalsIgnoreCase(obj.getSbu())) {
			dbNAme = "All_CnD_Sites";
		}
		 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getFrom_date())) {
		data = ""
				+ "select TRNO,VEHICLENO ,CONVERT(varchar(9), DATEIN, 105) AS DATEIN,"
						+ "MATERIAL,PARTY,TRANSPORTER,BILLDCNO,BILLWEIGHT,TIMEIN,FIRSTWEIGHT,USER1,"
						+ "CONVERT(varchar(9), DATEOUT, 105) AS DATEOUT,TIMEOUT,SECONDWEIGHT,USER2,SITEID,STATUS,FIRSTFRONTPOTO"
				+ "		   ,FIRSTBACKPOTO,SECONDFRONTPOTO,SECONDBACKPOTO,NETWT,SW_SITEID,TRIPNO,SHIFTNO,TRANSFERWASTEIE ,TRANSFERWASTE,MANIFESTNUMBER ,MANIFESTWEIGHT,MEMBERSHIPCODE"
				+ "		   ,INGATEPASSNO ,INMETERREADING,OUTGATEPASSNO,OUTMETERREADING ,TRANSFERID,TYPEOFWASTE,TOTALKMSTRAVELLED ,BILLABLEWEIGHT,TOTALTRANSPORTCHARGES ,BARCODENUM"
				+ "		   ,REMARKS,CONTAINERID from ["+dbNAme+"].[dbo].[WEIGHT] tt "
						+ "where TRNO is not null and NETWT is not null and NETWT <> '' ";
						
		int arrSize1 = 1;
	    if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSITEID())) {
	    	data = data + " and SITEID in("+obj.getSITEID()+")";
			//arrSize1++;
		}
		 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getFrom_date())) {
			   fromDateDash = obj.getFrom_date();
			   fromDateSlash = obj.getFrom_date();
			 
			  ////  String originalFormat = "yyyy-MM-dd";
		      //  LocalDate date = LocalDate.parse(fromDateSlash, DateTimeFormatter.ofPattern(originalFormat));
		      //  String desiredFormat = "M/d/yyyy";
		     //   fromDateSlash = date.format(DateTimeFormatter.ofPattern(desiredFormat))+" 12:00:00 AM";
		
		        
			 if(StringUtils.isEmpty(obj.getTo_date())) {
				 obj.setTo_date(obj.getFrom_date());
			 }else {
				    toDateSlash = obj.getTo_date();
				    toDateDash = obj.getTo_date();
				 //   originalFormat =  "yyyy-MM-dd";
			   ///     date = LocalDate.parse(toDateSlash, DateTimeFormatter.ofPattern(originalFormat));
			    //    desiredFormat = "M/d/yyyy";
			   //     toDateSlash = date.format(DateTimeFormatter.ofPattern(desiredFormat))+" 12:00:00 AM";
			     
			 }
			 	data = data + " AND (TRY_CAST(DATEIN AS date) between '"+fromDateSlash+"' and '"+toDateSlash+"')  ";
				arrSize1++;
				arrSize1++;
			
			}
	    data = data + " order by TRNO ";
	
		objsList = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
	
		
		 }
		return objsList;
	}
	
	
	
	
	
}
