package org.akka.essentials.java.router.example.scattergatherfirstcomplete;

import org.akka.essentials.java.router.example.RandomTimeActor;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.ScatterGatherFirstCompletedRouter;
import akka.util.Timeout;

/**
 * 使用Akka框架中的ScatterGatherFirstCompletedRouter路由器来创建一个Actor系统，
 * 并发送消息给多个Actor，最终获取第一个完成的响应
 *
 * ScatterGatherFirstCompletedRouter 是 Akka 框架中的一个路由器，其主要作用是将消息分发给多个路由节点（Actor），并等待第一个完成的响应。具体来说：
 * 分散消息：将消息发送给多个目标Actor。
 * 聚集响应：等待所有目标Actor的响应，但只返回第一个完成的响应。
 * 超时控制：设置一个超时时间，如果在规定时间内没有收到任何响应，则抛出异常或返回默认值。
 * 这种路由器适用于需要快速获取首个可用结果的场景，例如查询多个数据源并取最快返回的结果
 *
 */
public class Example {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ActorSystem _system = ActorSystem
				.create("ScatterGatherFirstCompletedRouterExample");
		ActorRef scatterGatherFirstCompletedRouter = _system.actorOf(new Props(
				RandomTimeActor.class)
				.withRouter(new ScatterGatherFirstCompletedRouter(5, Duration
						.create(2, "seconds"))),
				"myScatterGatherFirstCompletedRouterActor");

		Timeout timeout = new Timeout(Duration.create(10, "seconds"));
		Future<Object> futureResult = akka.pattern.Patterns.ask(
				scatterGatherFirstCompletedRouter, "message", timeout);
		String result = (String) Await.result(futureResult, timeout.duration());
		System.out.println(result);

		_system.shutdown();

	}

}
