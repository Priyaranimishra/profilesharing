package com.nelson.ecd.profitsharing.api.model;

public class Participant {

	private String ageCalculationMethod;
	private String profitSharingMethod;
	private String serviceStartDateCalculationMethod;
	private double basePct;
	private double basePctAmt;
	private String planPeriodStartDate;
	private String planPeriodEndDate;
	private String cycleEffectiveDate;
	private double profitSharingFlatAmt;
	private double profitSharingTotalAmtForProration;
	
	private String plan;
	private Long participantId;
	private String participantName;
	private String division;
	private String payrollOneCd;
	private String payrollTwoCd;
	private String dateOfBirth;
	private String dateOfHire;
	private String planEntryDate;
	private double basicPay;
	private double sumBasicPayAllParticipants;
	private double bonus;
	private double compensation;

	private double accountBalance;
	private double sumAccountBalanceAllParticipants;
	
	private int participantCalculatedServiceYrs;
	private int participantCalculatedAge;

	private int participantEligibleServiceYrsDT;
	private int participantEligibleAgeDT;

	private double profitSharingAmt;
	
	private double ageTierPct;
	private double serviceYrsTierPct;
	private double compensationTierPct;
	
	private boolean isEligible;
    private String status;
    private String statusDescription;

	public double getSumBasicPayAllParticipants() {
		return sumBasicPayAllParticipants;
	}

	public void setSumBasicPayAllParticipants(double sumBasicPayAllParticipants) {
		this.sumBasicPayAllParticipants = sumBasicPayAllParticipants;
	}

	public double getSumAccountBalanceAllParticipants() {
		return sumAccountBalanceAllParticipants;
	}

	public void setSumAccountBalanceAllParticipants(double sumAccountBalanceAllParticipants) {
		this.sumAccountBalanceAllParticipants = sumAccountBalanceAllParticipants;
	}

	public String getAgeCalculationMethod() {
		return ageCalculationMethod;
	}

	public void setAgeCalculationMethod(String ageCalculationMethod) {
		this.ageCalculationMethod = ageCalculationMethod;
	}

	public String getProfitSharingMethod() {
		return profitSharingMethod;
	}

	public void setProfitSharingMethod(String profitSharingMethod) {
		this.profitSharingMethod = profitSharingMethod;
	}

	public String getServiceStartDateCalculationMethod() {
		return serviceStartDateCalculationMethod;
	}

	public void setServiceStartDateCalculationMethod(String serviceStartDateCalculationMethod) {
		this.serviceStartDateCalculationMethod = serviceStartDateCalculationMethod;
	}

	public double getBasePct() {
		return basePct;
	}

	public void setBasePct(double basePct) {
		this.basePct = basePct;
	}

	public String getPlanPeriodStartDate() {
		return planPeriodStartDate;
	}

	public void setPlanPeriodStartDate(String planPeriodStartDate) {
		this.planPeriodStartDate = planPeriodStartDate;
	}

	public String getPlanPeriodEndDate() {
		return planPeriodEndDate;
	}

	public void setPlanPeriodEndDate(String planPeriodEndDate) {
		this.planPeriodEndDate = planPeriodEndDate;
	}

	public String getCycleEffectiveDate() {
		return cycleEffectiveDate;
	}

	public void setCycleEffectiveDate(String cycleEffectiveDate) {
		this.cycleEffectiveDate = cycleEffectiveDate;
	}

	public double getProfitSharingFlatAmt() {
		return profitSharingFlatAmt;
	}

	public void setProfitSharingFlatAmt(double profitSharingFlatAmt) {
		this.profitSharingFlatAmt = profitSharingFlatAmt;
	}

	public double getProfitSharingTotalAmtForProration() {
		return profitSharingTotalAmtForProration;
	}

	public void setProfitSharingTotalAmtForProration(double profitSharingTotalAmtForProration) {
		this.profitSharingTotalAmtForProration = profitSharingTotalAmtForProration;
	}

	public double getBasePctAmt() {
		return basePctAmt;
	}

	public void setBasePctAmt(double basePctAmt) {
		this.basePctAmt = basePctAmt;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getPayrollOneCd() {
		return payrollOneCd;
	}

	public void setPayrollOneCd(String payrollOneCd) {
		this.payrollOneCd = payrollOneCd;
	}

	public String getPayrollTwoCd() {
		return payrollTwoCd;
	}

	public void setPayrollTwoCd(String payrollTwoCd) {
		this.payrollTwoCd = payrollTwoCd;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDateOfHire() {
		return dateOfHire;
	}

	public void setDateOfHire(String dateOfHire) {
		this.dateOfHire = dateOfHire;
	}

	public String getPlanEntryDate() {
		return planEntryDate;
	}

	public void setPlanEntryDate(String planEntryDate) {
		this.planEntryDate = planEntryDate;
	}

	public double getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(double basicPay) {
		this.basicPay = basicPay;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public double getCompensation() {
		return compensation;
	}

	public void setCompensation(double compensation) {
		this.compensation = compensation;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public int getParticipantCalculatedServiceYrs() {
		return participantCalculatedServiceYrs;
	}

	public void setParticipantCalculatedServiceYrs(int participantCalculatedServiceYrs) {
		this.participantCalculatedServiceYrs = participantCalculatedServiceYrs;
	}

	public int getParticipantCalculatedAge() {
		return participantCalculatedAge;
	}

	public void setParticipantCalculatedAge(int participantCalculatedAge) {
		this.participantCalculatedAge = participantCalculatedAge;
	}

	public int getParticipantEligibleServiceYrsDT() {
		return participantEligibleServiceYrsDT;
	}

	public void setParticipantEligibleServiceYrsDT(int participantEligibleServiceYrsDT) {
		this.participantEligibleServiceYrsDT = participantEligibleServiceYrsDT;
	}

	public int getParticipantEligibleAgeDT() {
		return participantEligibleAgeDT;
	}

	public void setParticipantEligibleAgeDT(int participantEligibleAgeDT) {
		this.participantEligibleAgeDT = participantEligibleAgeDT;
	}

	public double getProfitSharingAmt() {
		return profitSharingAmt;
	}

	public void setProfitSharingAmt(double profitSharingAmt) {
		this.profitSharingAmt = profitSharingAmt;
	}

	public double getAgeTierPct() {
		return ageTierPct;
	}

	public void setAgeTierPct(double ageTierPct) {
		this.ageTierPct = ageTierPct;
	}

	public double getServiceYrsTierPct() {
		return serviceYrsTierPct;
	}

	public void setServiceYrsTierPct(double serviceYrsTierPct) {
		this.serviceYrsTierPct = serviceYrsTierPct;
	}

	public double getCompensationTierPct() {
		return compensationTierPct;
	}

	public void setCompensationTierPct(double compensationTierPct) {
		this.compensationTierPct = compensationTierPct;
	}

	public boolean isEligible() {
		return isEligible;
	}

	public void setEligible(boolean isEligible) {
		this.isEligible = isEligible;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

}
