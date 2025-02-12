package org.akka.essentials.java.dispatcher.example.Dispatcher;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinRouter;
import org.akka.essentials.java.dispatcher.example.MsgEchoActor;

/**
 * Hello world!
 * 
 */
public class Example1 {
	public static void main(String[] args) {
		ActorSystem _system = ActorSystem.create("default-dispatcher",
				ConfigFactory.load().getConfig("MyDispatcherExample"));
		
		ActorRef actor = _system.actorOf(new Props(MsgEchoActor.class)
				.withDispatcher("defaultDispatcher").withRouter(
						new RoundRobinRouter(3)));

		for (int i = 0; i < 10; i++) {
			actor.tell(i);
		}

		_system.shutdown();
	}
}
