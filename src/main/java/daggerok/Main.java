package daggerok;

import lombok.extern.log4j.Log4j2;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

@Log4j2
public class Main {

    public static void main(String[] args) {
        SeContainerInitializer initializer
                = SeContainerInitializer.newInstance()
                                        .disableDiscovery()
                                        .addPackages(true, Main.class);
        try (SeContainer container = initializer.initialize()) {
            Main application = container.select(Main.class).get();
            application.sayHello();
        }
    }

    private void sayHello() {
        log.info("hello!");
    }
}
