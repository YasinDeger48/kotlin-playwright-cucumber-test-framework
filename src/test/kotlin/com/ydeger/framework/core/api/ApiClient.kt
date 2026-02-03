package com.ydeger.framework.core.api

import com.ydeger.framework.config.ConfigManager
import io.restassured.RestAssured
import io.restassured.response.Response

object ApiClient {
    private val baseUrl = ConfigManager.get("api.baseUrl")

    init {
        RestAssured.baseURI = baseUrl
    }

    fun get(path: String): Response {
        return RestAssured
            .given()
            .header("Accept", "application/json")
            .`when`()
            .get(path)
            .then()
            .extract()
            .response()
    }
}
