package com.topdt.push.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.topdt.push.dao.DeviceDao;
import com.topdt.frame.util.SysContext;
import com.topdt.push.model.PushDevice;

/**
 * 设备操作类
 * 
 * @author thx01
 * 
 */
@Service
public class DeviceService {
	static Logger logger = LoggerFactory.getLogger(DeviceService.class);

	private DeviceDao deviceDao;

	public PushDevice addDevice(String id, String token, String remark, String remark1) {
		PushDevice device = deviceDao.get(id);
		if (device == null) {
			device = deviceDao.getDeviceByToken(token);
		}
		if (device == null) {
			device = new PushDevice();
			device.setDeviceId(id);
			device.setToken(token);
			device.setLastRegister(new Timestamp(System.currentTimeMillis()));
			device.setIsActive(0);
			device.setRemark(remark);
			device.setRemark1(remark1);
			deviceDao.saveOrUpdate(device);
		} else {
			device.setDeviceId(id);
			device.setToken(token);
			device.setLastRegister(new Timestamp(System.currentTimeMillis()));
			device.setIsActive(0);
			device.setRemark(remark);
			device.setRemark1(remark1);
			deviceDao.saveOrUpdate(device);
		}
		return device;
	}

	/**
	 * 获取所有有效的设备
	 * 
	 * @return
	 */
	public List<PushDevice> getDevicesByActive() {
		return deviceDao.getDevicesByActive();
	}

	public PushDevice getDevice(String id) {
		return deviceDao.get(id);
	}

	public PushDevice getDeviceBytoken(String token) {
		return deviceDao.getDeviceByToken(token);
	}

	public void removeDevice(String id) {
		deviceDao.delete(id);
	}

	public void updateDeviceStatus(String id) {
		PushDevice pushDevice = deviceDao.get(id);
		pushDevice.setIsActive(1);
		deviceDao.saveOrUpdate(pushDevice);
	}

	/**
	 * 清除失效token
	 */
	public void removeNoActive() {
		try {
			Map<String, Date> map = ThxApns.queue.getInactiveDevices();
			Iterator<Entry<String, Date>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Date> entry = it.next();

				PushDevice pushDevice = deviceDao.getDeviceByToken(entry.getKey());
				pushDevice.setIsActive(1);
				deviceDao.saveOrUpdate(pushDevice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DeviceDao getDeviceDao() {
		return deviceDao;
	}

	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	public void test() {

		System.out.println("123");
	}

	public static void main(String[] args) {
		DeviceService service = (DeviceService) SysContext.getBean("deviceService");
		PushMessageService pushMessageService = (PushMessageService) SysContext.getBean("pushMessageService");
		service.test();
		pushMessageService.test();

	}

}
