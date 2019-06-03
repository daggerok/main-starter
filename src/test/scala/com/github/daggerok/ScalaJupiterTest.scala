package com.github.daggerok

import org.junit.jupiter.api.{DisplayName, Test}

class ScalaJupiterTest {

  @Test
  @DisplayName("integration test") def integration_test =
    Main.main(Array())

  @Test
  @DisplayName("spring context test") def spring_context_test =
    assert(Messages.hello == "Hello, World!")
}
