package com.nelson.ecd.profitsharing.config;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.jvm.BufferPoolMetricSet;
import com.codahale.metrics.jvm.FileDescriptorRatioGauge;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;

/**
 *
 * @author satish-s
 *
 *         <p>
 *         This class controls the metrics configuration
 *         </p>
 */
@Configuration
@EnableMetrics(proxyTargetClass = true)
public class MetricsConfiguration {

	private static final String PROP_METRIC_REG_JVM_MEMORY = "jvm.memory";
	private static final String PROP_METRIC_REG_JVM_GARBAGE = "jvm.garbage";
	private static final String PROP_METRIC_REG_JVM_THREADS = "jvm.threads";
	private static final String PROP_METRIC_REG_JVM_FILES = "jvm.files";
	private static final String PROP_METRIC_REG_JVM_BUFFERS = "jvm.buffers";

	@Autowired
	private MetricRegistry metricRegistry;

	@Autowired
	private PinsterConfigurations pinsterConfigs;
	private final Logger log = LoggerFactory.getLogger(MetricsConfiguration.class);

	@PostConstruct
	public void initializeMetricsConiguration() {
		log.debug("Registering JVM gauges");
		metricRegistry.register(PROP_METRIC_REG_JVM_MEMORY, new MemoryUsageGaugeSet());
		metricRegistry.register(PROP_METRIC_REG_JVM_GARBAGE, new GarbageCollectorMetricSet());
		metricRegistry.register(PROP_METRIC_REG_JVM_THREADS, new ThreadStatesGaugeSet());
		metricRegistry.register(PROP_METRIC_REG_JVM_FILES, new FileDescriptorRatioGauge());
		metricRegistry.register(PROP_METRIC_REG_JVM_BUFFERS,
				new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));

		if (pinsterConfigs.getMetrics().getJmx().isEnabled()) {
			log.debug("Initializing Metrics JMX reporting");
			JmxReporter jmxReporter = JmxReporter.forRegistry(metricRegistry).build();
			jmxReporter.start();

		}

		if (pinsterConfigs.getMetrics().isLoggingEnabled()) {
			log.debug("Initializing Metrics Console reporting");
			/*
			 * ConsoleReporter consoleReporter =
			 * ConsoleReporter.forRegistry(metricRegistry)
			 * .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.
			 * MILLISECONDS).build();
			 * consoleReporter.start(pinsterConfigs.getMetrics().getLogs().
			 * getReportFrequency(), TimeUnit.SECONDS);
			 */

			final Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
					.outputTo(LoggerFactory.getLogger("metrics")).convertRatesTo(TimeUnit.SECONDS)
					.convertDurationsTo(TimeUnit.MILLISECONDS).build();
			reporter.start(pinsterConfigs.getMetrics().getReportingFrequency(), TimeUnit.SECONDS);

		}
	}

}
