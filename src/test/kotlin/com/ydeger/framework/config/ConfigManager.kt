package com.ydeger.framework.config

import java.io.InputStream
import java.util.Properties

object ConfigManager {
    private val properties: Properties = Properties()

    init {
        val env = System.getProperty("env", "default")
        load("config/default.properties")
        if (env != "default") {
            load("config/$env.properties")
        }
    }

    private fun load(resourcePath: String) {
        val stream: InputStream = ConfigManager::class.java.classLoader.getResourceAsStream(resourcePath)
            ?: return
        stream.use {
            val content = it.bufferedReader(Charsets.UTF_8).readText().removePrefix("\uFEFF")
            properties.load(content.reader())
        }
    }

    fun get(key: String): String {
        return System.getProperty(key)
            ?: System.getenv(key.uppercase().replace('.', '_'))
            ?: properties.getProperty(key)
            ?: error("Config key not found: $key")
    }

    fun getOrDefault(key: String, defaultValue: String): String {
        return System.getProperty(key)
            ?: System.getenv(key.uppercase().replace('.', '_'))
            ?: properties.getProperty(key)
            ?: defaultValue
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return getOrDefault(key, defaultValue.toString()).toBoolean()
    }
}
