package com.topdt.push.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.notnoop.apns.EnhancedApnsNotification;
import com.topdt.push.dao.PushMessageDao;
import com.topdt.push.model.Message;
import com.topdt.push.model.PushDevice;
import com.topdt.push.model.PushMessage;

/**
 * 保存发送信息
 * 
 * @author thx01
 * 
 */
public class PushMessageService {
	static Logger logger = LoggerFactory.getLogger(PushMessageService.class);


	@Autowired
	private PushMessageDao pushMessageDao;

	public void addBatchMessage(List<PushDevice> list, Message message) {
		for (PushDevice pushDevice : list) {
			this.addBatchMessage(pushDevice, message);
		}
	}

	
	public void addBatchMessage(PushDevice pushDevice, Message message) {
			PushMessage pushMessage = new PushMessage();
			pushMessage.setDeviceId(pushDevice.getDeviceId());
			pushMessage.setDeviceToken(pushDevice.getToken());
			pushMessage.setAddTime(new java.util.Date());
			pushMessage.setSendTime(new java.util.Date());
			pushMessage.setSendStatus(PushMessage.NO_SEND);
			pushMessageDao.saveOrUpdate(pushMessage);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("msgId", pushMessage.getMessageId());
			String messageJson = PayloadUtil.payloadByMessage(message,map).toString();
			pushMessage.setMessageJson(messageJson);
			pushMessageDao.saveOrUpdate(pushMessage);
	}
	/**
	 * 得到未发送的信息
	 * 
	 * @return
	 */
	public List<PushMessage> getMessageByTask() {
		return pushMessageDao.getMessageByTask();
	}

	/**
	 * 发送信息，并更新信息状态为正在发送
	 * 
	 * @param list
	 */
	public void push(List<PushMessage> list) {
		for (PushMessage pushMessage : list) {
			EnhancedApnsNotification notification = new EnhancedApnsNotification(pushMessage.getMessageId(), EnhancedApnsNotification.MAXIMUM_EXPIRY, pushMessage.getDeviceToken(),
					pushMessage.getMessageJson());
			pushMessage.setSendStatus(PushMessage.SEND_ING);
			pushMessageDao.saveOrUpdate(pushMessage);
			ThxApns.queue.push(notification);
		}
	}
	
	public void updateMessageSended(int id,String status){
		pushMessageDao.updateMessageSended(id, status);
	}
	public PushMessage getMessageById(int id){
		return pushMessageDao.getMessageById(id);
	}
	public PushMessageDao getPushMessageDao() {
		return pushMessageDao;
	}

	public void setPushMessageDao(PushMessageDao pushMessageDao) {
		this.pushMessageDao = pushMessageDao;
	}

	public void test() {
		System.out.println("123");
	}
}
