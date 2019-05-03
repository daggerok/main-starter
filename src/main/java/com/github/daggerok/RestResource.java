package com.github.daggerok;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.util.concurrent.CompletableFuture;
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
    return CompletableFuture.supplyAsync(() -> "hello");
  }

  @GET
  @Path("{name}")
  public CompletionStage<String> hello(@PathParam("name") String name) {
    return null == name || name.isEmpty() ? index()
        : CompletableFuture.supplyAsync(() -> greetingService.greeting(name));
  }
}
