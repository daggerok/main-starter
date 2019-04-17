package com.github.daggerok;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/*
// Junit 4:
import org.junit.*;

public class AppTest {

  @Test
  public void context() {
    GenericApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
    assertThat(ctx).isNotNull();
  }

  @Test
  public void main() {
    App.main(new String[0]);
  }
}
*/

// Junit 5 (Jupiter):
import org.junit.jupiter.api.*;

@DisplayName("Akka tests")
class AppTest {

  @Test
  void context() {
    GenericApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
    assertThat(ctx).isNotNull();
  }

  @Test
  void main() {
    App.main(new String[0]);
  }
}
