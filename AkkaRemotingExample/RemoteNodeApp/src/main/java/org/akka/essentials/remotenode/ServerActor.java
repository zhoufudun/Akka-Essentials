package org.akka.essentials.remotenode;

import akka.actor.UntypedActor;

public class ServerActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("ServerActor received message: " + message);
        // Get reference to the message sender and reply back
        getSender().tell(message + " bye");
    }
}
