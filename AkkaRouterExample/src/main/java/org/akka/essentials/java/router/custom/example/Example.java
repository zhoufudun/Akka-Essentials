package org.akka.essentials.java.router.custom.example;

import org.akka.essentials.java.router.example.MsgEchoActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Example {

	/**
	 * 这段代码是 Example 类的 main 方法，用于演示自定义路由器 BurstyMessageRouter 的使用。具体功能如下：
	 * 创建一个名为 CustomRouterExample 的 Actor 系统。
	 * 使用 BurstyMessageRouter 创建一个路由器 Actor，该路由器管理 5 个 MsgEchoActor 实例，并以每批 2 条消息的突发形式发送消息。
	 * 发送 13 条消息给路由器 Actor。
	 * 关闭 Actor 系统。
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ActorSystem _system = ActorSystem.create("CustomRouterExample");
		ActorRef burstyMessageRouter = _system.actorOf(new Props(
				MsgEchoActor.class).withRouter(new BurstyMessageRouter(5,2)));

		for (int i = 1; i <= 13; i++) {
			//sends series of messages in a round robin way to all the actors
			burstyMessageRouter.tell(i);
		}
		_system.shutdown();

	}

}
