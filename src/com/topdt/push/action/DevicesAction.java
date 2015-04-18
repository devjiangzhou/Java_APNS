package com.topdt.push.action;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.topdt.push.service.DeviceService;

/**
 * Servlet implementation class DevicesAction
 */
public class DevicesAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = LoggerFactory.getLogger(DevicesAction.class);

	@Autowired
	private DeviceService deviceService;

	/**
	 * Default constructor.
	 */
	public DevicesAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		 SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,  
	              config.getServletContext());  
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		String udid = request.getParameter("udid");
		String remark = request.getParameter("remark");

		String remark1 = request.getParameter("remark1");
		if ("opr".equals(remark)) {
			deviceService.updateDeviceStatus(udid);
		}else {
			String token = request.getParameter("token");
			token = token.replaceAll(" ", "");
			logger.info("Ê±¼ä£º udid:" + udid + " ,token£º" + token + "remark£º" + remark + "remark1£º" + remark1);
			deviceService.addDevice(udid, token, remark, remark1);
		}
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

}
