package com.jcdecaux.rest.api

import java.util.Properties

import org.bonitasoft.web.extension.ResourceProvider
/*
import com.bonitasoft.web.extension.rest.RestAPIContext
import com.sap.conn.jco.JCoDestination
import com.sap.conn.jco.JCoDestinationManager
import com.sap.conn.jco.ext.DestinationDataProvider

class SapConnection {
	public static JCoDestination getDestination(String destinationName, ResourceProvider resourceProvider) {
		Properties props = loadProperties("configuration.properties", resourceProvider)		
		Properties connectProperties = new Properties();
		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, props["JCO_ASHOST"]);
		connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  props["JCO_SYSNR"]);
		connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, props["JCO_CLIENT"]);
		connectProperties.setProperty(DestinationDataProvider.JCO_USER,   props["JCO_USER"]);
		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, props["JCO_PASSWD"]);
		connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   props["JCO_LANG"]);
		createDestinationDataFile(destinationName, connectProperties);
		JCoDestination destination = JCoDestinationManager.getDestination(destinationName);

	}
	static void createDestinationDataFile(String destinationName, Properties connectProperties)
	{
		File destCfg = new File(destinationName+".jcoDestination");
		try
		{
			FileOutputStream fos = new FileOutputStream(destCfg, false);
			connectProperties.store(fos, "for poc only !");
			fos.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException("Unable to create the destination files", e);
		}
	}
	/**
	 * Load a property file into a java.util.Properties
	 */
    /*
	static Properties loadProperties(String fileName, ResourceProvider resourceProvider) {
		Properties props = new Properties()
		resourceProvider.getResourceAsStream(fileName).withStream { InputStream s ->
			props.load s
		}
		props
	}
	*/
class SapConnection {
}
