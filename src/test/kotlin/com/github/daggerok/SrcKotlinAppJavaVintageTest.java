package com.github.daggerok;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * FIXME: This test is not going to be executed with 'mvn test' command!
 */
public class SrcKotlinAppJavaVintageTest {
  @Test public void java_vintage_test_from_kotlin_test_sources() {
    final App app = new App();
    assertThat(app).isNotNull();
    App.main(null);
  }
}
