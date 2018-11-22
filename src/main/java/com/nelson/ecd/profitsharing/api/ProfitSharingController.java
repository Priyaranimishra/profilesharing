package com.nelson.ecd.profitsharing.api;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nelson.ecd.profitsharing.api.model.Participant;
import com.nelson.ecd.profitsharing.api.model.ParticipantResponse;
import com.nelson.ecd.profitsharing.api.service.ProfitSharingRuleEngineService;
import com.nelson.ecd.profitsharing.config.Constants;

@RestController
public class ProfitSharingController {

	private final ProfitSharingRuleEngineService profitSharingRuleEngineService;

	private final Logger logger = LoggerFactory.getLogger(ProfitSharingController.class);
	private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE_TIME;

	@Autowired
	public ProfitSharingController(ProfitSharingRuleEngineService profitSharingRuleEngineService) {
		this.profitSharingRuleEngineService = profitSharingRuleEngineService;
	}

	@RequestMapping(value = "/performProfitSharingCalc", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ParticipantResponse getProfitSharingDataList(@RequestBody Participant participant) {
		
		if (isValidParticipant(participant)) {
			setEligibleAge(participant);
			setEligibleServiceYrs(participant);

			profitSharingRuleEngineService.filterEligibleParticipant(participant);

			
			logger.debug("Participant eligible/ineligible ={}", participant.isEligible());

			if (participant.isEligible()) {
				calculateProfitSharingAmtBasedOnPaymentMethod(participant);
			}
			
			// ELK log
			logger.debug("planId :{}, participantId :{}, BasePct :{}, "
					+ "sumBasicPayAllParticipants :{}, sumAccountBalanceAllParticipants :{}, profitSharingFlatAmt :{}, "
					+ "AccountBalance :{}, BasicPay :{}, Age :{}, ServiceYrs :{}, ProfitSharingAmt :{}, eligible :{}",
					participant.getPlan(),
					participant.getParticipantId(),
					participant.getBasePct(), 
					participant.getSumBasicPayAllParticipants(), participant.getSumAccountBalanceAllParticipants(), participant.getProfitSharingFlatAmt(),
					participant.getAccountBalance(), participant.getBasicPay(), 
					participant.getParticipantCalculatedAge(), participant.getParticipantCalculatedServiceYrs(), 
					participant.getProfitSharingAmt(), 
					participant.isEligible());

		} else {
			logger.debug("Validations Failed for Participant Id={}", participant.getParticipantId());
		}
		
		return getResponse(participant);
	}

	private void calculateProfitSharingAmtBasedOnPaymentMethod(Participant participant) {
		if (participant.getProfitSharingMethod().equals(Constants.PROFIT_SHARING_METHOD_FLAT_AMT)) {
			updateProfitBasedOnFlatAmt(participant);

		} else if (participant.getProfitSharingMethod().equals(Constants.PROFIT_SHARING_METHOD_PRORATED_BY_COMPENSATION)) {
			updateProfitBasedOnProratedByCompensation(participant);

		} else if (participant.getProfitSharingMethod().equals(Constants.PROFIT_SHARING_METHOD_PRORATED_BY_ACCT_BALANCE)) {
			updateProfitBasedOnProratedByAcctBalance(participant);

		} else if (participant.getProfitSharingMethod().equals(Constants.PROFIT_SHARING_METHOD_BASED_ON_PCT_COMPENSATION)) {
			updateProfitSharingBasedOnPctCompensation(participant);

		} else if (participant.getProfitSharingMethod().equals(Constants.PROFIT_SHARING_METHOD_BASED_ON_AGE_SERVICE_COMPENSATION)) {
			updateProfitSharingBasedOnAgeServiceCompensation(participant);

		}
	}

	private void updateProfitSharingBasedOnAgeServiceCompensation(Participant participant) {
			double basePctCompensationAmt = participant.getBasicPay() * participant.getBasePct()/100;
			participant.setBasePctAmt(basePctCompensationAmt);
			participant.setProfitSharingAmt(basePctCompensationAmt);

			profitSharingRuleEngineService.performProfitSharingTierBasedCalculations(participant);

			logger.debug("planId ={}, participantId ={}, base% of BasicPay ={}, AccountBalance ={}, BasicPay ={}, Age ={}, Service Yrs ={}, Calculated Profit Sharing Amount ={}",
					participant.getPlan(),
					participant.getParticipantId(),
					participant.getBasePct(), 
					participant.getAccountBalance(), participant.getBasicPay(), 
					participant.getParticipantCalculatedAge(), participant.getParticipantCalculatedServiceYrs(),
					participant.getProfitSharingAmt());
	}

	private void updateProfitSharingBasedOnPctCompensation(Participant participant) {
			double basePctCompensationAmt = participant.getBasicPay() * participant.getBasePct()/100;
			participant.setBasePctAmt(basePctCompensationAmt);
			participant.setProfitSharingAmt(basePctCompensationAmt);

			logger.debug("planId ={}, participantId ={}, base% of BasicPay ={}, AccountBalance ={}, BasicPay ={}, Age ={}, Service Yrs ={}, Calculated Profit Sharing Amount ={}",
					participant.getPlan(),
					participant.getParticipantId(),
					participant.getBasePct(), 
					participant.getAccountBalance(), participant.getBasicPay(), 
					participant.getParticipantCalculatedAge(), participant.getParticipantCalculatedServiceYrs(),
					participant.getProfitSharingAmt());

	}

	private void updateProfitBasedOnProratedByAcctBalance(Participant participant) {
			double profitSharingAmt = (participant.getAccountBalance() * participant.getProfitSharingTotalAmtForProration())
					/ participant.getSumAccountBalanceAllParticipants();
			participant.setProfitSharingAmt(profitSharingAmt);

			logger.debug("planId ={}, participantId ={}, base% of BasicPay ={}, AccountBalance ={}, BasicPay ={}, Age ={}, Service Yrs ={}, Calculated Profit Sharing Amount ={}",
					participant.getPlan(),
					participant.getParticipantId(),
					participant.getBasePct(), 
					participant.getAccountBalance(), participant.getBasicPay(), 
					participant.getParticipantCalculatedAge(), participant.getParticipantCalculatedServiceYrs(),
					participant.getProfitSharingAmt());

	}

	private void updateProfitBasedOnProratedByCompensation(Participant participant) {
			double profitSharingAmt = (participant.getBasicPay()* participant.getProfitSharingTotalAmtForProration())
					/ participant.getSumBasicPayAllParticipants();
			participant.setProfitSharingAmt(profitSharingAmt);

			logger.debug("planId ={}, participantId ={}, base% of BasicPay ={}, AccountBalance ={}, BasicPay ={}, Age ={}, Service Yrs ={}, Calculated Profit Sharing Amount ={}",
					participant.getPlan(),
					participant.getParticipantId(),
					participant.getBasePct(), 
					participant.getAccountBalance(), participant.getBasicPay(), 
					participant.getParticipantCalculatedAge(), participant.getParticipantCalculatedServiceYrs(),
					participant.getProfitSharingAmt());

	}

	private void updateProfitBasedOnFlatAmt(Participant participant) {
		double profitSharingAmt = participant.getProfitSharingFlatAmt();
			participant.setProfitSharingAmt(profitSharingAmt);

			logger.debug("planId ={}, participantId ={}, base% of BasicPay ={}, AccountBalance ={}, BasicPay ={}, Age ={}, Service Yrs ={}, Calculated Profit Sharing Amount ={}",
					participant.getPlan(),
					participant.getParticipantId(),
					participant.getBasePct(), 
					participant.getAccountBalance(), participant.getBasicPay(), 
					participant.getParticipantCalculatedAge(), participant.getParticipantCalculatedServiceYrs(),
					participant.getProfitSharingAmt());

	}

	private ParticipantResponse getResponse(Participant participant) {

			ParticipantResponse participantResponse = new ParticipantResponse();

			participantResponse.setPlan(participant.getPlan());
			participantResponse.setParticipantId(participant.getParticipantId());
			participantResponse.setParticipantName(participant.getParticipantName());
			participantResponse.setBasicPay(participant.getBasicPay());

			participantResponse.setServiceYrs(participant.getParticipantCalculatedServiceYrs());
			participantResponse.setAge(participant.getParticipantCalculatedAge());

			participantResponse.setAgeTierPct(decimalFormat.format(participant.getAgeTierPct()));
			participantResponse.setServiceYrsTierPct(decimalFormat.format(participant.getServiceYrsTierPct()));
			participantResponse.setCompensationTierPct(decimalFormat.format(participant.getCompensationTierPct()));

			participantResponse.setFlatAmt(decimalFormat.format(participant.getProfitSharingFlatAmt()));
			participantResponse.setProfitSharingAmt(decimalFormat.format(participant.getProfitSharingAmt()));

			participantResponse.setEligible(participant.isEligible());
			
			participantResponse.setStatus(participant.getStatus());
			participantResponse.setStatusDescription(participant.getStatusDescription());
			
			return participantResponse;

	}

	private void setEligibleAge(Participant participant) {

		LocalDate currentDate = LocalDate.now();
		LocalDate dob = LocalDate.parse(participant.getDateOfBirth(), dateFormatter);

		int age = 0;
		if (participant.getAgeCalculationMethod().equals(Constants.EXACT_AGE_INDICATOR)) {
			age = Period.between(dob, currentDate).getYears();

		} else if (participant.getAgeCalculationMethod().equals(Constants.PLAN_PERIOD_AGE_INDICATOR)) {
			LocalDate planPeriodStartDate = LocalDate.parse(participant.getPlanPeriodStartDate(), dateFormatter);
			age = Period.between(dob, planPeriodStartDate).getYears();

		} else {
			age = 0;

		}
		logger.debug("ParticipantId ={}, Calculated Eligible Age ={}", participant.getParticipantId(), age);
		participant.setParticipantCalculatedAge(age);
	}

	private void setEligibleServiceYrs(Participant participant) {
		String serviceStartDateStr = getServiceStartDate(participant);
		int serviceYrs = 0;

		if (serviceStartDateStr != null) {
			LocalDate currentDate = LocalDate.now();
			LocalDate serviceStartDate = LocalDate.parse(serviceStartDateStr, dateFormatter);

			Period difference = Period.between(serviceStartDate, currentDate);
			serviceYrs = difference.getYears();
		}

		logger.debug("ParticipantId ={}, Calculated Eligible Service Years ={}", participant.getParticipantId(), serviceYrs);
		participant.setParticipantCalculatedServiceYrs(serviceYrs);
	}

	private String getServiceStartDate(Participant participant) {
		String serviceStartDate = null;
		if (participant.getServiceStartDateCalculationMethod().equals(Constants.SERVICE_START_DATE_DATE_OF_HIRE_INDICATOR)) {
			serviceStartDate = participant.getDateOfHire();

		} else if (participant.getServiceStartDateCalculationMethod().equals(Constants.SERVICE_START_DATE_PLAN_ENTRY_DATE_INDICATOR)) {
			serviceStartDate = participant.getPlanEntryDate();
		} 
		return serviceStartDate;
	}

	/*	public static void main(String a[]) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");

		LocalDate localDate = LocalDate.parse("25-09-2018", dateTimeFormatter);
		LocalDate localDate2 = LocalDate.parse("14-10-2018", dateTimeFormatter);
		LocalDate currentDate = LocalDate.now();

		System.out.println("Date :" + localDate);
		System.out.println("Date 2:" + localDate2);

		Period difference = Period.between(localDate, localDate2);
		System.out.println("Now :" + currentDate);
		System.out.println("difference :" + difference.getDays());
		System.out.println("difference M:" + difference.getMonths());
		System.out.println("difference Y:" + difference.getYears());


	}*/
	/*	private void populateParticipantData(Participant participantRequest, Participant participant) {
		participant.setPlanId(participantRequest.getPlanId());
		participant.setParticipantId(participantRequest.getParticipantId());
		participant.setDivision(participantRequest.getDivision());
		participant.setPayrollOneCd(participantRequest.getPayrollOneCd());
		participant.setPayrollTwoCd(participantRequest.getPayrollTwoCd());
		participant.setDob(participantRequest.getDob());
		participant.setDateOfHiring(participantRequest.getDateOfHiring());
		participant.setPlanEntryDate(participantRequest.getPlanEntryDate());
		participant.setBasicPay(participantRequest.getBasicPay());
		participant.setBonus(participantRequest.getBonus());
		participant.setAcctBalance(participantRequest.getAcctBalance());

		participant.setPlanPeriodStartDate(participantRequest.getPlanPeriodStartDate());
		participant.setPlanPeriodEndDate(participantRequest.getPlanPeriodEndDate());
		participant.setCycleEffectiveDate(participantRequest.getCycleEffectiveDate());
		participant.setProfitSharingFlatAmt(participantRequest.getProfitSharingFlatAmt());
		participant.setProfitSharingTotalAmtForProbation(participantRequest.getProfitSharingTotalAmtForProbation());
		participant.setBasePct(participantRequest.getBasePct());
		participant.setAgeCalculationMethod(participantRequest.getAgeCalculationMethod());
		participant.setProfitSharingMethod(participantRequest.getProfitSharingMethod());

	}*/

	private boolean isValidParticipant(Participant participant) {
		boolean isValid = true;

		if(participant.getProfitSharingMethod() ==null) {
			participant.setStatus(Constants.STATUS_FAILED);
			participant.setStatusDescription("\"Profit Sharing Method\" is mandatory input");
			isValid = false;

		} else if (participant.getProfitSharingMethod().equals(Constants.PROFIT_SHARING_METHOD_FLAT_AMT) 
				&& participant.getProfitSharingFlatAmt() == 0.0) {

			participant.setStatus(Constants.STATUS_FAILED);
			participant.setStatusDescription("\"Flat Amount\" can't be 0 as selected Profit Sharing method is \"Flat Amount\"");
			isValid = false;

		} else if (participant.getProfitSharingMethod().equals(Constants.PROFIT_SHARING_METHOD_PRORATED_BY_COMPENSATION)
				&& (participant.getProfitSharingTotalAmtForProration() == 0.0
				      || participant.getSumBasicPayAllParticipants() == 0.0)) {
			participant.setStatus(Constants.STATUS_FAILED);
			participant.setStatusDescription("\"Total Amount for Proration\" and \"Sum of compensation(BasicPay)\" is mandatory as selected Profit Sharing method is \"Prorated By Compensation\"");
			isValid = false;

		} else if (participant.getProfitSharingMethod().equals(Constants.PROFIT_SHARING_METHOD_PRORATED_BY_ACCT_BALANCE)
				&& (participant.getProfitSharingTotalAmtForProration() == 0.0 
						|| participant.getSumAccountBalanceAllParticipants() == 0.0)) {
			participant.setStatus(Constants.STATUS_FAILED);
			participant.setStatusDescription("\"Total Amount for Proration\" and \"Sum of Account Balance for all Participants\" is mandatory as selected Profit Sharing method is \"Prorated By Account Balance\"");
			isValid = false;

		} else if (participant.getProfitSharingMethod().equals(Constants.PROFIT_SHARING_METHOD_BASED_ON_PCT_COMPENSATION)
				&& participant.getBasePct() == 0.0) {
			participant.setStatus(Constants.STATUS_FAILED);
			participant.setStatusDescription("\"Percentage of Compensation\" value can't be 0 as selected Profit Sharing method is \"Percentage of Compensation\"");
			isValid = false;

		} else if (participant.getProfitSharingMethod().equals(Constants.PROFIT_SHARING_METHOD_BASED_ON_AGE_SERVICE_COMPENSATION)
				&& participant.getBasePct() == 0.0) {
			participant.setStatus(Constants.STATUS_FAILED);
			participant.setStatusDescription("\"Percentage of Compensation\" value can't be 0 as selected Profit Sharing method is \"Age and/or Service Years and/or Compensation Tiers\"");
			isValid = false;

		} else if (participant.getAgeCalculationMethod() == null) {
			participant.setStatus(Constants.STATUS_FAILED);
			participant.setStatusDescription("\"Age Calculation Method (exact age/plan period start date)\" value is mandatory.");
			isValid = false;

		} else if ((participant.getAgeCalculationMethod().equals(Constants.EXACT_AGE_INDICATOR) 
				|| participant.getAgeCalculationMethod().equals(Constants.PLAN_PERIOD_AGE_INDICATOR))

				&& participant.getDateOfBirth() == null) {
			participant.setStatus(Constants.STATUS_FAILED);
			participant.setStatusDescription("\"Date of Birth\" value is mandatory for Age Calculation.");
			isValid = false;

		} else if (participant.getAgeCalculationMethod().equals(Constants.PLAN_PERIOD_AGE_INDICATOR)
				&& participant.getPlanPeriodStartDate() == null) {
			participant.setStatus(Constants.STATUS_FAILED);
			participant.setStatusDescription("\"Plan Period Start Date\" value is mandatory for Age Calculation Method as \" Age as of Plan Period Start Date\".");
			isValid = false;

		} else if (participant.getServiceStartDateCalculationMethod() == null) {
			participant.setStatus(Constants.STATUS_FAILED);
			participant.setStatusDescription("\"Service Start Date Calculation Method (date of hire/plan entry date)\" value is mandatory.");
			isValid = false;

		} else if (participant.getServiceStartDateCalculationMethod().equals(Constants.SERVICE_START_DATE_DATE_OF_HIRE_INDICATOR)
				&& participant.getDateOfHire() == null) {
			participant.setStatus(Constants.STATUS_FAILED);
			participant.setStatusDescription("\"date of hire\" value is mandatory for Service Years Calculation method as \"Date of Hire\" ");
			isValid = false;

		} else if (participant.getServiceStartDateCalculationMethod().equals(Constants.SERVICE_START_DATE_PLAN_ENTRY_DATE_INDICATOR)
				&& participant.getDateOfHire() == null) {
			participant.setStatus(Constants.STATUS_FAILED);
			participant.setStatusDescription("\"plan entry date\" value is mandatory for Service Years Calculation method as \"Plan Entry Date\"");
			isValid = false;

		} else {
			participant.setStatus(Constants.STATUS_SUCCESS);
			participant.setStatusDescription("Processed");
			isValid = true;

		}

		return isValid;
	}



	/*	private void populateParticipantDataBasedOnFlatContriAmt(Participant participant) {
		Double flatAmtContibution=participant.getFlatContributionAmt();
		double participantContributionAmt = 0;

		if(null==flatAmtContibution) {
			double partContriPct = participant.getPartContributionPct();
			participantContributionAmt = participant.getCompensation()*(partContriPct/100);

		} else {
			double compensation=participant.getCompensation();
			participant.setPartContributionPct((flatAmtContibution/compensation)*100);
			participantContributionAmt = flatAmtContibution;
		}

		participant.setPartContributionAmt(participantContributionAmt);
	}*/ 

}
