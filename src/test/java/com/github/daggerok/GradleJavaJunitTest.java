package com.github.daggerok;

import org.junit.Test;

/**
 * This one is working only with Gradle (not Maven)
 */
public class GradleJavaJunitTest {

  @Test
  public void integrationTest() {
    Ctx.main(new String[0]);
  }

  @Test
  public void scalaInteropTest() {
    Main.main(new String[0]);
  }
}
