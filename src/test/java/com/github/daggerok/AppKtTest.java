package com.github.daggerok;

import static org.assertj.core.api.Assertions.assertThat;

/*
// Junit 4:
import org.junit.Test;

public class AppKtTest {

  @Test
  public void main() {
    final App app = new App();
    assertThat(app).isNotNull();
    AppKt.main();
  }
}
*/

// Junit 5 (Jupiter):
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Junit 5 Test")
class AppKtTest {

  @Test
  void main() {
    final App app = new App();
    assertThat(app).isNotNull();
    AppKt.main();
  }
}
