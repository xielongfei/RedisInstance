package cn.com.socket.server;

import java.util.Vector;

public class ChatManager {
	private ChatManager() {

	}

	private static final ChatManager chatManager = new ChatManager();

	public static ChatManager instance() {
		return chatManager;
	}

	//Vector 线程安全,默认增长为原来一倍,数组方式存储数据,
	Vector<ChatSocket> vector = new Vector<ChatSocket>();

	public void add(ChatSocket chatSocket) {
		vector.add(chatSocket); // 每个线程加入集合
		for(ChatSocket chatSocket2 : vector){
			System.out.println(chatSocket2.socket);
		}
	}

	public void remove(ChatSocket chatSocket) {
		vector.remove(chatSocket);
	}

	public void publish(ChatSocket chatSocket, String message) { // 客户端发送消息
		for (int i = 0; i < vector.size(); i++) {
			ChatSocket chatSocket2 = vector.get(i);
			if (!chatSocket2.equals(chatSocket)) { // 发送消息至客户端除自己外
				chatSocket2.out(message);
			}
		}
	}

}
