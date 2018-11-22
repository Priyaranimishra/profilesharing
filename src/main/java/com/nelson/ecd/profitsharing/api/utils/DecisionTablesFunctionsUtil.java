 package com.nelson.ecd.profitsharing.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nelson.ecd.profitsharing.api.model.Participant;

public class DecisionTablesFunctionsUtil {
	private final static Logger logger = LoggerFactory.getLogger(DecisionTablesFunctionsUtil.class);

	public static void performSummation(Participant participant) {
		
		if (participant.getParticipantCalculatedServiceYrs() >= participant.getParticipantEligibleServiceYrsDT()
				&& participant.getParticipantCalculatedAge() >= participant.getParticipantEligibleAgeDT()) {

			participant.setEligible(true);
			logger.debug("Eligible Participant details - planId :{}, participantId :{}, Age :{}, Service Yrs :{}",
					participant.getPlan(),
					participant.getParticipantId(),
					participant.getParticipantCalculatedAge(), 
					participant.getParticipantCalculatedServiceYrs());

		} else {
			participant.setEligible(false);
			logger.debug("Ineligible Participant details - planId :{}, participantId :{}, Age :{}, Service Yrs :{}",
					participant.getPlan(),
					participant.getParticipantId(),
					participant.getParticipantCalculatedAge(), 
					participant.getParticipantCalculatedServiceYrs());

		}
	}
	
	public static double getProfitSharingAmt(Participant participant, int tierAdditionalPct) {
		
		logger.debug("planId :{}, participantId :{}, Input Profit Sharing Amount :{}, CompensationTier Additional Pct :{}, "
				+ "Age Additional Pct :{}, Service Yrs Additional Pct :{}, Pct to add into Input Profit :{}",
				
				participant.getPlan(),
				participant.getParticipantId(),
				participant.getProfitSharingAmt(), 
				participant.getCompensationTierPct(), participant.getAgeTierPct(), participant.getServiceYrsTierPct(),
				tierAdditionalPct);

		double tierBasedProfitSharingAmt = participant.getBasePctAmt()*tierAdditionalPct/100;
		double updatedProfitSharingAmt = tierBasedProfitSharingAmt + participant.getProfitSharingAmt();

		logger.debug("planId :{}, participantId :{}, Updated Profit Sharing Amount :{}",
				participant.getPlan(),
				participant.getParticipantId(),
				updatedProfitSharingAmt);

		return updatedProfitSharingAmt;
	}
	
}
