package com.github.daggerok

import org.junit.Test

class ScalaVintageTest {

  @Test def integration_test() = Main.main(Array())

  @Test def spring_context_test() = assert(Messages.hello == "Hello, World!")
}
