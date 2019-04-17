package com.github.daggerok;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

class MyActor extends AbstractActor {

  public static Props props() {
    return Props.create(MyActor.class, MyActor::new);
  }

  public static String name() {
    return MyActor.class.getName();
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .matchEquals("ping", s -> {
          context().system().log().info("{}", s);
          ////context().sender().tell("pong", self());
        })
        .matchEquals("fail", s -> {
          context().system().log().info(s);
          throw new RuntimeException("expected fail!");
        })
        .matchEquals("stop", s -> {
          context().system().log().info("self {}", s);
          context().stop(self());
        })
        .matchEquals("terminate", s -> {
          context().system().log().info("{} system!", s);
          context().system().terminate();
        })
        .matchAny(o -> {
          context().system().log().info("{} system!", o);
          throw new IllegalAccessException("unexpected: " + o);
        })
        .build();
  }
}

@Configuration
class Cfg {

  @Bean
  public ActorSystem actorSystem() {
    return ActorSystem.create();
  }
}

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = App.class)
public class App {

  public static void main(String[] args) {
    ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
    ActorSystem actorSystem = ctx.getBean(ActorSystem.class);
    ActorRef actorRef = actorSystem.actorOf(MyActor.props(), MyActor.name());

    actorRef.tell("ping", ActorRef.noSender());
    actorRef.tell("fail", ActorRef.noSender());
    actorRef.tell("ololo", ActorRef.noSender());
    actorRef.tell("stop", ActorRef.noSender());
    actorRef.tell("terminate", ActorRef.noSender());

    Try.run(() -> TimeUnit.SECONDS.sleep(1))
       .andFinally(actorSystem::terminate);
  }
}
