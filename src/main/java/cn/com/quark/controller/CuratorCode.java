package cn.com.quark.controller;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.format.number.CurrencyFormatter;

/**
 * Zookeeper客户端框架
 * @author LongfeiXie
 *
 */
public class CuratorCode {
	
	public static void main(String[] args) throws Exception {
		//基数乘以2的移位幂次方
		//int s = 2 << 4;
		RetryPolicy policy = new ExponentialBackoffRetry(1000, 3); 
		CuratorFramework client = CuratorFrameworkFactory.
				newClient("host:ip", 5000, 3000, policy);
		
		//client.create().forPath(path, data)
		//client.delete().guaranteed().forPath("");  //删除失败重试
		
		client.start();
	}
}
