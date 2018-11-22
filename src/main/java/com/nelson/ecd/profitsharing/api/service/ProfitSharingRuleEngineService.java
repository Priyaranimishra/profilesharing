package com.nelson.ecd.profitsharing.api.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelson.ecd.profitsharing.api.model.Participant;

@Service
public class ProfitSharingRuleEngineService {

	private final KieContainer kieContainer;

	@Autowired
	public ProfitSharingRuleEngineService(KieContainer kieContainer) {
		this.kieContainer = kieContainer;
	}

	public void performProfitSharingTierBasedCalculations(Participant participant) {
		
		KieSession kieSession = kieContainer.newKieSession("dtableSession");

		kieSession.insert(participant);
		
		kieSession.fireAllRules();
		kieSession.dispose();
	}


	public void filterEligibleParticipant(Participant participant) {
		KieSession kieSession = kieContainer.newKieSession("validationDtableSession");

		kieSession.insert(participant);
		kieSession.fireAllRules();
		kieSession.dispose();
	}

}
