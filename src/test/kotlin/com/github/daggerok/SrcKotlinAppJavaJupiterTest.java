package com.github.daggerok;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * FIXME: This test is not going to be executed with 'mvn test' command!
 */
class SrcKotlinAppJavaJupiterTest {
  @Test @DisplayName("java jupiter test from kotlin test sources")
  void java_jupiter_test_from_kotlin_test_sources() {
    String[] args = new String[0];
    assertThat(args).isNotNull();
    App.main(args);
  }
}
