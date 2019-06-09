package com.github.daggerok;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SrcJavaAppJavaJupiterTest {
  @Test @DisplayName("java jupiter test from java test sources")
  void java_jupiter_test_from_java_test_sources() {
    String[] args = new String[0];
    assertThat(args).isNotNull();
    App.main(args);
  }
}
