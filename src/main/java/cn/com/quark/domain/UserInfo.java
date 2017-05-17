package cn.com.quark.domain;

import java.util.Date;

/**
 * 用户登录实体信息
 * @author LongfeiXie
 *
 */
public class UserInfo {

	//用户名
	private String userName;
	
	//token(时间,应用名,用户id 生产唯一token)
	private String token;
	
	//登录方式
	private String system;
	
	//请求时间
	private Date time;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public static class Builder {
		private String userName;
		private String token;
		private String system;
		private Date time;
		
		public Builder(String userName){
			this.userName = userName;
		}
		public Builder token(String token){
			this.token = token;
			return this;
		}
		public Builder system(String system){
			this.system = system;
			return this;
		}
		public Builder time(Date time){
			this.time = time;
			return this;
		}
		
		public UserInfo bulid(){
			return new UserInfo(this);
		}
	}
	
	public UserInfo(){
		
	}
	
	public UserInfo(Builder b){
		this.userName = b.userName;
		this.token = b.token;
		this.system = b.token;
		this.time = b.time;
	}

}
