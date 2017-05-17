package cn.com.quark.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.com.quark.domain.UserInfo;
import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/login.do")
public class LoginController {

	@RequestMapping
	@ResponseBody
	public String execute(HttpServletRequest request, HttpServletResponse response){
		String userName = request.getParameter("userName");  
        String password = request.getParameter("pwd");   
        
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String token = getCookie(request);  //拦截器判断用户状态(token)
        if(token != null && token != ""){
        	String userinfo = jedis.get(token);
        	jedis.close();
        	UserInfo user = JSONObject.parseObject(userinfo, UserInfo.class);
        	if(user != null){
        		System.out.println("用户名:"+ user.getUserName());
        		return "登录成功";
        	}
        	
        	return "登录失败";
        }
        
        //1.用户登录成功信息
	    UserInfo userInfo = new UserInfo.Builder(userName)
	    		.token("W5naGFvLm5ldCIsImV4cCI6IjE0Mzg5NTU0NDUiLCJuYW1lIjoid2FuZ2")
	    		.system("Chrome")
	    		.time(new Date())
	    		.bulid();
	    
	    //2.生成cookie
	    String userKey = UUID.randomUUID().toString();  
		Cookie cookie = new Cookie("UserToken", userKey);  //cookie返回给客户端,下次请求传过来
        response.addCookie(cookie);
		//3.用户信息写redis
		//Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.set(userKey, JSONObject.toJSONString(userInfo));
		jedis.expire(userKey, 30);  //设置用户key过期时间
		System.out.println("写入redis成功");
		
		jedis.close();
		
        return "登录成功";
	}
	
	//
	public String getCookie(HttpServletRequest request){
		//验证cookie判断用户第一次登录
        Cookie[] cookies = request.getCookies();
		String token = "";
		if(cookies!=null){
			for(int i=0;i<cookies.length;i++){
				if(cookies[i].getName().equals("UserToken")){
					token = cookies[i].getValue();
					break;
				} 
			}
		}
		return token;
	}
	
}
