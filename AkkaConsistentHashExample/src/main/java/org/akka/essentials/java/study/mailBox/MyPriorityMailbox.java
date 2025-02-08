package org.akka.essentials.java.study.mailBox;

import akka.dispatch.PriorityGenerator;
import akka.dispatch.UnboundedPriorityMailbox;
import com.typesafe.config.Config;
import akka.actor.ActorSystem;

public class MyPriorityMailbox extends UnboundedPriorityMailbox {

    public MyPriorityMailbox(ActorSystem.Settings settings, Config config) {
        super(new PriorityGenerator() {
            @Override
            public int gen(Object message) {
                // 值越小优先级越高
                if (message instanceof Integer)
                    return (Integer) message;
                return 100000;
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });
    }
}
