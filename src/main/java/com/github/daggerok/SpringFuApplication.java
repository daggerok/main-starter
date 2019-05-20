package com.github.daggerok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.fu.jafu.ConfigurationDsl;
import org.springframework.fu.jafu.JafuApplication;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static org.springframework.fu.jafu.Jafu.webApplication;
import static org.springframework.fu.jafu.r2dbc.H2R2dbcDsl.r2dbcH2;
import static org.springframework.fu.jafu.web.WebFluxServerDsl.server;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.ServerResponse.accepted;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
class User {
  private String login;
  private String firstName;
  private String lastName;
}

@Log4j2
@RequiredArgsConstructor
class UserRepository {
  private final DatabaseClient client;

  void init() {
    client.execute()
          .sql("      CREATE TABLE IF NOT EXISTS users (  " +
                   "      login VARCHAR PRIMARY KEY       " +
                   "    , first_name VARCHAR              " +
                   "    , last_name VARCHAR               " +
                   "  );                                  "
          )
          .then()
          .then(deleteAll())
          .then(save(User.of("smaldini", "Stéphane", "Maldini")))
          .then(save(User.of("sdeleuze", "Sébastien", "Deleuze")))
          .then(save(User.of("jlong", "Joshua", "Long")))
          .then(save(User.of("bclozel", "Brian", "Clozel")))
          .thenMany(findAll())
          .subscribe(user -> log.info("{}", user));
  }

  Mono<Long> count() {
    return client.execute()
                 .sql("SELECT COUNT(*) FROM users")
                 .map((row, rowMetadata) -> row.get("COUNT(*)"))
                 .one()
                 .cast(Long.class)
        ;
  }

  Flux<User> findAll() {
    return client.select()
                 .from("users")
                 .as(User.class)
                 .fetch()
                 .all();
  }

  /**
   * https://docs.spring.io/spring-data/r2dbc/docs/1.0.0.M1/reference/html/#r2dbc.datbaseclient.binding
   */
  Mono<User>/* Mono<Map<String, Object>> */ findOne(String login) {
    return client.execute()
                 ////1:
                 //.sql("SELECT * FROM users WHERE login = ?1")
                 //.bind("$1", login)
                 ////2:
                 //.sql("SELECT * FROM users WHERE login = ?1")
                 //.bind(0, login)
                 ////3:
                 .sql("SELECT * FROM users WHERE login = $1")
                 .bind("$1", login)
                 ////4:
                 //.sql("SELECT * FROM users WHERE login = $1")
                 //.bind(0, login)
                 .as(User.class)
                 .fetch()
                 .one()
        ;
  }

  Mono<User> save(User user) {
    return client.insert()
                 .into(User.class)
                 .table("users")
                 .using(user)
                 .map((r, m) -> User.of(r.get("login", String.class),
                                        r.get("first_name", String.class),
                                        r.get("last_name", String.class)))
                 .one();
  }

  Mono<User> update(String login, User user) {
    Objects.requireNonNull(login, "login may not be null");
    Objects.requireNonNull(user, "user may not be null");
    Objects.requireNonNull(user.getLogin(), "user.login may not be null");
    Objects.requireNonNull(user.getFirstName(), "user.firstName may not be null");
    Objects.requireNonNull(user.getLastName(), "user.lastName may not be null");
    return client.execute()
                 .sql("     UPDATE users        " +
                          " SET first_name = $1 " +
                          "   , last_name = $2  " +
                          "   , login = $3      " +
                          " WHERE login = $4    ")
                 .bind("$1", user.getFirstName())
                 .bind("$2", user.getLastName())
                 .bind("$3", user.getLogin())
                 .bind("$4", login)
                 .fetch()
                 .one()
                 .then(findOne(login));
  }

  Mono<Map<String, Object>>/*<Void>*/ deleteOne(String login) {
    return client.execute()
                 .sql("DELETE FROM users WHERE login = $1")
                 .bind("$1", login)
                 .fetch()
                 .one();
  }

