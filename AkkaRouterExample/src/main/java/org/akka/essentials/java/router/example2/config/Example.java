package org.akka.essentials.java.router.example2.config;

import org.akka.essentials.java.router.example.MsgEchoActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;

import com.typesafe.config.ConfigFactory;

/**
 * 这段代码展示了如何使用Akka框架中的FromConfig路由器来创建一个Actor系统，并根据配置文件中的设置初始化一个随机路由的Actor。具体步骤如下：
 * 加载配置：从application.conf中加载配置，创建名为RandomRouterExample的Actor系统。
 * 初始化路由器：根据配置文件中的/myRandomRouterActor设置，创建一个包含5个实例的随机路由器（random），并将其命名为myRandomRouterActor。
 * 发送消息：向路由器发送10条消息，这些消息会被随机分发给路由器下的各个Actor实例。
 * 关闭系统：所有消息发送完毕后，关闭Actor系统。
 */
public class Example {
	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		ActorSystem _system = ActorSystem.create("RandomRouterExample",
				ConfigFactory.load().getConfig("MyRouterExample"));
		ActorRef randomRouter = _system.actorOf(
				new Props(MsgEchoActor.class).withRouter(new FromConfig()),
				"myRandomRouterActor");

		for (int i = 1; i <= 10; i++) {
			// sends randomly to actors
			randomRouter.tell(i);
		}
		_system.shutdown();
	}
}
