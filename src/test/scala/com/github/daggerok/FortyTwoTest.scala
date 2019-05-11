package com.github.daggerok

import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Matchers}
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FortyTwoTest extends FunSuite with Matchers {
  println("in 42 class")
  test("should test 42") {
    println("42 should be (42)")
    42 should be (42)
  }
}
