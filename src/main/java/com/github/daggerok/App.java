package com.github.daggerok;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.concurrent.TimeUnit;

/**
 * This class is using only for debug by running main method from IDE.
 *
 * Real main class should be: {@link org.jboss.weld.environment.se.StartMain}
 * java -cp build/libs/*-all.jar org.jboss.weld.environment.se.StartMain arg1 arg2
 */
@Slf4j
@ApplicationScoped
public class App {
  public static void main(String[] args) {
    SeContainerInitializer.newInstance()
                          .addPackages(true, App.class)
                          .enableInterceptors(com.github.daggerok.log.LogMeInterceptor.class) // not needed on annotated
                          .disableDiscovery()
                          .initialize();
    Try.run(() -> TimeUnit.SECONDS.sleep(1))
       .onFailure(e -> log.error("oops: {} ", e.getLocalizedMessage(), e));
  }
}
