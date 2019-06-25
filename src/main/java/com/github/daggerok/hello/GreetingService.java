package com.github.daggerok.hello;

import com.github.daggerok.infrastructure.LogMe;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static java.lang.String.format;

@ApplicationScoped
public class GreetingService {

  @LogMe
  @Inject
  Logger log;

  @Inject
  @ConfigProperty(name = "greeting", defaultValue = "привет")
  String greeting;

  @Inject
  @ConfigProperty(name = "default.anonymous.name", defaultValue = "никто")
  String anonymous;

  public CompletionStage<String> greeting() {
    log.info("greeting()");
    return CompletableFuture.supplyAsync(() -> greeting);
  }

  public CompletionStage<String> greeting(String maybeName) {
    log.info("greeting('{}')", maybeName);
    return CompletableFuture.supplyAsync(() -> Optional.ofNullable(maybeName)
                                                       .map(String::trim)
                                                       .filter(s -> !s.isEmpty())
                                                       .orElse(anonymous))
                            .thenApply(name -> format("%s, %s!", greeting, name))
                            .exceptionally(Throwable::getLocalizedMessage);
  }
}
