package com.github.daggerok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SrcKotlinAppKotlinJupiterTest {
  @Test internal fun `kotlin jupiter test from kotlin test sources`() {
    val args = arrayOf<String>()
    assertThat(args).isNotNull
    App.main(args)
  }
}
