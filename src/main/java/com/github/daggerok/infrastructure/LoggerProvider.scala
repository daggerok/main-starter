package com.github.daggerok.infrastructure

import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.enterprise.inject.spi.InjectionPoint
import org.slf4j.{Logger, LoggerFactory}

@ApplicationScoped
class LoggerProvider {

  @LogMe
  @Produces
  def log(injectionPoint: InjectionPoint): Logger =
    LoggerFactory.getLogger(injectionPoint.getMember.getDeclaringClass.getName)
}
