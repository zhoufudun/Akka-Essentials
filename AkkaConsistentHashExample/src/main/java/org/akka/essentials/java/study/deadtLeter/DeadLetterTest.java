package org.akka.essentials.java.study.deadtLeter;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.Props;
import org.junit.Test;

public class DeadLetterTest {

    @Test
    public void testDeadLetter() {
        ActorSystem actorSystem = ActorSystem.create("DeadLetterTest");
        ActorRef deadLetterActor = actorSystem.actorOf(Props.create(DeadLetterActor.class));
        actorSystem.eventStream().subscribe(deadLetterActor, DeadLetter.class);

        // 获取一个不存在的 Actor 引用
        ActorRef nonExistentActor = actorSystem.actorFor("user/nonExistentActor");

        // 向不存在的 Actor 发送消息，消息会到死信队列中去
        nonExistentActor.tell("dead msg", ActorRef.noSender());
        // 为了确保消息被处理，可以添加一些等待时间
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        actorSystem.terminate();
    }
}
