package com.ydeger.framework.core.ui

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserContext
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import com.ydeger.framework.config.ConfigManager

object PlaywrightManager {
    private val playwrightHolder = ThreadLocal<Playwright?>()
    private val browserHolder = ThreadLocal<Browser?>()
    private val contextHolder = ThreadLocal<BrowserContext?>()
    private val pageHolder = ThreadLocal<Page?>()

    val page: Page?
        get() = pageHolder.get()

    fun start() {
        val browserName = ConfigManager.getOrDefault("ui.browser", "chromium")
        val headless = ConfigManager.getBoolean("ui.headless", true)

        val playwright = Playwright.create()
        val launchOptions = BrowserType.LaunchOptions().setHeadless(headless)

        val browser = when (browserName.lowercase()) {
            "firefox" -> playwright.firefox().launch(launchOptions)
            "webkit" -> playwright.webkit().launch(launchOptions)
            else -> playwright.chromium().launch(launchOptions)
        }

        val context = browser.newContext()
        val page = context.newPage()

        playwrightHolder.set(playwright)
        browserHolder.set(browser)
        contextHolder.set(context)
        pageHolder.set(page)
    }

    fun stop() {
        pageHolder.get()?.close()
        contextHolder.get()?.close()
        browserHolder.get()?.close()
        playwrightHolder.get()?.close()

        pageHolder.remove()
        contextHolder.remove()
        browserHolder.remove()
        playwrightHolder.remove()
    }
}
