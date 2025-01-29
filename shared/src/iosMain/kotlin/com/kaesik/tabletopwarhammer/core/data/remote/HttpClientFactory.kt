package com.kaesik.tabletopwarhammer.core.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class HttpClientFactory {
     actual fun create(): HttpClient {
            return HttpClient(Darwin) {
                 install(ContentNegotiation) {
                    json()
                 }
            }
     }
}
