package com.topdt.push.service;

import java.io.InputStream;

import javax.net.ssl.SSLContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notnoop.apns.ApnsServiceBuilder;
import com.notnoop.apns.ReconnectPolicy.Provided;
import com.notnoop.apns.internal.QueuedApnsService;
import com.notnoop.apns.internal.Utilities;
import com.notnoop.exceptions.InvalidSSLConfig;
import com.topdt.frame.util.Loader;
import com.topdt.push.common.AppContext;

/**
 * spring
 * 
 * @author thx01
 * 
 */
public class ThxApns {

	static Logger logger = LoggerFactory.getLogger(ThxApns.class);
	
	
	private static String keyStoreName = AppContext.getStrVal("CER_NAME");
	private static String keyStorePwd = AppContext.getStrVal("CAR_PASSWORD");
	//private static int threadCount = AppContext.getIntVal("THREAD_COUNT");
	private static boolean isProduction = AppContext.getBoolVal("IS_PRODUCTION");
	private static String keyStoreType = AppContext.getStrVal("CAR_TYPE");
	private static String keyStoreAlgorithm = AppContext.getStrVal("CAR_Algorithm");

	static QueuedApnsService queue;
	
	private ThxApnsDelegate thxApnsDelegate;
	
	public void init() {
		try {
			InputStream stream = Loader.getResource(keyStoreName).openStream();
			SSLContext sslContext = Utilities.newSSLContext(stream, keyStorePwd, keyStoreType, keyStoreAlgorithm);
			queue = (QueuedApnsService) new ApnsServiceBuilder().withSSLContext(sslContext).withAppleDestination(isProduction).withDelegate(thxApnsDelegate)
			 .withNoErrorDetection()
					.withAutoAdjustCacheLength(true).withReconnectPolicy(Provided.EVERY_HALF_HOUR).asQueued().build();
			queue.start();
		} catch (InvalidSSLConfig e) {
			logger.error("",e);
		}catch (Exception e) {
			logger.error("",e);
		}
	}

	public ThxApnsDelegate getThxApnsDelegate() {
		return thxApnsDelegate;
	}

	public void setThxApnsDelegate(ThxApnsDelegate thxApnsDelegate) {
		this.thxApnsDelegate = thxApnsDelegate;
	}
	
	
}
