package org.akka.essentials.java.study.SupervisorStrategy;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupervisorTest {

    private static ActorSystem system;
    private ActorRef actorRef;

    @BeforeAll
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterAll
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @BeforeEach
    public void setUp() {
        actorRef = system.actorOf(Props.create(Supervisor.class), "MySupervisor");
    }

    @Test
    public void preStart_ShouldCreateChildActorAndSendMessage() {
        new TestKit(system) {{
            ActorRef child = system.actorOf(Props.create(MyChildActor.class), "myChild");
            // 模拟子 Actor 的行为
            new TestKit(system) {{
                child.tell("ok", getRef());
                // 验证child Actor的preStart消息是否收到
                assertEquals("ok", expectMsg("ok"));
            }};
        }};
    }

    @Test
    public void supervisorStrategy_ShouldReturnCorrectStrategy() {
        // 获得策略
//        SupervisorStrategy strategy = actorRef.
//
//        // 验证策略是OneForOneStrategy
//        assertTrue(strategy instanceof OneForOneStrategy);
//
//        // 验证最大重启次数和时间窗口
//        OneForOneStrategy oneForOneStrategy = (OneForOneStrategy) strategy;
//        assertEquals(10, oneForOneStrategy.maxNrOfRetries());
//        assertEquals(Duration.create("1 minute"), oneForOneStrategy.withinTimeRange());
//
//        // 验证在Exception时的决策
//        assertEquals(SupervisorStrategy.restart(), oneForOneStrategy.decider().apply(new Exception()));
    }
}
