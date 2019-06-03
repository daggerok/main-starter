package com.github.daggerok

import org.springframework.context.annotation.{AnnotationConfigApplicationContext, Bean}
import org.springframework.stereotype.Component

@Component
class Ctx {
  @Bean def message() = "Hello, World!"
}

object Main extends App { // def main()
  val ctx = new AnnotationConfigApplicationContext(classOf[Ctx])
  val message = ctx getBean classOf[String]
  println(message)
}
