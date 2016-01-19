package com.bfkelsey.api

import com.bfkelsey.api.core.Provider
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.equalTo
import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.testing.junit.DropwizardAppRule
import org.junit.After
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType


public class ProviderResourceTest {
    lateinit var client: Client


    companion object {
        val CONFIG_PATH = ResourceHelpers.resourceFilePath("config-test.yaml")
        @ClassRule
        @JvmStatic
        public val RULE = DropwizardAppRule<ApiConfiguration>(ApiApplication::class.java, CONFIG_PATH)
    }
    @Before
    public fun setUp() {
        client = ClientBuilder.newClient()
    }

    @After
    public fun tearDown() {
        client.close()
    }

    @Test
    public fun testShouldCreateAProvider() {
        val provider = Provider(id = 0, name = "John's Lawn")
        val response = client.target("http://localhost:${RULE.localPort}/api/providers")
                .request()
                .post(Entity.entity(provider, MediaType.APPLICATION_JSON_TYPE))
        val providerResponse = response.readEntity(String::class.java)

        assertThat(response.status, equalTo(200))
        assertThat(providerResponse, containsSubstring(provider.name))
    }
}