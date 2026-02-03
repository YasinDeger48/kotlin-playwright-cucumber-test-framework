package com.ydeger.framework.steps

import com.ydeger.framework.config.ConfigManager
import com.ydeger.framework.core.ui.PlaywrightManager
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.slf4j.LoggerFactory
import kotlin.test.assertTrue

class UiSteps {
    private val log = LoggerFactory.getLogger(UiSteps::class.java)

    @Given("user navigates to the home page")
    fun openHomePage() {
        val baseUrl = ConfigManager.get("ui.baseUrl")
        log.info("Navigating to home page: {}", baseUrl)
        PlaywrightManager.page?.navigate(baseUrl)
    }

    @When("user navigates to {string} page")
    fun openPage(path: String) {
        val targetUrl = if (path.startsWith("http://") || path.startsWith("https://")) {
            path
        } else {
            val baseUrl = ConfigManager.get("ui.baseUrl").trimEnd('/')
            val normalizedPath = path.trimStart('/')
            "$baseUrl/$normalizedPath"
        }
        log.info("Navigating to page: {}", targetUrl)
        PlaywrightManager.page?.navigate(targetUrl)
    }

    @Then("page title should contain {string}")
    fun assertTitleContains(text: String) {
        val title = PlaywrightManager.page?.title() ?: ""
        log.info("Asserting page title contains: '{}' | actual='{}'", text, title)
        assertTrue(title.contains(text, ignoreCase = true), "Actual title: $title")
    }

    @Then("current URL should contain {string}")
    fun assertUrlContains(text: String) {
        val url = PlaywrightManager.page?.url() ?: ""
        log.info("Asserting current URL contains: '{}' | actual='{}'", text, url)
        assertTrue(url.contains(text, ignoreCase = true), "Actual URL: $url")
    }

    @Then("page should contain text {string}")
    fun assertPageContainsText(text: String) {
        val content = PlaywrightManager.page?.content() ?: ""
        log.info("Asserting page content contains text: '{}'", text)
        assertTrue(content.contains(text, ignoreCase = true), "Text not found in page content: $text")
    }
}