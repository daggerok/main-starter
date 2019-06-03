package com.github.daggerok

import org.junit.jupiter.api.{DisplayName, Test}
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class ScalaJupiterTest {

  @Test
  @DisplayName("integration test") def integration_test = Main.main(Array())

  @Test
  @DisplayName("spring context test") def spring_context_test = {
    val ctx = new AnnotationConfigApplicationContext(classOf[Ctx])
    val message: String = ctx.getBean(classOf[String])
    assert(message == "Hello, World!")
  }
}
