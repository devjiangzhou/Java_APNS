package com.topdt.push.dao;

import java.util.List;

import com.topdt.frame.base.HibernateBaseDao;
import com.topdt.push.model.PushDevice;

public class DeviceDao extends HibernateBaseDao<PushDevice> {
	
	@SuppressWarnings("unchecked")
	public List<PushDevice> getDevicesByActive() {
		String hql = "from PushDevice where isActive=0 order by lastRegister desc";
		return getSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	public PushDevice getDeviceByToken(String token){
		String hql = "from PushDevice where token='"+token+"'";
		List<PushDevice> list= getSession().createQuery(hql).list();
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}
}
