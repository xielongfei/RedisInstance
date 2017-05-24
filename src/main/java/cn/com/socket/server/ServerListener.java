package cn.com.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerListener extends Thread {
	
	@Override
	public void run(){
		int port = 8083;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while(true){
				Socket socket = serverSocket.accept(); //阻塞监听
				//JOptionPane.showMessageDialog(null, "有客户端连接本机"+port+"端口");
				System.out.println("有客户端连接本机"+port+"端口");
				System.out.println(serverSocket);
				ChatSocket cs= new ChatSocket(socket);
			    cs.start();
			    ChatManager.instance().add(cs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
