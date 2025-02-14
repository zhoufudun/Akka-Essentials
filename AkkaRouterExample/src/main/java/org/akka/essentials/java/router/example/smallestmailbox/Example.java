package org.akka.essentials.java.router.example.smallestmailbox;

import org.akka.essentials.java.router.example.MsgEchoActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.SmallestMailboxRouter;

public class Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ActorSystem _system = ActorSystem.create("SmallestMailBoxRouterExample");
		ActorRef smallestMailBoxRouter = _system.actorOf(new Props(MsgEchoActor.class)
				/**
				 * 其作用是根据每个 Actor 的邮箱（mailbox）大小来动态分配消息。具体来说，它会将消息发送给当前邮箱中消息数量最少的 Actor，
				 * 从而尽量平衡各个 Actor 的负载，避免某些 Actor 过度繁忙而其他 Actor 处于空闲状态
				 */
				.withRouter(new SmallestMailboxRouter(5)),"mySmallestMailBoxRouterActor");

		for (int i = 1; i <= 10; i++) {
			//works like roundrobin but tries to rebalance the load based on
			//size of actor's mailbox
			smallestMailBoxRouter.tell(i);
		}
		_system.shutdown();

	}

}
