package com.github.daggerok;

import static org.assertj.core.api.Assertions.assertThat;

// Junit 4 (vintage):
import org.jboss.weld.environment.se.StartMain;
import org.junit.Test;

public class AppTest {

  @Test
  public void main() {
    final App app = new App();
    assertThat(app).isNotNull();
    App.main(new String[0]);
    StartMain.main(new String[0]);
  }
}

/*
// Junit 5 (Jupiter):
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Junit 5 Test")
class AppTest {

  @Test
  void main() {
    final App app = new App();
    assertThat(app).isNotNull();
  }
}
*/
