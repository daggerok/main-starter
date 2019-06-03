package com.github.daggerok

import org.junit.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class ScalaVintageTest {

  @Test def integration_test() = Main.main(Array())

  @Test def spring_context_test() = {
    val ctx = new AnnotationConfigApplicationContext(classOf[Ctx])
    val message: String = ctx.getBean(classOf[String])
    assert(message == "Hello, World!")
  }
}
