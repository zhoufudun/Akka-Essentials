package org.akka.essentials.localnode;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

public class ClientActor extends UntypedActor {
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	Timeout timeout = new Timeout(FiniteDuration.apply(5, TimeUnit.SECONDS));

	ActorRef remoteActor;

	@Override
	public void preStart() {
		System.out.println("preStart");
		// 请求远程服务
		//Get a reference to the remote actor
		remoteActor = getContext().actorFor(
				// complete remote actor path
				"akka.tcp://ServerNodeApp@127.0.0.1:2551/user/remoteActor");
		System.out.println("remoteActor==="+remoteActor.toString());
	}

	@Override
	public void onReceive(Object message) throws Exception {
		System.out.println("onReceive received message: " + message);
		Future<Object> future = Patterns.ask(remoteActor, message, timeout); // 阻塞等待
		String result = (String) Await.result(future, timeout.duration());
		log.info("Message received from Server -> {}", result);
//		remoteActor.tell(message, getSelf());
	}
}
