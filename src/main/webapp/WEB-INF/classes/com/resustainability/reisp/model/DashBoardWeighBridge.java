package com.resustainability.reisp.model;

import java.util.List;

public class DashBoardWeighBridge {

	private String   UID,plant,cabsTransID,actualVisitDate,indicator_name,
	route,customerTown,vehicleRegNo,aPIType,aPIID,visitDayTime
		      ,TRNO,VEHICLENO,MATERIAL,count,min,dmNmae,days_diff,PARTY,all_wb,all_sites,visitDays,customerStatus,TRANSPORTER,curDAte,BILLDCNO,BILLWEIGHT,DATEIN,TIMEIN,FIRSTWEIGHT,USER1,DATEOUT,TIMEOUT,SECONDWEIGHT,USER2,SITEID,STATUS,FIRSTFRONTPOTO
		   ,FIRSTBACKPOTO,SECONDFRONTPOTO,SECONDBACKPOTO,NETWT,SW_SITEID,TRIPNO,SHIFTNO,TRANSFERWASTEIE ,TRANSFERWASTE,MANIFESTNUMBER ,MANIFESTWEIGHT,MEMBERSHIPCODE
		   ,INGATEPASSNO ,INMETERREADING,OUTGATEPASSNO,OUTMETERREADING ,TRANSFERID,TYPEOFWASTE,TOTALKMSTRAVELLED ,BILLABLEWEIGHT,TOTALTRANSPORTCHARGES ,BARCODENUM
		   ,Remarks,FIRSTWEIGHTACTUAL,TYPEOFVEHICLE ,DRIVERNAME,status ,WeightTransNo,VEHICLECAPACITY,id ,weight_TRNO,PTC_status ,PTCDT ,MSG,user_ip ,CONTAINERID,REMARKS,
		    	    	    	      company ,greenWB,no_of_wbR,no_of_wb2
		    	    	    	    	      ,sbu
		    	    	    	    	      ,project
		    	    	    	    	      ,project_name
		    	    	    	    	      ,location
		    	    	    	    	      ,project_status
		    	    	    	    	      ,no_of_wb
		    	    	    	    	      ,wb_site_id
		    	    	    	    	      ,db_name
		    	    	    	    	      ,table_name
		    	    	    	    	      ,api_status
		    	    	    	    	      ,api_consumed_by
		    	    	    	    	      ,developed_by,dailyCount,to_date,from_date;
	
	List<DashBoardWeighBridge> transactionsList;
	
	
	public String getVisitDayTime() {
		return visitDayTime;
	}

	public void setVisitDayTime(String visitDayTime) {
		this.visitDayTime = visitDayTime;
	}

	public String getVisitDays() {
		return visitDays;
	}

	public void setVisitDays(String visitDays) {
		this.visitDays = visitDays;
	}

