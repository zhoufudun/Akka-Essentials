package org.akka.essentials.java.router.example2.custom;

import org.akka.essentials.java.router.example.MsgEchoActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.DefaultResizer;
import akka.routing.RandomRouter;

/**
 * 创建一个名为CustomRouteeRouterExample的ActorSystem。
 * 定义一个DefaultResizer，设置路由实例的最小数量为2，最大数量为15。
 * 使用Props创建一个RandomRouter，并将MsgEchoActor与之关联。
 * 循环发送10条消息给RandomRouter，这些消息会被随机分发给路由中的Actor实例
 */
public class Example3 {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		ActorSystem _system = ActorSystem.create("CustomRouteeRouterExample");

		int lowerBound = 2;
		int upperBound = 15;
		DefaultResizer resizer = new DefaultResizer(lowerBound, upperBound);

		ActorRef randomRouter = _system.actorOf(new Props(MsgEchoActor.class)
				.withRouter(new RandomRouter(resizer)));

		for (int i = 1; i <= 10; i++) {
			// sends randomly to actors
			randomRouter.tell(i);
		}
		_system.shutdown();
	}

}
