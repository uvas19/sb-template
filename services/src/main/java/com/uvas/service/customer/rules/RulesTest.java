package com.uvas.service.customer.rules;

import static com.uvas.common.date.utils.DateTimeUtil.currentLocalDateTime;

import java.time.LocalDateTime;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

public class RulesTest {

	public static void main(String[] args) {
		
		// define facts
        Facts dfacts = new Facts();
        LocalDateTime current = currentLocalDateTime();
        current = current.minusDays(1);
        dfacts.put("msgDateTime", current);
        
        // register rule
        Rules rules = new Rules();
        rules.register(new InvalidMsgCreatedDateRule());
        
        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, dfacts);

	}

}
