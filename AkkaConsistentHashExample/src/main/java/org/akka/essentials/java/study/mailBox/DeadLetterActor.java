package org.akka.essentials.java.study.mailBox;

import akka.actor.DeadLetter;
import akka.actor.UntypedActor;

public class DeadLetterActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof DeadLetter) {
            DeadLetter letter = (DeadLetter) message;
            System.out.println("akka actor: " + letter.sender() + " receive DeadLetter: " + letter.message());
        }
    }
}