package com.github.daggerok.log;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import static javax.interceptor.Interceptor.Priority.APPLICATION;

@Slf4j
@LogMe
@Interceptor
@Priority(APPLICATION)
public class LogMeInterceptor {

  @AroundInvoke
  public Object invoke(InvocationContext ctx) {
    String name = ctx.getMethod().getName();
    log.debug("enter {}", name);
    return Try.of(ctx::proceed)
              .map(o -> {
                log.debug("result: {}", o);
                return o;
              })
              .onFailure(e -> log.error("oops: {}", e.getLocalizedMessage(), e))
              .andFinally(() -> log.debug("exit {}", name))
              .getOrNull();
  }
}
