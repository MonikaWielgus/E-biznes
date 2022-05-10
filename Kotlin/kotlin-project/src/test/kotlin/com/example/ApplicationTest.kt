package com.example

import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ReaderRouteTests {
    @Test
    fun testGetReader() = testApplication {

        application {
            configureRouting()
            configureSerialization()
        }

        val response = client.get("/reader")

        val expectedResponse = "[{\"nick\":\"jan_nowak\",\"firstName\":\"jan\",\"lastName\":\"nowak\",\"email\":\"jannowak@gmail.com\"},{\"nick\":\"olaola\",\"firstName\":\"aleksandra\",\"lastName\":\"mazur\",\"email\":\"olaola@gmail.com\"}]"
        assertEquals(
            expectedResponse,
            response.body()
        )

        assertEquals(HttpStatusCode.OK, response.status)
    }
}