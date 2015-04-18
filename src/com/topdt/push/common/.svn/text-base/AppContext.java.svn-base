package com.topdt.push.common;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.*;
import com.topdt.frame.util.Loader;

/**
 * ∂¡»°≈‰÷√Œƒº˛
 * 
 * @author thx01
 * 
 */
public class AppContext {

	private static Logger logger = Logger.getLogger(AppContext.class);

	private static String CONFIG_PROPERTIES_FILE = "app-config.properties";

	private static Properties CONFIG = null;

	private synchronized static void load() {
		if (CONFIG == null) {
			URL prop_url = Loader.getResource(CONFIG_PROPERTIES_FILE);
			InputStream pron_in = null;
			try {
				CONFIG = new Properties();
				pron_in = prop_url.openStream();
				CONFIG.load(pron_in);

			} catch (Exception e) {
				logger.error("", e);
			} finally {
				try {
					if (pron_in != null) {
						pron_in.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

	public static String getStrVal(String key) {
		if (CONFIG == null) {
			load();
		}
		return CONFIG.getProperty(key);
	}

	public static int getIntVal(String key) {
		if (CONFIG == null) {
			load();
		}
		return Integer.parseInt(CONFIG.getProperty(key));
	}

	public static boolean getBoolVal(String key) {
		if (CONFIG == null) {
			load();
		}
		String str = CONFIG.getProperty(key);
		if ("0".equals(str)) {
			return true;
		} else if ("1".equals(str)) {
			return false;
		}
		return false;
	}
}
