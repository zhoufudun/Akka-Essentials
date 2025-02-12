package org.akka.essentials.java.dispatcher.example.CallingThreadDispatcher;

import org.akka.essentials.java.dispatcher.example.MsgEchoActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinRouter;

import com.typesafe.config.ConfigFactory;

public class Example {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ActorSystem _system = ActorSystem.create("callingThread-dispatcher",
				ConfigFactory.load().getConfig("MyDispatcherExample"));

		/**
		 * RoundRobinRouter 被配置为包含 2 个路由节点，并且这些路由节点的消息处理将由
		 * CallingThreadDispatcher来调度。这意味着消息会轮流分发给
		 * 2个Actor实例，并且这些实例的消息处理将在调用线程中执行
		 */
		ActorRef actor = _system.actorOf(new Props(MsgEchoActor.class)
				.withDispatcher("CallingThreadDispatcher").withRouter(
						new RoundRobinRouter(2)));

		for (int i = 0; i < 50; i++) {
			actor.tell(i);
		}

		_system.shutdown();

	}
}
