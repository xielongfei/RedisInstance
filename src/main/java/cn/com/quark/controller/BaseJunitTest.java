package cn.com.quark.controller;

import javax.servlet.http.Cookie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:springMVC.xml"})
public class BaseJunitTest {
	
    @Autowired
	private LoginController loginController;
    
	@Test
	public void test() throws Exception{
		MockHttpServletRequest request = new MockHttpServletRequest();  
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setCharacterEncoding("UTF-8");
        request.setServletPath("/login.do");  
        request.addParameter("userName", "xiaoxie");  
        request.addParameter("password", "123456");  
        request.setMethod("post");  
       
        String value;
        //��һ�ε�¼(��cookie)
        value = loginController.execute(request, response);
        System.out.println("�û���һ�ε�¼; value="+value);
        
        value = response.getCookie("UserToken").getValue();
        System.out.println("�û���¼�ɹ�����cookieֵ; value="+value);
        Thread.sleep(30 * 1000);
        //�ڶ��ε�¼(��cookie)
        request.setCookies(new Cookie("UserToken", value));
        value = loginController.execute(request, response);
        System.out.println("�û��ڶ��ε�¼; value="+value);
	}
}
