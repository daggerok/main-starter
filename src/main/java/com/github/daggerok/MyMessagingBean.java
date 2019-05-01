package com.github.daggerok;

import com.github.daggerok.log.LogMe;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.streams.operators.PublisherBuilder;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class MyMessagingBean {

  @Outgoing("source")
  public PublisherBuilder<String> source() {
    log.debug("source");
    return ReactiveStreams.of("hello", "with", "SmallRye", "reactive", "message");
  }

  @Incoming("source")
  @Outgoing("processed-a")
  public String toUpperCase(String payload) {
    log.debug("payload: {}", payload);
    return payload.toUpperCase();
  }

  @Incoming("processed-a")
  @Outgoing("processed-b")
  public PublisherBuilder<String> filter(PublisherBuilder<String> input) {
    log.debug("input: {}", input);
    return input.filter(item -> item.length() > 4);
  }

  @LogMe
  @Incoming("processed-b")
  public void sink(String word) {
    //System.out.println(">> " + word);
    log.info(">> {}", word);
  }
}
