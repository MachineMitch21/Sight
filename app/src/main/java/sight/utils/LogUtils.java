package sight.utils;

import java.io.InputStream;
import org.apache.logging.log4j.core.config.*;


public class LogUtils {


    public static void loadLogConfigFile(InputStream streamForXMLfile) {
        try {
			ConfigurationSource source = new ConfigurationSource(streamForXMLfile);
			Configurator.initialize(null, source);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
