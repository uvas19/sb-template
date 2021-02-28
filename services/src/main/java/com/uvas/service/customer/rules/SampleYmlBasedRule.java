package com.uvas.service.customer.rules;

import java.io.FileReader;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.spel.SpELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class SampleYmlBasedRule {

	private static final String RULE_FILE = "/Users/uvashish/repo/github/sb-template/services/src/main/resources/weather-rule.yml";
	
	private static final String FACT_KEY = "RAIN";
	private static final boolean FACT_VALUE = true;
	
	public static void main(String[] args) {
		SpELRuleFactory ruleFactory = new SpELRuleFactory(new YamlRuleDefinitionReader());
		try {
			// define facts
	        Facts dfacts = new Facts();
	        dfacts.put(FACT_KEY, FACT_VALUE);
	        
	        log.info(dfacts.getFact(FACT_KEY).getName());
	        log.info(dfacts.getFact(FACT_KEY).getValue().toString());
	        
	        // define rules
			Rules weatherRules = ruleFactory.createRules(new FileReader(RULE_FILE));
	        Rules rules = new Rules();
	        rules.register(weatherRules);
	        
	        // fire rules on known facts
	        RulesEngine rulesEngine = new DefaultRulesEngine();
	        rulesEngine.fire(rules, dfacts);
	        
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}

}
