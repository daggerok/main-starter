package com.github.daggerok;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletionStage;

@Configuration
class Cfg {

  @Bean
  public ActorSystem actorSystem() {
    return ActorSystem.create();
  }

  @Bean
  public Http http() {
    return Http.get(actorSystem());
  }

  @Bean
  public ActorMaterializer actorMaterializer() {
    return ActorMaterializer.create(actorSystem());
  }
}

@Service
@RequiredArgsConstructor
class MinimalHttp extends AllDirectives {

  private final Http http;
  private final ActorSystem actorSystem;
  private final ActorMaterializer materializer;

  @PostConstruct
  public void init() {
    Flow<HttpRequest, HttpResponse, NotUsed> httpFlow = createRoute().flow(actorSystem, materializer);
    ConnectHttp connect = ConnectHttp.toHost("localhost", 8080);
    CompletionStage<ServerBinding> binging = http.bindAndHandle(httpFlow, connect, materializer);
    Try.run(System.in::read)
       .andFinally(() -> binging.thenCompose(ServerBinding::unbind)
                                .thenAccept(done -> actorSystem.terminate()));
  }

  private Route createRoute() {
    return concat(
        path("", () ->
            get(() -> complete("<h1>Say hello to akka-http</h1>"))
        )
    );
  }
}

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = App.class)
public class App {

  public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
    Try.run(() -> ctx.getBean(MinimalHttp.class))
       .andFinally(ctx.getBean(ActorSystem.class)::terminate);
  }
}
