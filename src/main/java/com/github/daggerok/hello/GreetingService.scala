package com.github.daggerok.hello

import java.lang.String.format
import java.util.Optional
import java.util.concurrent.{CompletableFuture, CompletionStage}

import com.github.daggerok.infrastructure.LogMe
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.slf4j.Logger

@ApplicationScoped
class GreetingService(@LogMe @Inject
                      var log: Logger = null,
                      @Inject

                      @ConfigProperty(name = "greeting", defaultValue = "привет")
                      var greeting: String = null,

                      @Inject
                      @ConfigProperty(name = "default.anonymous.name", defaultValue = "никто")
                      var anonymous: String = null) {

//@ApplicationScoped
//class GreetingService {
//
//  @LogMe
//  @Inject
//  var log: Logger = _
//
//  @Inject
//  @ConfigProperty(name = "greeting", defaultValue = "привет")
//  var greeting: String =_
//
//  @Inject
//  @ConfigProperty(name = "default.anonymous.name", defaultValue = "никто")
//  var anonymous: String = _

  def greetingNoArg: CompletionStage[String] = {
    log.info("greetingNoArg()")
    CompletableFuture.supplyAsync(() => greeting)
  }

  def greeting(maybeName: String): CompletionStage[String] = {
    log.info("greeting('{}')", maybeName)
    CompletableFuture
      .supplyAsync(() => Optional
        .ofNullable(maybeName)
        .map[String](s => s.trim)
        .filter(s => !s.isEmpty)
        .orElse(anonymous))
      .thenApply(name => format("%s, %s!", greeting, name))
  }
}
