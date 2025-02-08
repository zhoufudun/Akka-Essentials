package org.akka.essentials.java.study.mailBox;

import akka.actor.UntypedActor;

public class MailBoxActor extends UntypedActor {

    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println("receive msg="+msg);
    }

}
