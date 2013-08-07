/* Copyright (c) 2011 GeoSolutions - http://www.geo-solutions.it/.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package it.geosolutions.geoserver.jms.client;

import it.geosolutions.geoserver.jms.JMSFactory;
import it.geosolutions.geoserver.jms.configuration.JMSConfiguration;
import it.geosolutions.geoserver.jms.configuration.ToggleConfiguration;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

final public class JMSContainer extends DefaultMessageListenerContainer {

	@Autowired
	public JMSFactory jmsFactory;

	private JMSConfiguration config;
	//
	// private JMSQueueListener listener;

	private boolean verified = false;

	public JMSContainer(JMSConfiguration config, JMSQueueListener listener) {
		super();
		
		// the listener used to handle incoming events
		setMessageListener(listener);
		// configuration
		this.config = config;
		
		// TODO ad-hoc config
		final String startString = config
				.getConfiguration(ToggleConfiguration.TOGGLE_CONSUMER_KEY);
		if (startString != null) {
			setAutoStartup(Boolean.parseBoolean(startString));
		}
	}

	@PostConstruct
	private void init() {
		final Properties conf = config.getConfigurations();
		setDestination(jmsFactory.getClientDestination(conf));
		setConnectionFactory(jmsFactory.getConnectionFactory(conf));
	}

	private static void verify(final Object type, final String message) {
		if (type == null)
			throw new IllegalArgumentException(message != null ? message
					: "Verify fails the argument check");
	}

	@Override
	public void start() throws JmsException {
		if (!verified) {
			verify(jmsFactory, "failed to get a JMSFactory");
			verify(config, "configuration is null");
			verified = true;
		}
//		if (!isActive()) {
			init();
			super.start();
//		}
	}

}
