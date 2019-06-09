package com.github.daggerok;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SrcJavaAppJavaVintageTest {
  @Test public void java_vintage_test_from_java_test_sources() {
    final App app = new App();
    assertThat(app).isNotNull();
    App.main(null);
  }
}
