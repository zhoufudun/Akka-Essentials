package org.akka.essentials.localnode;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Hello world!
 * 
 */
public class ClientNodeApplication {
	public static void main(String[] args) throws Exception {
		ActorSystem _system = ActorSystem.create("ClientNodeApp",ConfigFactory
				.load().getConfig("ClientSys"));
		ActorRef localActor = _system.actorOf(Props.create(ClientActor.class));
		localActor.tell("Hello",null);

		Thread.sleep(100000);
		_system.shutdown();
	}
}
