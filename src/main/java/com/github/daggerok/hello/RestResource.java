package com.github.daggerok.hello;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.concurrent.CompletionStage;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/v1/hello")
@Produces(APPLICATION_JSON)
public class RestResource {

  @Inject
  GreetingService greetingService;

  @GET
  @Path("")
  public CompletionStage<String> index() {
    return greetingService.greeting();
  }

  @GET
  @Path("{name}")
  public CompletionStage<String> hello(@PathParam("name") String name) {
    return greetingService.greeting(name);
  }
}
