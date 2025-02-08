package org.akka.essentials.java.study.SupervisorStrategy;

import akka.actor.UntypedActor;

public class MyChildActor extends UntypedActor {

    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println("MyChildActor receive msg="+msg);
        if(msg.equals("ok")){
            getSender().tell("ok",getSelf());
        }
    }
}
