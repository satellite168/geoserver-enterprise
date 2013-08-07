/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package it.geosolutions.geoserver.jms.configuration;

import java.io.IOException;

public interface JMSConfigurationExt {

	public void initDefaults(JMSConfiguration config) throws IOException;

	public boolean checkForOverride(JMSConfiguration config) throws IOException;

}
