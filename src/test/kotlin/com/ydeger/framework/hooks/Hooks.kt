package com.ydeger.framework.hooks

import com.ydeger.framework.core.ui.PlaywrightManager
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import org.slf4j.LoggerFactory

class Hooks {
    private val log = LoggerFactory.getLogger(Hooks::class.java)
    private val startedAt = ThreadLocal<Long>()

    @Before
    fun beforeScenario(scenario: Scenario) {
        startedAt.set(System.currentTimeMillis())
        log.info("START | {} | tags={}", scenario.name, scenario.sourceTagNames)
    }

    @Before("@ui")
    fun beforeUiScenario() {
        log.info("UI setup started")
        PlaywrightManager.start()
        log.info("UI setup completed")
    }

    @After("@ui")
    fun afterUiScenario() {
        log.info("UI teardown started")
        PlaywrightManager.stop()
        log.info("UI teardown completed")
    }

    @After
    fun afterScenario(scenario: Scenario) {
        val durationMs = System.currentTimeMillis() - (startedAt.get() ?: System.currentTimeMillis())
        log.info("END   | {} | status={} | duration={} ms", scenario.name, scenario.status, durationMs)
        startedAt.remove()
    }
}