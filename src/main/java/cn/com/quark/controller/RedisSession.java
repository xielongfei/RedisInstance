package cn.com.quark.controller;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;

import cn.com.quark.domain.UserInfo;
import redis.clients.jedis.Jedis;

/**
 * �����û���״̬��Ϣ
 * @author LongfeiXie
 *
 */
public class RedisSession {

	
	/*public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		Set<String> set = jedis.keys("*");
		for(String s : set){
			System.out.println(s);
		}
		jedis.close();
	}*/
	
	//���cookie
	public void saveCoolie(HttpServletResponse response){
		String userKey = UUID.randomUUID().toString();  
		Cookie cookie = new Cookie("UserToken", userKey);   
		response.addCookie(cookie);  
	}
	
	//��ȡcookie
	public String takeCookies(HttpServletRequest request){
		String userKey = null;  
		Cookie[] cookies = request.getCookies();  
		for (Cookie cookie : cookies) {  
		    if("UserToken".equals(cookie.getName())) {  
		        userKey = cookie.getValue();  
		        break;  
		    }  
		}
		return userKey;
	}
	
	//��redis
	public void putRedis(){
		//ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml"); 
		
		String userName = "xiaoxie";
	    String pwd = "123456";
	    
	    //1.�û���¼�ɹ���Ϣ
	    UserInfo userInfo = new UserInfo.Builder(userName)
	    		.token("W5naGFvLm5ldCIsImV4cCI6IjE0Mzg5NTU0NDUiLCJuYW1lIjoid2FuZ2")
	    		.system("Chrome")
	    		.time(new Date())
	    		.bulid();
	    
	    //2.����cookie
	    String userKey = UUID.randomUUID().toString();  
		//Cookie cookie = new Cookie("UserToken", userKey);  //cookie���ظ��ͻ���,�´����󴫹���
		
		//3.�û���Ϣдredis
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.set(userKey, JSONObject.toJSONString(userInfo));
		jedis.expire(userKey, 30);  //�����û�key����ʱ��
		System.out.println("д��redis�ɹ�");
		
		jedis.close();
	}
	
	public static void main(String[] args) {
		new RedisSession().putRedis();
	}
}
