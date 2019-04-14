package com.github.daggerok;

import io.vavr.Lazy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
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
@SpringBootApplication
public class App {

  public static void main(String[] args) {
    ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);
    Function<String, String> function = ctx.getBean(Function.class);

    log.info("result: {}", function.apply("Max"));
    log.info(ctx.getBean(String.class));
  }
}