	public String getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}

	public String getIndicator_name() {
		return indicator_name;
	}

	public void setIndicator_name(String indicator_name) {
		this.indicator_name = indicator_name;
	}

	public String getAll_wb() {
		return all_wb;
	}

	public void setAll_wb(String all_wb) {
		this.all_wb = all_wb;
	}

	public String getCabsTransID() {
		return cabsTransID;
	}

	public void setCabsTransID(String cabsTransID) {
		this.cabsTransID = cabsTransID;
	}

	public String getActualVisitDate() {
		return actualVisitDate;
	}

	public void setActualVisitDate(String actualVisitDate) {
		this.actualVisitDate = actualVisitDate;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getCustomerTown() {
		return customerTown;
	}

	public void setCustomerTown(String customerTown) {
		this.customerTown = customerTown;
	}

	public String getVehicleRegNo() {
		return vehicleRegNo;
	}

	public void setVehicleRegNo(String vehicleRegNo) {
		this.vehicleRegNo = vehicleRegNo;
	}

	public String getaPIType() {
		return aPIType;
	}

	public void setaPIType(String aPIType) {
		this.aPIType = aPIType;
	}

	public String getaPIID() {
		return aPIID;
	}

	public void setaPIID(String aPIID) {
		this.aPIID = aPIID;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getDmNmae() {
		return dmNmae;
	}

	public void setDmNmae(String dmNmae) {
		this.dmNmae = dmNmae;
	}

	public String getNo_of_wb2() {
		return no_of_wb2;
	}

	public void setNo_of_wb2(String no_of_wb2) {
		this.no_of_wb2 = no_of_wb2;
	}

	public String getNo_of_wbR() {
		return no_of_wbR;
	}

	public void setNo_of_wbR(String no_of_wbR) {
		this.no_of_wbR = no_of_wbR;
	}

	public String getGreenWB() {
		return greenWB;
	}

	public void setGreenWB(String greenWB) {
		this.greenWB = greenWB;
	}

	public String getAll_sites() {
		return all_sites;
	}

	public void setAll_sites(String all_sites) {
		this.all_sites = all_sites;
	}

	public String getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getCONTAINERID() {
		return CONTAINERID;
	}

	public void setCONTAINERID(String cONTAINERID) {
		CONTAINERID = cONTAINERID;
	}

	public String getWeightTransNo() {
		return WeightTransNo;
	}

	public void setWeightTransNo(String weightTransNo) {
		WeightTransNo = weightTransNo;
	}

	public String getDays_diff() {
		return days_diff;
	}

	public void setDays_diff(String days_diff) {
		this.days_diff = days_diff;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DashBoardWeighBridge> getTransactionsList() {
		return transactionsList;
	}

	public void setTransactionsList(List<DashBoardWeighBridge> transactionsList) {
		this.transactionsList = transactionsList;
	}

	public String getDailyCount() {
		return dailyCount;
	}

	public void setDailyCount(String dailyCount) {
		this.dailyCount = dailyCount;
	}

	public String getCurDAte() {
		return curDAte;
	}

	public void setCurDAte(String curDAte) {
		this.curDAte = curDAte;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSbu() {
		return sbu;
	}

	public void setSbu(String sbu) {
		this.sbu = sbu;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProject_status() {
		return project_status;
	}

	public void setProject_status(String project_status) {
		this.project_status = project_status;
	}

	public String getNo_of_wb() {
		return no_of_wb;
	}

	public void setNo_of_wb(String no_of_wb) {
		this.no_of_wb = no_of_wb;
	}

	public String getWb_site_id() {
		return wb_site_id;
	}

	public void setWb_site_id(String wb_site_id) {
		this.wb_site_id = wb_site_id;
	}

	public String getDb_name() {
		return db_name;
	}

	public void setDb_name(String db_name) {
		this.db_name = db_name;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getApi_status() {
		return api_status;
	}

	public void setApi_status(String api_status) {
		this.api_status = api_status;
	}

	public String getApi_consumed_by() {
		return api_consumed_by;
	}

	public void setApi_consumed_by(String api_consumed_by) {
		this.api_consumed_by = api_consumed_by;
	}

	public String getDeveloped_by() {
		return developed_by;
	}

	public void setDeveloped_by(String developed_by) {
		this.developed_by = developed_by;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getTRNO() {
		return TRNO;
	}

	public void setTRNO(String tRNO) {
		TRNO = tRNO;
	}

	public String getVEHICLENO() {
		return VEHICLENO;
	}

	public void setVEHICLENO(String vEHICLENO) {
		VEHICLENO = vEHICLENO;
	}

	public String getMATERIAL() {
		return MATERIAL;
	}

	public void setMATERIAL(String mATERIAL) {
		MATERIAL = mATERIAL;
	}

	public String getPARTY() {
		return PARTY;
	}

	public void setPARTY(String pARTY) {
		PARTY = pARTY;
	}

	public String getTRANSPORTER() {
		return TRANSPORTER;
	}

	public void setTRANSPORTER(String tRANSPORTER) {
		TRANSPORTER = tRANSPORTER;
	}

	public String getBILLDCNO() {
		return BILLDCNO;
	}

	public void setBILLDCNO(String bILLDCNO) {
		BILLDCNO = bILLDCNO;
	}

	public String getBILLWEIGHT() {
		return BILLWEIGHT;
	}

	public void setBILLWEIGHT(String bILLWEIGHT) {
		BILLWEIGHT = bILLWEIGHT;
	}

	public String getDATEIN() {
		return DATEIN;
	}

	public void setDATEIN(String dATEIN) {
		DATEIN = dATEIN;
	}

	public String getTIMEIN() {
		return TIMEIN;
	}

	public void setTIMEIN(String tIMEIN) {
		TIMEIN = tIMEIN;
	}

	public String getFIRSTWEIGHT() {
		return FIRSTWEIGHT;
	}

	public void setFIRSTWEIGHT(String fIRSTWEIGHT) {
		FIRSTWEIGHT = fIRSTWEIGHT;
	}

	public String getUSER1() {
		return USER1;
	}

	public void setUSER1(String uSER1) {
		USER1 = uSER1;
	}

	public String getDATEOUT() {
		return DATEOUT;
	}

	public void setDATEOUT(String dATEOUT) {
		DATEOUT = dATEOUT;
	}

	public String getTIMEOUT() {
		return TIMEOUT;
	}

	public void setTIMEOUT(String tIMEOUT) {
		TIMEOUT = tIMEOUT;
	}

	public String getSECONDWEIGHT() {
		return SECONDWEIGHT;
	}

	public void setSECONDWEIGHT(String sECONDWEIGHT) {
		SECONDWEIGHT = sECONDWEIGHT;
	}

	public String getUSER2() {
		return USER2;
	}

	public void setUSER2(String uSER2) {
		USER2 = uSER2;
	}

	public String getSITEID() {
		return SITEID;
	}

	public void setSITEID(String sITEID) {
		SITEID = sITEID;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getFIRSTFRONTPOTO() {
		return FIRSTFRONTPOTO;
	}

	public void setFIRSTFRONTPOTO(String fIRSTFRONTPOTO) {
		FIRSTFRONTPOTO = fIRSTFRONTPOTO;
	}

	public String getFIRSTBACKPOTO() {
		return FIRSTBACKPOTO;
	}

	public void setFIRSTBACKPOTO(String fIRSTBACKPOTO) {
		FIRSTBACKPOTO = fIRSTBACKPOTO;
	}

	public String getSECONDFRONTPOTO() {
		return SECONDFRONTPOTO;
	}

	public void setSECONDFRONTPOTO(String sECONDFRONTPOTO) {
		SECONDFRONTPOTO = sECONDFRONTPOTO;
	}

	public String getSECONDBACKPOTO() {
		return SECONDBACKPOTO;
	}

	public void setSECONDBACKPOTO(String sECONDBACKPOTO) {
		SECONDBACKPOTO = sECONDBACKPOTO;
	}

	public String getNETWT() {
		return NETWT;
	}

	public void setNETWT(String nETWT) {
		NETWT = nETWT;
	}

	public String getSW_SITEID() {
		return SW_SITEID;
	}

	public void setSW_SITEID(String sW_SITEID) {
		SW_SITEID = sW_SITEID;
	}

	public String getTRIPNO() {
		return TRIPNO;
	}

	public void setTRIPNO(String tRIPNO) {
		TRIPNO = tRIPNO;
	}

	public String getSHIFTNO() {
		return SHIFTNO;
	}

	public void setSHIFTNO(String sHIFTNO) {
		SHIFTNO = sHIFTNO;
	}

	public String getTRANSFERWASTEIE() {
		return TRANSFERWASTEIE;
	}

	public void setTRANSFERWASTEIE(String tRANSFERWASTEIE) {
		TRANSFERWASTEIE = tRANSFERWASTEIE;
	}

	public String getTRANSFERWASTE() {
		return TRANSFERWASTE;
	}

	public void setTRANSFERWASTE(String tRANSFERWASTE) {
		TRANSFERWASTE = tRANSFERWASTE;
	}

	public String getMANIFESTNUMBER() {
		return MANIFESTNUMBER;
	}

	public void setMANIFESTNUMBER(String mANIFESTNUMBER) {
		MANIFESTNUMBER = mANIFESTNUMBER;
	}

	public String getMANIFESTWEIGHT() {
		return MANIFESTWEIGHT;
	}

	public void setMANIFESTWEIGHT(String mANIFESTWEIGHT) {
		MANIFESTWEIGHT = mANIFESTWEIGHT;
	}

	public String getMEMBERSHIPCODE() {
		return MEMBERSHIPCODE;
	}

	public void setMEMBERSHIPCODE(String mEMBERSHIPCODE) {
		MEMBERSHIPCODE = mEMBERSHIPCODE;
	}

	public String getINGATEPASSNO() {
		return INGATEPASSNO;
	}

	public void setINGATEPASSNO(String iNGATEPASSNO) {
		INGATEPASSNO = iNGATEPASSNO;
	}

	public String getINMETERREADING() {
		return INMETERREADING;
	}

	public void setINMETERREADING(String iNMETERREADING) {
		INMETERREADING = iNMETERREADING;
	}

	public String getOUTGATEPASSNO() {
		return OUTGATEPASSNO;
	}

	public void setOUTGATEPASSNO(String oUTGATEPASSNO) {
		OUTGATEPASSNO = oUTGATEPASSNO;
	}

	public String getOUTMETERREADING() {
		return OUTMETERREADING;
	}

	public void setOUTMETERREADING(String oUTMETERREADING) {
		OUTMETERREADING = oUTMETERREADING;
	}

	public String getTRANSFERID() {
		return TRANSFERID;
	}

	public void setTRANSFERID(String tRANSFERID) {
		TRANSFERID = tRANSFERID;
	}

	public String getTYPEOFWASTE() {
		return TYPEOFWASTE;
	}

	public void setTYPEOFWASTE(String tYPEOFWASTE) {
		TYPEOFWASTE = tYPEOFWASTE;
	}

	public String getTOTALKMSTRAVELLED() {
		return TOTALKMSTRAVELLED;
	}

	public void setTOTALKMSTRAVELLED(String tOTALKMSTRAVELLED) {
		TOTALKMSTRAVELLED = tOTALKMSTRAVELLED;
	}

	public String getBILLABLEWEIGHT() {
		return BILLABLEWEIGHT;
	}

	public void setBILLABLEWEIGHT(String bILLABLEWEIGHT) {
		BILLABLEWEIGHT = bILLABLEWEIGHT;
	}

	public String getTOTALTRANSPORTCHARGES() {
		return TOTALTRANSPORTCHARGES;
	}

	public void setTOTALTRANSPORTCHARGES(String tOTALTRANSPORTCHARGES) {
		TOTALTRANSPORTCHARGES = tOTALTRANSPORTCHARGES;
	}

	public String getBARCODENUM() {
		return BARCODENUM;
	}

	public void setBARCODENUM(String bARCODENUM) {
		BARCODENUM = bARCODENUM;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public String getFIRSTWEIGHTACTUAL() {
		return FIRSTWEIGHTACTUAL;
	}

	public void setFIRSTWEIGHTACTUAL(String fIRSTWEIGHTACTUAL) {
		FIRSTWEIGHTACTUAL = fIRSTWEIGHTACTUAL;
	}

	public String getTYPEOFVEHICLE() {
		return TYPEOFVEHICLE;
	}

	public void setTYPEOFVEHICLE(String tYPEOFVEHICLE) {
		TYPEOFVEHICLE = tYPEOFVEHICLE;
	}

	public String getDRIVERNAME() {
		return DRIVERNAME;
	}

	public void setDRIVERNAME(String dRIVERNAME) {
		DRIVERNAME = dRIVERNAME;
	}

	public String getVEHICLECAPACITY() {
		return VEHICLECAPACITY;
	}

	public void setVEHICLECAPACITY(String vEHICLECAPACITY) {
		VEHICLECAPACITY = vEHICLECAPACITY;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWeight_TRNO() {
		return weight_TRNO;
	}

	public void setWeight_TRNO(String weight_TRNO) {
		this.weight_TRNO = weight_TRNO;
	}

	public String getPTC_status() {
		return PTC_status;
	}

	public void setPTC_status(String pTC_status) {
		PTC_status = pTC_status;
	}

	public String getPTCDT() {
		return PTCDT;
	}

	public void setPTCDT(String pTCDT) {
		PTCDT = pTCDT;
	}

	public String getMSG() {
		return MSG;
	}

	public void setMSG(String mSG) {
		MSG = mSG;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	public String getREMARKS() {
		return REMARKS;
	}

	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}
	
	
	
	
	
} 
