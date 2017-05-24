package cn.com.socket.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ChatSocket extends Thread {
	// 创建一个Socket对象来接收SocketListener传来的Socket对象
	Socket socket;

	public ChatSocket(Socket socket) {
		this.socket = socket;
	}

	public void out(String out) {
		try {
			socket.getOutputStream().write((out + "\n").getBytes("UTF-8")); // 接受服务端数据
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("断开了一个客户端链接");
			ChatManager.instance().remove(this);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		out("已经连接服务器");

		try {
			/**BufferedWriter 字符输出流缓冲区对象
			  BufferedReader 缓存字符输入流 
			  buffered表示缓冲 reader表示字符输入流 writer表示字符输出流
			  InputStreamReader 是字节流通向字符流的桥梁,将字节流转换为字符流.
			  InputStream每次读取一个byte,InputStreamReader读取一个字符,BufferedReader读取一行字符
			  */
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				ChatManager.instance().publish(this, line);
			}
			bufferedReader.close();
			System.out.println("断开了一个客户端链接");
			ChatManager.instance().remove(this);
		} catch (IOException e) {
			System.out.println("断开了一个客户端链接");
			ChatManager.instance().remove(this);
			e.printStackTrace();
		}
	}
}
