package com.bfkelsey.api.resources

import com.bfkelsey.api.core.Provider
import com.bfkelsey.api.jdbi.ProviderDAO
import io.dropwizard.jersey.params.IntParam
import org.slf4j.LoggerFactory
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/providers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ProviderResource(val providerDAO: ProviderDAO) {
    val logger = LoggerFactory.getLogger(ProviderResource::class.java)
    @GET
    fun getAll(): Response {
        logger.info("Handling request to list providers")
        return Response.ok(providerDAO.findAll()).build()
    }

    @POST
    fun create(@Valid provider: Provider) : Response {
        logger.info("Handling request to create a provider")
        val id = providerDAO.insert(provider)
        return Response.ok(providerDAO.find(id)).build()
    }

    @GET
    @Path("/{id}")
    fun get(@PathParam("id") id: IntParam) : Response {
        logger.info("Handling request to get provider with id: $id")
        val provider = providerDAO.find(id.get())
        if (provider != null) {
            return Response.ok(provider).build()
        }
        logger.error("Provider with id: $id was not found")
        return Response.status(Response.Status.NOT_FOUND).build()
    }

    @PUT
    @Path("/{id}")
    fun update(@PathParam("id") id: IntParam, provider: Provider) : Response {
        logger.info("Handling request to update provider with id: $id")
        if (providerDAO.update(id.get(), provider) == 1) {
            return Response.ok(providerDAO.find(id.get())).build()
        }
        logger.error("Provider with id: $id was not found")
        return Response.status(Response.Status.NOT_FOUND).build()
    }

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: IntParam) : Response {
        logger.info("Handling request to delete provider with id: $id")
        if (providerDAO.delete(id.get()) == 1) {
            return Response.ok().build()
        }
        logger.error("Provider with id: $id was not found")
        return Response.status(Response.Status.NOT_FOUND).build()
    }
}
