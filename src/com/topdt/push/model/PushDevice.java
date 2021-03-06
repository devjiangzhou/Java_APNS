package com.topdt.push.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "push_device")
public class PushDevice {
	@Id
	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "token")
	private String token;
	@Column(name = "lastRegister")
	private Timestamp lastRegister;
	@Column(name = "isActive")
	private int isActive;
	@Column(name = "remark")
	private String remark;
	@Column(name = "remark1")
	private String remark1;
	@Column(name="send_failedCount")
	private int sendFailedCount;
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getLastRegister() {
		return lastRegister;
	}

	public void setLastRegister(Timestamp lastRegister) {
		this.lastRegister = lastRegister;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public int getSendFailedCount() {
		return sendFailedCount;
	}

	public void setSendFailedCount(int sendFailedCount) {
		this.sendFailedCount = sendFailedCount;
	}
}
