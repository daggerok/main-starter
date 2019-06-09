package com.github.daggerok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SrcJavaAppKotlinJupiterTest {
  @Test internal fun `kotlin jupiter test from java test sources`() {
    val args = arrayOf<String>()
    assertThat(args).isNotNull
    App.main(args)
  }
}
