package com.bfkelsey.api

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory


class ApiConfiguration(@JsonProperty("database") val dataSourceFactory: DataSourceFactory) : Configuration()
