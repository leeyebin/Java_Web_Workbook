package spms.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import spms.context.ApplicationContext;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	static ApplicationContext applicationContext;
	
	//ContextLoaderListener에서 만든 ApplicationContext 객체를 얻을 때 사용한다.
	public static ApplicationContext getApplicationContext() {
	    return applicationContext;
	  }
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext sc = event.getServletContext();

			//프로퍼티 파일의 이름과 경로 정보도 web.xml 파일로부터 읽어 오게 처리되었다.
			//ServletContext의 getInitParameter()를 호출하여 web.xml에 설정된 매개변수 정보를 가져온다.
			String propertiesPath = sc.getRealPath(
			          sc.getInitParameter("contextConfigLocation"));
			
			//Application 객체는 프런트 컨트롤러에서 사용할 수 있게 ContextLoaderListener의 클래스 변수 'applicationContext'에 저장한다.
			applicationContext = new ApplicationContext(propertiesPath);
			
			//InitialContext initialContext = new InitialContext();
			//DataSource ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/studydb");

			//MySqlMemberDao memberDao = new MySqlMemberDao();
			//memberDao.setDataSource(ds);
			
			//sc.setAttribute("/auth/login.do", new LogInController().setMemberDao(memberDao));
			//sc.setAttribute("/auth/logout.do", new LogOutController());
			//sc.setAttribute("/member/list.do", new MemberListController().setMemberDao(memberDao));
			//sc.setAttribute("/member/add.do", new MemberAddController().setMemberDao(memberDao));
			//sc.setAttribute("/member/update.do", new MemberUpdateController().setMemberDao(memberDao));
			//sc.setAttribute("/member/delete.do", new MemberDeleteController().setMemberDao(memberDao));

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// context.xml에 이미 closeMethod 속성이 있어서 톰캣 서버가 종료되면 close를 알아서 해준다.
	}
}
