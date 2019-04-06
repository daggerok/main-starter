package com.github.daggerok

import org.slf4j.LoggerFactory
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.se.SeContainerInitializer
import javax.enterprise.inject.spi.BeanManager

private val log = LoggerFactory.getLogger(App::class.java)

@ApplicationScoped
open class App

fun main() {
  log.info("Hello!")
  SeContainerInitializer.newInstance()
      .setClassLoader(App::class.java.classLoader)
      // require <exclude name="org.jboss.weld.**"/> into beans.xml
      .addPackages(true, App::class.java, BeanManager::class.java)
      .initialize()
}
