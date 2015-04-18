package com.topdt.push.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsDelegate;
import com.notnoop.apns.ApnsNotification;
import com.notnoop.apns.DeliveryError;
import com.notnoop.apns.internal.Utilities;
import com.topdt.push.model.PushDevice;
import com.topdt.push.model.PushMessage;

public class ThxApnsDelegate implements ApnsDelegate{
	static Logger logger=LoggerFactory.getLogger(ThxApnsDelegate.class);
	public PushMessageService pushMessageService;
	public DeviceService deviceService;
	@Override
	public void messageSent(ApnsNotification message, boolean resent) {
		// TODO Auto-generated method stub
		//���ɹ� �����豸���ע��ʱ�� �Լ� ��Ϣ��״̬
		if (resent) {
			pushMessageService.updateMessageSended(message.getIdentifier(),PushMessage.NO_SEND);
			logger.info("����ʧ�ܺ����״̬");
		}else {
			pushMessageService.updateMessageSended(message.getIdentifier(),PushMessage.SENDED);
			logger.info("���ͳɹ������״̬");
		}
	}

	@Override
	public void messageSendFailed(ApnsNotification message, Throwable e) {
		// TODO Auto-generated method stub
		PushMessage pushMessage=pushMessageService.getMessageById(message.getIdentifier());
		PushDevice pushDevice=deviceService.getDeviceBytoken(pushMessage.getDeviceToken());
		pushDevice.setSendFailedCount(pushDevice.getSendFailedCount()+1);
		if (pushDevice.getSendFailedCount()>=10) {
			pushDevice.setIsActive(1);
		}
		deviceService.getDeviceDao().saveOrUpdate(pushDevice);
		//����ʧ��
		pushMessageService.updateMessageSended(message.getIdentifier(),PushMessage.SEND_FAILED);
		Utilities.encodeHex(message.getDeviceToken());
		Utilities.encodeHex(message.getPayload());
		e.printStackTrace();
		logger.info("����ʧ��");
	}

D

	@Override
	public void cacheLengthExceeded(int newCacheLength) {
		//ThxApns.queue.
	}

	@Override
	public void notificationsResent(int resendCount) {
		// TODO Auto-generated method stub
		
	}

	public PushMessageService getPushMessageService() {
		return pushMessageService;
	}

	public void setPushMessageService(PushMessageService pushMessageService) {
		this.pushMessageService = pushMessageService;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	
}
