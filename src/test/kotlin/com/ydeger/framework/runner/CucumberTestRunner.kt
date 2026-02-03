package com.ydeger.framework.runner

import io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME
import io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME
import io.cucumber.junit.platform.engine.Constants.SNIPPET_TYPE_PROPERTY_NAME
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.ydeger.framework")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, summary, html:target/reports/cucumber/report.html")
@ConfigurationParameter(key = SNIPPET_TYPE_PROPERTY_NAME, value = "camelcase")
@ConfigurationParameter(key = "cucumber.execution.parallel.enabled", value = "true")
@ConfigurationParameter(key = "cucumber.execution.parallel.config.strategy", value = "fixed")
@ConfigurationParameter(key = "cucumber.execution.parallel.config.fixed.parallelism", value = "4")
class CucumberTestRunner

