package com.topdt.push.dao;

import com.topdt.frame.base.HibernateBaseDao;
import com.topdt.push.model.PushMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushMessageDao extends HibernateBaseDao<PushMessage> {

	static Logger logger = LoggerFactory.getLogger(PushMessageDao.class);

	/**
	 * 保存到队列
	 * 
	 * @param list
	 */
	public void addbatch(final List<PushMessage> list) {
		getSession().doWork(new Work() {
			String sql = "INSERT INTO push_message (push_message.device_id," + "	push_message.device_token," + "	push_message.message_json," + "	push_message.add_time,"
					+ "	push_message.send_time," + "	push_message.send_status) VALUES (?, ?, ?, ?, ?, ?)";

			@Override
			public void execute(Connection con) throws SQLException {
				try {
					con.setAutoCommit(false);
					PreparedStatement ps = con.prepareStatement(sql);
					int count = 0;
					for (int i = 0; i < list.size(); i++) {
						count++;
						PushMessage pushMessage = list.get(i);
						ps.setString(1, pushMessage.getDeviceId());
						ps.setString(2, pushMessage.getDeviceToken());
						ps.setString(3, pushMessage.getMessageJson());
						ps.setTimestamp(4, new Timestamp(pushMessage.getAddTime().getTime()));
						ps.setTimestamp(5,	new Timestamp(pushMessage.getAddTime().getTime()));
						ps.setString(6, pushMessage.getSendStatus());
						ps.addBatch();
						if (count == 500) {
							ps.executeBatch();
							count = 0;
						} else if (i == list.size() - 1) {
							ps.executeBatch();
						}
					}
					con.commit();
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("", e);
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<PushMessage> getMessageByTask() {
		String hql = "from PushMessage where sendStatus=0 and sendTime<?";
		List<PushMessage> l = createQuery(hql, new Object[] { new Date() }).list();
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public PushMessage getMessageById(int id){
		String hql="from PushMessage where messageId=?";
		List<PushMessage> l=createQuery(hql, new Object[]{id}).list();
		if (l.size()>0) {
			return l.get(0);
		}
		return null;
	}
	
	public void updateMessageSended(int id,String status){
		String hql="update PushMessage set send_status=? where messageId=?";
		createQuery(hql, new Object[]{PushMessage.SENDED,id}).executeUpdate();
	}
}
