package com.uvas.service.customer.rules;

import static com.uvas.common.date.utils.DateTimeUtil.currentLocalDateTime;

import java.time.LocalDateTime;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import com.uvas.common.exceptions.PastDateException;

@Rule(name = "InvalidMsgCreatedDate Rule", description = "If message created date is in past, return invalid message date response")
public class InvalidMsgCreatedDateRule {

	@Condition
	public boolean messageCreatedInPast(@Fact("msgDateTime") LocalDateTime msgDateTime) {
		return msgDateTime.isBefore(currentLocalDateTime());
	}

	@Action
	public void returnError(){
		throw new PastDateException("Invalid Msg Create Date in past time");
	}

}
