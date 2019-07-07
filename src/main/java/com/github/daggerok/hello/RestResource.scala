package com.github.daggerok.hello

import javax.inject.Inject
import javax.ws.rs._
import java.util.concurrent.CompletionStage

import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("/v1/hello")
@Produces(Array(APPLICATION_JSON))
class RestResource {

  @Inject //@scala.beans.BeanProperty
  var greetingService: GreetingService = _

  @GET @Path("")
  def index: CompletionStage[String] =
    greetingService.greetingNoArg

  @GET @Path("{name}")
  def hello(@PathParam("name") name: String): CompletionStage[String] =
    greetingService.greeting(name)
      .exceptionally(e => e.getLocalizedMessage)
}
