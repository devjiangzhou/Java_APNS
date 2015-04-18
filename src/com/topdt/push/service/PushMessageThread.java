package com.topdt.push.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.topdt.push.model.PushMessage;

public class PushMessageThread extends Thread {
	static Logger log = LoggerFactory.getLogger(PushMessageThread.class);
	
	private boolean isRunning=true;
	private PushMessageService pushMessageService;

	public void run() {
		while (isRunning) {
			List<PushMessage> list = pushMessageService.getMessageByTask();
			log.info("·¢ËÍ¶ÓÁÐ"+list.size());
			if (list.size() > 0) {
				pushMessageService.push(list);
			} else {
				try {
					log.debug("no pushMsg sleep 10s");
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					log.error("", e);
				}
			}
		}
	}

	public void setPushMessageService(PushMessageService pushMessageService) {
		this.pushMessageService = pushMessageService;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
