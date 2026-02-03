package com.ydeger.framework.steps

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.ydeger.framework.core.api.ApiClient
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.restassured.response.Response
import org.slf4j.LoggerFactory
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ApiSteps {
    private val log = LoggerFactory.getLogger(ApiSteps::class.java)
    private lateinit var endpoint: String
    private lateinit var response: Response
    private lateinit var body: JsonElement

    @Given("API endpoint is {string}")
    fun setApiEndpoint(path: String) {
        endpoint = path
        log.info("API endpoint set: {}", endpoint)
    }

    @When("I send a GET request")
    fun sendGet() {
        response = ApiClient.get(endpoint)
        body = JsonParser.parseString(response.asString())
        val payload = response.asString().replace("\n", "").replace("\r", "")
        val preview = if (payload.length > 180) payload.substring(0, 180) + "..." else payload
        log.info(
            "GET {} -> status={} time={} ms body={} chars",
            endpoint,
            response.statusCode,
            response.time,
            payload.length
        )
        log.debug("GET {} response preview: {}", endpoint, preview)
    }

    @Then("status code should be {int}")
    fun assertStatusCode(expectedStatusCode: Int) {
        log.info("Asserting status code: expected={} actual={}", expectedStatusCode, response.statusCode)
        assertEquals(expectedStatusCode, response.statusCode)
    }

    @Then("response time should be under {int} ms")
    fun assertResponseTimeUnder(maxMs: Int) {
        log.info("Asserting response time: max={} ms actual={} ms", maxMs, response.time)
        assertTrue(response.time < maxMs, "Actual response time: ${response.time} ms")
    }

    @Then("response body field {string} should be {string}")
    fun assertFieldEquals(path: String, expectedValue: String) {
        val actual = resolveField(path)
        assertNotNull(actual, "Field not found at path: $path")
        val actualText = toComparableText(actual)
        log.info("Asserting field equality: path={} expected={} actual={}", path, expectedValue, actualText)
        assertEquals(expectedValue, actualText)
    }

    @Then("response body field {string} should not be blank")
    fun assertFieldNotBlank(path: String) {
        val actual = resolveField(path)
        assertNotNull(actual, "Field not found at path: $path")
        val actualText = toComparableText(actual)
        log.info("Asserting field not blank: path={} value={}", path, actualText)
        assertTrue(actualText.isNotBlank(), "Field is blank at path: $path")
    }

    @Then("response body should be an array")
    fun assertBodyIsArray() {
        log.info("Asserting response body is array")
        assertTrue(body.isJsonArray, "Response body is not an array")
    }

    @Then("response body array size should be {int}")
    fun assertArraySize(expectedSize: Int) {
        assertTrue(body.isJsonArray, "Response body is not an array")
        val actualSize = body.asJsonArray.size()
        log.info("Asserting array size: expected={} actual={}", expectedSize, actualSize)
        assertEquals(expectedSize, actualSize)
    }

    private fun resolveField(path: String): JsonElement? {
        val tokens = tokenizePath(path)
        var current: JsonElement = body
        for (token in tokens) {
            if (token.all { it.isDigit() }) {
                if (!current.isJsonArray) return null
                val index = token.toInt()
                val array = current.asJsonArray
                if (index < 0 || index >= array.size()) return null
                current = array[index]
            } else {
                if (!current.isJsonObject) return null
                val obj = current.asJsonObject
                if (!obj.has(token)) return null
                current = obj.get(token)
            }
        }
        return current
    }

    private fun tokenizePath(path: String): List<String> {
        val normalized = path.replace(Regex("\\[(\\d+)]"), ".$1")
        return normalized.split(".").filter { it.isNotBlank() }
    }

    private fun toComparableText(element: JsonElement): String {
        if (element.isJsonPrimitive) {
            val primitive = element.asJsonPrimitive
            return when {
                primitive.isString -> primitive.asString
                primitive.isNumber -> primitive.asNumber.toString()
                primitive.isBoolean -> primitive.asBoolean.toString()
                else -> primitive.toString()
            }
        }
        return element.toString()
    }
}