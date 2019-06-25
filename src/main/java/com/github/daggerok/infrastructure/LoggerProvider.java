package com.github.daggerok.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class LoggerProvider {

  @LogMe
  @Produces
  public Logger log(InjectionPoint injectionPoint) {
    final String name = injectionPoint.getMember().getDeclaringClass().getName();
    return LoggerFactory.getLogger(name);
  }
}
