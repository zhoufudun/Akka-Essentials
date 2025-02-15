package org.akka.essentials.remotenode;

import akka.actor.ActorSystem;
import akka.actor.Props;

import akka.kernel.Bootable;
import com.typesafe.config.ConfigFactory;

public class ServerNodeApplication implements Bootable {


    static final ActorSystem system = ActorSystem.create("ServerNodeApp", ConfigFactory.load().getConfig("ServerSys"));

    static {
        // Create the 'remoteActor' actor，提供远程actor
        system.actorOf(new Props(ServerActor.class), "remoteActor");
    }

    public void shutdown() {
        system.shutdown();
    }

    public void startup() {
        System.out.println("ServerNodeApplication startup");
    }
}
