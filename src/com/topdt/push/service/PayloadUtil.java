package com.topdt.push.service;

import java.util.Map;

import com.notnoop.apns.PayloadBuilder;
import com.notnoop.apns.internal.Utilities;
import com.topdt.push.model.Message;

public class PayloadUtil {

	public static String payloadByMessage(Message message,Map<String, Object> map) {
		PayloadBuilder payloadBuilder = PayloadBuilder.newPayload();
		if (message.getContent() != null && message.getContent().length() > 0)
			payloadBuilder.alertBody(message.getContent());
		if (message.getBadge() > 0)
			payloadBuilder.badge(message.getBadge());
		if (message.getSound() != null)
			payloadBuilder.sound(message.getSound());
		if (map!=null) {
			payloadBuilder.customFields(map);
		}
		// ´¦Àí³¤×Ö·û´®
		if (payloadBuilder.build().length()>Utilities.MAX_PAYLOAD_LENGTH) {
			int overLength=payloadBuilder.build().length()-Utilities.MAX_PAYLOAD_LENGTH+3;
			message.setContent(message.getContent().substring(0, overLength-1)+"...");
		}
		return payloadBuilder.build();
	}
}
