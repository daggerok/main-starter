package com.github.daggerok

import assertk.assertThat
import assertk.assertions.isNotNull

/*
import org.junit.Test

class AppTest {

  @Test
  fun main() {
    val app = App()
    assertThat(app).isNotNull()
    com.github.daggerok.main()
  }
}
*/

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Junit 5 test")
class AppTest {

  @Test
  fun main() {
    val app = App()
    assertThat(app).isNotNull()
    com.github.daggerok.main()
  }
}
