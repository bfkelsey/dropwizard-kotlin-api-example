package com.bfkelsey.api

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.bfkelsey.api.jdbi.ProviderDAO
import com.bfkelsey.api.resources.ProviderResource
import io.dropwizard.Application
import io.dropwizard.db.DataSourceFactory
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.migrations.MigrationsBundle
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import io.dropwizard.assets.AssetsBundle

class ApiApplication : Application<ApiConfiguration>() {

    companion object {
        @JvmStatic public fun main(args: Array<String>) {
            ApiApplication().run(*args)
        }
    }

    override fun run(configuration: ApiConfiguration, environment: Environment) {
        val factory = DBIFactory()
        val jdbi = factory.build(environment, configuration.dataSourceFactory, "postgresql")
        val providerDAO = jdbi.onDemand(ProviderDAO::class.java)
        environment.jersey().register(ProviderResource(providerDAO))
    }

    override fun initialize(bootstrap: Bootstrap<ApiConfiguration>) {
        bootstrap.objectMapper.registerModule(KotlinModule())
        bootstrap.addBundle(object: MigrationsBundle<ApiConfiguration>() {
            override fun getDataSourceFactory(configuration: ApiConfiguration): DataSourceFactory {
                return configuration.dataSourceFactory
            }
        })
        bootstrap.addBundle(AssetsBundle("/assets/", "/"))
    }

    override fun getName(): String {
        return "api"
    }

}

