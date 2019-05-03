package com.github.daggerok;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import java.util.Objects;

@Slf4j
@ApplicationScoped
public class GreetingService {

  public String greeting(String maybeName) {
    log.info(maybeName);
    final String name = Objects.requireNonNull(maybeName, "maybeName is required");
    return "hello, " + name;
  }
}
