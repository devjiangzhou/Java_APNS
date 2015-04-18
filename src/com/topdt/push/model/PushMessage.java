package com.topdt.push.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 信息表
 * 
 * @author thx01
 * 
 */
@Entity
@Table(name = "push_message")
public class PushMessage {

	public static final String NO_SEND = "0";
	public static final String SEND_ING = "1";
	public static final String SENDED = "2";
	public static final String SEND_FAILED = "9";
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")     
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")   
	@Column(name = "message_id")
	private int messageId;
	@Column(name = "device_Id")
	private String deviceId;
	@Column(name = "device_token")
	private String deviceToken;
	@Column(name = "message_json")
	private String messageJson;
	@Column(name = "add_time")
	private Date addTime;
	@Column(name = "send_time")
	private Date sendTime;
	@Column(name = "send_status")
	private String sendStatus;

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getMessageJson() {
		return messageJson;
	}

	public void setMessageJson(String messageJson) {
//		// 处理长字符串
//		if (messageJson.length() > Utilities.MAX_PAYLOAD_LENGTH) {
//			messageJson = messageJson.substring(0, Utilities.MAX_PAYLOAD_LENGTH - 1);
//		}
		this.messageJson = messageJson;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
}
