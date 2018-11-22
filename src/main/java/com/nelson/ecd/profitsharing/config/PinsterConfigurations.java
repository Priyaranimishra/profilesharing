package com.nelson.ecd.profitsharing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author satish-s
 *
 *
 *         Properties specific to PInster.
 *
 *         <p>
 *         Properties are configured in the application.yml file.
 *         </p>
 *
 */
@Data
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "pinster", ignoreUnknownFields = false)
public class PinsterConfigurations {
	private final Metrics metrics = new Metrics();
	private final LoggingConfigurations loggingConfig = new LoggingConfigurations();

	public Metrics getMetrics() {
		return metrics;
	}

	public LoggingConfigurations getLoggingConfig() {
		return loggingConfig;
	}

	@Data
	@NoArgsConstructor
	public static class Metrics {

		private final Jmx jmx = new Jmx();
		private boolean loggingEnabled = false;
		private int reportingFrequency = 60;
		
		
		public boolean isLoggingEnabled() {
			return loggingEnabled;
		}


		public void setLoggingEnabled(boolean loggingEnabled) {
			this.loggingEnabled = loggingEnabled;
		}


		public int getReportingFrequency() {
			return reportingFrequency;
		}


		public void setReportingFrequency(int reportingFrequency) {
			this.reportingFrequency = reportingFrequency;
		}


		public Jmx getJmx() {
			return jmx;
		}


		@Data
		@NoArgsConstructor
		public static class Jmx {
			private boolean enabled = false;

			public boolean isEnabled() {
				return enabled;
			}

			public void setEnabled(boolean enabled) {
				this.enabled = enabled;
			}
			
		}

	}
	@Data
	@NoArgsConstructor
	public static class LoggingConfigurations {
		private boolean logStashEnabled = false;
		private LogStash logStashConfig;
		public boolean isLogStashEnabled() {
			return logStashEnabled;
		}
		public void setLogStashEnabled(boolean logStashEnabled) {
			this.logStashEnabled = logStashEnabled;
		}
		public LogStash getLogStashConfig() {
			return logStashConfig;
		}
		public void setLogStashConfig(LogStash logStashConfig) {
			this.logStashConfig = logStashConfig;
		}
		
	}

	@Data
	@NoArgsConstructor
	public static class LogStash {

		private String host;
		private int port;
		private int queueSize;
		private int stackTranceLength = 7500;
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public int getQueueSize() {
			return queueSize;
		}
		public void setQueueSize(int queueSize) {
			this.queueSize = queueSize;
		}
		public int getStackTranceLength() {
			return stackTranceLength;
		}
		public void setStackTranceLength(int stackTranceLength) {
			this.stackTranceLength = stackTranceLength;
		}
		
	}
}
