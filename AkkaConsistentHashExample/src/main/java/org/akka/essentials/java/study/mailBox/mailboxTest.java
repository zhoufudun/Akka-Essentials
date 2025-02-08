package org.akka.essentials.java.study.mailBox;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import org.akka.essentials.java.study.MsgEchoActor;
import org.junit.Test;

/**
 * Mailbox      类型	                            作用
 * 默认队列     （UnboundedMailbox）	            Akka 默认使用的 无界 FIFO（先进先出）队列
 * 有界队列     （BoundedMailbox）	            限制队列大小，防止 Actor 过载
 * 优先级队列    （PriorityMailbox）	            让高优先级的消息优先处理
 * 单独队列     （UnboundedDequeBasedMailbox）	适用于 Stash 操作（允许 Actor 重新排序消息）
 */
public class mailboxTest {
    /**
     *
     * 配置
     * my-mailbox {
     *     mailbox-type = "akka.dispatch.BoundedMailbox"
     *     mailbox-capacity = 50   # 最大消息数
     *     mailbox-push-timeout-time = 10s  # 发送超时
     * }
     */

    @Test
    public void test1() throws InterruptedException {

        ActorSystem system = ActorSystem.create("test",
                ConfigFactory.load().getConfig("MyDispatcherExample"));

        ActorRef actor = system.actorOf(
                Props.create(MailBoxActor.class)
                        .withMailbox("my-mailbox")  // 绑定自定义 Mailbox
        );

        for (int i = 10; i  > 0; i--) {
            actor.tell(i, null); // 按照优先级顺序，输出的顺序是：1，2，3，4，5，6，7，8，9，10
        }


        Thread.sleep(3000);
        system.terminate();
    }
}

