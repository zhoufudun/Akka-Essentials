package org.akka.essentials.java.study.example.Dispatcher;

import org.akka.essentials.java.study.MsgEchoActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinRouter;

import com.typesafe.config.ConfigFactory;

public class Example2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ActorSystem _system = ActorSystem.create("default-dispatcher",
				ConfigFactory.load().getConfig("MyDispatcherExample"));
		
		ActorRef actor = _system.actorOf(new Props(MsgEchoActor.class)
				.withDispatcher("defaultDispatcher1").withRouter(
						new RoundRobinRouter(5)));

		for (int i = 0; i < 25; i++) {
			actor.tell(i);
		}

		_system.shutdown();

	}

}
