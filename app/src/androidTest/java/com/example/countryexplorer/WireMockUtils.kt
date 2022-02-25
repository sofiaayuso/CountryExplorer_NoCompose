package com.example.countryexplorer

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit.WireMockRule

class WireMockUtils(private val wireMockRule: WireMockRule) {

    fun loadFileFromAssets(filePath: String): String {
        val json = ApplicationProvider
            .getApplicationContext<Application>()
            .applicationContext
            .assets.open(filePath)
        return json.bufferedReader().use { it.readText() }
    }

    fun setup() {
        wireMockRule.stubFor(
            get(urlMatching("/v3.1/all")).willReturn(
                jsonResponse(loadFileFromAssets("stubs/countries/countries.json"),
                    200
                )
            )
        )
    }

}