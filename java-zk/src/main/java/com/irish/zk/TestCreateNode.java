
package com.irish.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * 测试创建zookeeper节点
 *
 */
public class TestCreateNode {

	// 连接地址
	private static final String ADDRES = "127.0.0.1:2181";
	// 连接地址
	private static final int SESSIN_TIME_OUT = 2000;
	private static final CountDownLatch countDownLatch = new CountDownLatch(1);

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ZooKeeper zooKeeper = new ZooKeeper(ADDRES, SESSIN_TIME_OUT, new Watcher() {

			public void process(WatchedEvent event) {
				// 获取事件状态
				KeeperState keeperState = event.getState();
				// 获取事件类型
				EventType eventType = event.getType();

				if (KeeperState.SyncConnected == keeperState) {
					if (EventType.None == eventType) {
						countDownLatch.countDown();
						System.out.println("开启连接............");
					}
				}
			}
		});
		countDownLatch.await();
		// 创建节点
		String result = zooKeeper.create("/test", "irish".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		System.out.println("result:" + result);
		Thread.sleep(1000 * 10);
		zooKeeper.close();
	}

}
