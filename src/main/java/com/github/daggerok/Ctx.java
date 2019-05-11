package com.github.daggerok;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = Ctx.class)
public class Ctx {

  public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(Ctx.class);
  }
}
