package com.github.daggerok

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SrcKotlinAppKotlinVintageTest {
  @Test fun `kotlin vintage test from kotlin test sources`() {
    val args = arrayOf<String>()
    assertThat(args).isNotNull
    App.main(args)
  }
}
