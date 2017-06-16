package spms.listeners;

// SqlSessionFactory 객체 준비
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import spms.context.ApplicationContext;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			//ApplicationContext 객체를 생성할 때 기본 생성자를 호춣하도록 코드를 변경
			applicationContext = new ApplicationContext();

			//SqlSessionFactoryBuilder 클래스의 build()를 호출해야만 SqlSessionFactory 객체를 생성할 수 있다.
			String resource = "spms/dao/mybatis-config.xml";
			//mybatis에서 제공하는 Resources 클래스를 사용하면 자바 클래스 경로에 있는 파일의 입력 스트림을 쉽게 얻을 수 있다.
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

			//SqlSessionFactory 객체를 ApplicationContext에 등록한다.
			applicationContext.addBean("sqlSessionFactory", sqlSessionFactory);

			//프로퍼티 파일(aqpplication-context.properties) 경로를 알아내기
			ServletContext sc = event.getServletContext();
			String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));

			//프로퍼티 파일의 내용에 따라 객체를 생성하도록 ApplicationContext에 지시한다.
			applicationContext.prepareObjectsByProperties(propertiesPath);

			//@Component 어노테이션이 붙은 클래스를 찾아 객체를 생성한다.
			applicationContext.prepareObjectsByAnnotation("");

			//ApplicationContext에서 관리하는 객체들을 조사하여 의존 객체를 주입한다.
			applicationContext.injectDependency();

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
}
