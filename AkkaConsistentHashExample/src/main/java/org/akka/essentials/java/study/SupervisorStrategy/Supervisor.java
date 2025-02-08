package org.akka.essentials.java.study.SupervisorStrategy;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

/**
 * 使用场景
 * 错误处理：当子Actor抛出异常时，父Actor可以定义如何响应这些异常。这包括重启、停止或恢复子Actor。
 * 容错性增强：通过定义策略，确保系统在遇到故障时能够自动恢复，提高系统的稳定性和可靠性。
 * 资源管理：合理地管理资源分配与释放，避免因单个组件失败导致整个系统崩溃
 */
public class Supervisor extends UntypedActor {

    /**
     * 此案例展示了如何创建一个自定义的监督策略，并应用于父Actor上。
     * 当子Actor抛出任何类型的Exception时，父Actor将尝试重启它最多10次，
     * 在1分钟的时间范围内。如果超过了这个限制，则会采取其他措施（如停止该子Actor）。
     */
    private static final SupervisorStrategy strategy = new OneForOneStrategy(
        10, // 最大重试次数
        Duration.create("1 minute"), // // 时间窗口
        DeciderBuilder.match(Exception.class, e -> SupervisorStrategy.restart()).build()

            /**
             * restart：重启子 Actor，清除其状态并重新初始化。
             * stop：停止子 Actor，不再处理任何消息。
             * escalate：将异常传递给更高层次的监督者（父 Actor 的父 Actor），由更高级别的监督者来处理。
             */
//            DeciderBuilder.matchAny(e -> SupervisorStrategy.stop()).build()
//            DeciderBuilder.matchAny(e -> SupervisorStrategy.resume()).build() // resume策略：当子Actor抛出异常时，父 Actor 会简单地恢复（resume）子Actor的正常运行，而不会重启或停止它。这意味着子 Actor 的状态会被保留，只有导致异常的消息会被丢弃，后续消息将继续被处理
//            DeciderBuilder.matchAny(e -> SupervisorStrategy.escalate()).build()
    );

    @Override
    public void onReceive(Object message) throws Throwable, Throwable {
        System.out.println("receive msg="+message);
        getSender().tell("ok",getSelf());
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public void preStart() throws Exception {
        ActorRef child = getContext().actorOf(Props.create(MyChildActor.class), "myChild");
        System.out.println("preStart...");
        // 发送消息给子Actor...
        child.tell("preStart", getSelf());
    }
}