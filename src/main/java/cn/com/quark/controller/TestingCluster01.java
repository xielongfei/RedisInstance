package cn.com.quark.controller;

import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.TestingZooKeeperServer;

/**
 * Zookeeper集群工具类
 * @author LongfeiXie
 *
 */
public class TestingCluster01 {

	public static void main(String[] args) throws Exception{
		TestingCluster cluster = new TestingCluster(3);
		cluster.start();
		Thread.sleep(2000);
		
		TestingZooKeeperServer server = null;
		for(TestingZooKeeperServer zs : cluster.getServers()){
			System.out.print(zs.getInstanceSpec().getServerId()+"-");
			System.out.print(zs.getQuorumPeer().getServerState()+"-");
			System.out.println(zs.getInstanceSpec().getDataDirectory().getAbsolutePath());
			if(zs.getQuorumPeer().getServerState().equals("leading")){
				server = zs;
			}
		}
		server.kill();
		for(TestingZooKeeperServer zs : cluster.getServers()){
			System.out.print(zs.getInstanceSpec().getServerId()+"-");
			System.out.print(zs.getQuorumPeer().getServerState()+"-");
			System.out.println(zs.getInstanceSpec().getDataDirectory().getAbsolutePath());
			if(zs.getQuorumPeer().getServerState().equals("leading")){
				server = zs;
			}
		}
		//cluster.stop();
		cluster.close();
	}
}
