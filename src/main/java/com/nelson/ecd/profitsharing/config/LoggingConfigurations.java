package com.nelson.ecd.profitsharing.config;


import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.spi.ContextAwareBase;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;

/**
 * 
 * @author satish-s
 * 
 *         <pre>
 * 
 *         </pre>
 */
@Configuration
public class LoggingConfigurations {

	private final Logger logger = LoggerFactory.getLogger(LoggingConfigurations.class);

	private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
	@Value("${spring.application.name}")
	private String appName;

	@Value("${server.port}")
	private String serverPort;

	// @Value("${eureka.instance.instanceId}")
	private String instanceId = "INSTANCE-";

	@Autowired
	private PinsterConfigurations configs;

	@PostConstruct
	public void intializeLogAppender() {

		if (configs.getLoggingConfig().isLogStashEnabled()) {
			logger.info(
					"Logstash is Enabled. Will start logstash appender in Asynchronous mode. (StackTrace will be limited to configured number)");
			addLogger(context);
			addContextListener(context);
		} else {
			logger.info("Logstash is Disabled. Will not start logstash appender");
		}
	}

	private void addContextListener(LoggerContext context) {
		LogbackLoggerContextListener loggerContextListener = new LogbackLoggerContextListener();
		loggerContextListener.setContext(context);
		context.addListener(loggerContextListener);
	}

	public void addLogger(LoggerContext context) {
		LogstashTcpSocketAppender logstashAppender = new LogstashTcpSocketAppender();
		logstashAppender.setName("LOGSTASH");
		logstashAppender.setContext(context);
		String optionalFields = "";
		String customFields = "{\"app_name\":\"" + appName + "\",\"app_port\":\"" + serverPort + "\"," + optionalFields
				+ "\"version\":\"" + "1" + "\"" + instanceId+configs.getLoggingConfig().getLogStashConfig().getHost() + "\" }";

		// More documentation is available at:
		// https://github.com/logstash/logstash-logback-encoder
		LogstashEncoder logstashEncoder = new LogstashEncoder();
		// Set the Logstash appender config from JHipster properties
		logstashEncoder.setCustomFields(customFields);
		// Set the Logstash appender config from JHipster properties
		logstashAppender.addDestinations(new InetSocketAddress(configs.getLoggingConfig().getLogStashConfig().getHost(), configs.getLoggingConfig().getLogStashConfig().getPort()));

		ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
		throwableConverter.setRootCauseFirst(true);
		logstashEncoder.setThrowableConverter(throwableConverter);
		logstashEncoder.setCustomFields(customFields);

		logstashAppender.setEncoder(logstashEncoder);
		logstashAppender.start();

		// Wrap the appender in an Async appender for performance
		AsyncAppender asyncLogstashAppender = new AsyncAppender();
		asyncLogstashAppender.setContext(context);
		asyncLogstashAppender.setName("ASYNC_LOGSTASH");
		asyncLogstashAppender.setQueueSize(512);
		asyncLogstashAppender.addAppender(logstashAppender);
		asyncLogstashAppender.start();

		context.getLogger("ROOT").addAppender(asyncLogstashAppender);
	}

	class LogbackLoggerContextListener extends ContextAwareBase implements LoggerContextListener {

		@Override
		public boolean isResetResistant() {
			return true;
		}

		@Override
		public void onStart(LoggerContext context) {
			addLogger(context);
		}

		@Override
		public void onReset(LoggerContext context) {
			addLogger(context);
		}

		@Override
		public void onStop(LoggerContext context) {
			// Nothing to do.
		}

		@Override
		public void onLevelChange(ch.qos.logback.classic.Logger logger, Level level) {
			// Nothing to do.
		}
	}

}
