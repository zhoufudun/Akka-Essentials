package org.akka.essentials.java.dispatcher.example.PinnedDispatcher;

import org.akka.essentials.java.dispatcher.example.MsgEchoActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinRouter;

import com.typesafe.config.ConfigFactory;

public class Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ActorSystem _system = ActorSystem.create("pinned-dispatcher",
				ConfigFactory.load().getConfig("MyDispatcherExample"));
		
		ActorRef actor = _system.actorOf(new Props(MsgEchoActor.class)
				.withDispatcher("pinnedDispatcher").withRouter(
						new RoundRobinRouter(2)));

		for (int i = 0; i < 16; i++) {
			actor.tell(i);
		}

		_system.shutdown();

	}

}
