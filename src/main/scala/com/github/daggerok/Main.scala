package com.github.daggerok

import akka.actor.{Actor, ActorRef, ActorSystem, Props, Terminated}
import com.github.daggerok.Publisher.{Publish, Subscribe, Unsubscribe}
import org.slf4j.LoggerFactory

object Publisher {
  case class Publish(topic: String, message: Any)
  case class Published(publish: Publish)

  case class Subscribe(topic: String, subscriber: ActorRef)
  case class Subscribed(subscribe: Subscribe)
  case class AlreadySubscribed(subscribe: Subscribe)

  case class Unsubscribe(topic: String, subscriber: ActorRef)
  case class Unsubscribed(unsubscribe: Unsubscribe)
  case class NotSubscribed(unsubscribe: Unsubscribe)

  case class GetSubscribers(topic: String)

  def props(ref: ActorRef) = Props(new Publisher(ref))
}

class Publisher(loggerRef: ActorRef) extends Actor {
  import Publisher._

  private var counter = 0
  private val log = LoggerFactory.getLogger(getClass.getName)
  private var subscribers = Map.empty[String, Set[ActorRef]].withDefaultValue(Set.empty)

  override def receive = {
    case publication@Publish(topic, message) =>
      counter += 1
      log.info(s"counter: $counter")
      subscribers(topic).foreach(_ ! message)
      loggerRef ! Published(publication)

    case subscription@Subscribe(topic, subscriber) =>
      counter += 1
      log.info(s"counter: $counter")
      if (subscribers(topic).contains(subscriber))
        loggerRef ! AlreadySubscribed(subscription)
      else {
        subscribers += topic -> (subscribers(topic) - subscriber)
        loggerRef ! Subscribed(subscription)
      }

    case unsubscription@Unsubscribe(topic, subscriber) =>
      counter += 1
      log.info(s"counter: $counter")
      if (!subscribers(topic).contains(subscriber))
        loggerRef ! NotSubscribed(unsubscription)
      else {
        subscribers += topic -> (subscribers(topic) - subscriber)
        loggerRef ! Unsubscribed(unsubscription)
      }

    case GetSubscribers(topic) =>
      counter += 1
      log.info(s"counter: $counter")
      loggerRef ! subscribers(topic)
  }
}

object LoggerActor {
  def props = Props(new LoggerActor)
}

class LoggerActor extends Actor {
  private val log = LoggerFactory.getLogger(getClass.getName)

  override def receive: Receive = {
    case any => log.info(s"received: $any")
  }
}

object Main extends App { // def main()
  val system = ActorSystem("pub-sub")
  val loggerRef = system.actorOf(LoggerActor.props, LoggerActor.getClass.getSimpleName)
  val mediatorRef = system.actorOf(Publisher.props(loggerRef), Publisher.getClass.getSimpleName)

  val topic = "hello"
  mediatorRef ! Publish(topic, "1st")

  val subscription = Subscribe(topic, loggerRef)
  mediatorRef ! subscription
  mediatorRef ! Publish(topic, "2nd")

  mediatorRef ! subscription
  mediatorRef ! Unsubscribe(topic, loggerRef)

  Thread.sleep(1111)
  system.terminate()
}
