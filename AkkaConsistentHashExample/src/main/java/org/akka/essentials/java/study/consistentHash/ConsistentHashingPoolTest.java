package org.akka.essentials.java.study.consistentHash;

import akka.routing.ConsistentHashingPool;
import org.akka.essentials.java.study.MsgEchoActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

public class ConsistentHashingPoolTest {
    public static void main(String[] args) {
        ActorSystem _system = ActorSystem.create("hashing-dispatcher",
                ConfigFactory.load().getConfig("MyDispatcherExample"));

        // 创建 ConsistentHashingPool，指定 5 个 Actor
        ConsistentHashingPool hashingPool = new ConsistentHashingPool(5)
                .withHashMapper(message -> {
                    // 让相同的消息哈希到同一个 Actor
                    return message;

                });

        // 创建路由 Actor，指定 dispatcher
        ActorRef actor = _system.actorOf(Props.create(MsgEchoActor.class)
                .withDispatcher("FixDispatcher")
                .withRouter(hashingPool));

        // 发送 10 个消息
        for (int i = 0; i < 10; i++) {
            actor.tell(i % 5, null);  // 0, 1, 2, 3, 4, 0, 1, 2. 3，4，0..
        }

        _system.terminate();
    }
}
