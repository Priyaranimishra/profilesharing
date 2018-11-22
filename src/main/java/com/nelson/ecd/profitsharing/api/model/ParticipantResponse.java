package com.nelson.ecd.profitsharing.api.model;

public class ParticipantResponse {

	private String plan;
	private Long participantId;
	private String participantName;
	private double basicPay;

	private int age;
	private int serviceYrs;
	
	private String ageTierPct;
	private String serviceYrsTierPct;
	private String compensationTierPct;
	private String flatAmt;
	private String profitSharingAmt;
	
	private boolean isEligible;
    private String status;
    private String statusDescription;
    
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
	public double getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(double basicPay) {
		this.basicPay = basicPay;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getServiceYrs() {
		return serviceYrs;
	}
	public void setServiceYrs(int serviceYrs) {
		this.serviceYrs = serviceYrs;
	}
	public String getAgeTierPct() {
		return ageTierPct;
	}
	public void setAgeTierPct(String ageTierPct) {
		this.ageTierPct = ageTierPct;
	}
	public String getServiceYrsTierPct() {
		return serviceYrsTierPct;
	}
	public void setServiceYrsTierPct(String serviceYrsTierPct) {
		this.serviceYrsTierPct = serviceYrsTierPct;
	}
	public String getCompensationTierPct() {
		return compensationTierPct;
	}
	public void setCompensationTierPct(String compensationTierPct) {
		this.compensationTierPct = compensationTierPct;
	}
	public String getFlatAmt() {
		return flatAmt;
	}
	public void setFlatAmt(String flatAmt) {
		this.flatAmt = flatAmt;
	}
	public String getProfitSharingAmt() {
		return profitSharingAmt;
	}
	public void setProfitSharingAmt(String profitSharingAmt) {
		this.profitSharingAmt = profitSharingAmt;
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
	@Override
	public String toString() {
		return "ParticipantResponse [planId=" + plan + ", participantId=" + participantId + ", participantName="
				+ participantName + ", basicPay=" + basicPay + ", age=" + age + ", serviceYrs=" + serviceYrs
				+ ", ageTierPct=" + ageTierPct + ", serviceYrsTierPct=" + serviceYrsTierPct + ", compensationTierPct="
				+ compensationTierPct + ", flatAmt=" + flatAmt + ", profitSharingAmt=" + profitSharingAmt + ", status="
				+ status + ", statusDescription=" + statusDescription + "]";
	}
	public boolean isEligible() {
		return isEligible;
	}
	public void setEligible(boolean isEligible) {
		this.isEligible = isEligible;
	}    

}