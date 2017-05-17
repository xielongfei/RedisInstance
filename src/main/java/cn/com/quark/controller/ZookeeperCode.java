package cn.com.quark.controller;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class ZookeeperCode implements Watcher{

	private static CountDownLatch countDownLatch = new CountDownLatch(1);
	private static AtomicInteger atomicInteger = new AtomicInteger(1);
	
	public static void main(String[] args) throws IOException {
		ZooKeeper zooKeeper = new ZooKeeper("ip:host", 5000, new ZookeeperCode());
		System.out.println(zooKeeper.getState());
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {}
		
		atomicInteger.incrementAndGet();
		
	}
	
	@Override
	public void process(WatchedEvent event) {
		if(KeeperState.SyncConnected == event.getState()){
			countDownLatch.countDown();
		}
	}
	
	private void zkClient(){
		ZkClient zkClient = new ZkClient("");
		//遍历子节点
		zkClient.deleteRecursive("");
	}

}