  Mono<Map<String, Object>>/*<Void>*/ deleteAll() {
    return client.execute()
                 .sql("DELETE FROM users")
                 .fetch()
                 .one();
  }
}

@RequiredArgsConstructor
class UserHandler {
  private static final ParameterizedTypeReference<Map<String, Object>> genericType =
      new ParameterizedTypeReference<Map<String, Object>>() {};

  private final UserRepository repository;

  Mono<ServerResponse> count(ServerRequest request) {
    return ok().contentType(APPLICATION_JSON_UTF8)
               .body(repository.count(), Long.class);
               //.body(repository.count(), genericType);
  }

  Mono<ServerResponse> getAll(ServerRequest request) {
    return ok().contentType(APPLICATION_JSON_UTF8)
               .body(repository.findAll(), User.class);
  }

  Mono<ServerResponse> getOne(ServerRequest request) {
    String login = request.pathVariable("login");
    return ok().contentType(APPLICATION_JSON_UTF8)
               .body(repository.findOne(login), User.class);
                //.body(repository.findOne(login), genericType);
  }

  Mono<ServerResponse> add(ServerRequest request) {
    return accepted().contentType(APPLICATION_JSON_UTF8)
                     .body(request.bodyToMono(User.class)
                                  .flatMap(repository::save), User.class);
  }

  public Mono<ServerResponse> edit(ServerRequest request) {
    String login = request.pathVariable("login");
    return accepted().contentType(APPLICATION_JSON_UTF8)
                     .body(request.bodyToMono(User.class)
                                  .flatMap(u -> repository.update(login, u)), User.class);
  }

  Mono<ServerResponse> rmOne(ServerRequest request) {
    String login = request.pathVariable("login");
    return accepted().contentType(APPLICATION_JSON_UTF8)
                     //.body(repository.deleteOne(login), Void.class);
                     .body(repository.deleteOne(login), genericType);
  }

  Mono<ServerResponse> rmAll(ServerRequest request) {
    return accepted().contentType(APPLICATION_JSON_UTF8)
                     //.body(repository.deleteAll(), Void.class);
                     .body(repository.deleteAll(), genericType);
  }
}

public class SpringFuApplication {
  public static void main(String[] args) {

    Consumer<ConfigurationDsl> r2dbcConfig = c -> c
        .beans(beans -> beans.bean(UserRepository.class))
        .enable(r2dbcH2());

    Consumer<ConfigurationDsl> webFluxConfig = c -> c
        .beans(beans -> beans.bean(UserHandler.class))
        .enable(
            server(
                server -> server
                    .router(router -> {
                      UserHandler userHandler = c.ref(UserHandler.class);
                      router.GET("/count", userHandler::count);
                    })
                    .router(router -> {
                      UserHandler userHandler = c.ref(UserHandler.class);
                      router.GET("/{login}", userHandler::getOne);
                    })
                    .router(router -> {
                      UserHandler userHandler = c.ref(UserHandler.class);
                      router.GET("/", userHandler::getAll);
                    })
                    .router(router -> {
                      UserHandler userHandler = c.ref(UserHandler.class);
                      router.POST("/", userHandler::add);
                    })
                    .router(router -> {
                      UserHandler userHandler = c.ref(UserHandler.class);
                      router.PUT("/{login}", userHandler::edit);
                    })
                    .router(router -> {
                      UserHandler userHandler = c.ref(UserHandler.class);
                      router.DELETE("/{login}", userHandler::rmOne);
                    })
                    .router(router -> {
                      UserHandler userHandler = c.ref(UserHandler.class);
                      router.DELETE("/", userHandler::rmAll);
                    })
                    .codecs(codecs -> codecs.string()
                                            .jackson())
            )
        );

    JafuApplication jafu = webApplication(
        app -> app.enable(r2dbcConfig)
                  .enable(webFluxConfig)
                  .listener(ApplicationReadyEvent.class, event -> app.ref(UserRepository.class)
                                                                     .init())
    );

    jafu.run(args);
  }
}
