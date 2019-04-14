package com.github.daggerok;

import io.vavr.Lazy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static java.lang.String.format;

@Configuration
class Cfg {

  @Bean
  public String myString() {
    return "42";
  }
}

@Component
class Greeter implements Function<String, String> {

  @Override
  public String apply(String name) {
    return format("hello, %s!", name);
  }
}

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = App.class)
public class App {
/*
  private static final Lazy<GenericApplicationContext> context = Lazy.of(() -> {
    String basePackage = App.class.getPackage().getName();
    GenericApplicationContext parent = new AnnotationConfigApplicationContext(basePackage);
    GenericApplicationContext ctx = new GenericApplicationContext(parent);
    ctx.refresh();
    return ctx;
  });
*/

  private static final Lazy<GenericApplicationContext> context =
      Lazy.of(() -> new AnnotationConfigApplicationContext(App.class));

  public static void main(String[] args) {
    Function<String, String> function = context.get().getBean(Function.class);

    log.info("result: {}", function.apply("Max"));
    log.info(context.get().getBean(String.class));
    log.info(context.get().getApplicationName());
  }
}
