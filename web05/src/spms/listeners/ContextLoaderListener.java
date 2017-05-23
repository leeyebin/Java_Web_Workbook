package spms.listeners;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import spms.controls.LogInController;
import spms.controls.LogOutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.dao.MySqlMemberDao;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	// BasicDataSource ds;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext sc = event.getServletContext();

			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/studydb");



			MySqlMemberDao memberDao = new MySqlMemberDao();
			// DataSource를 주입
			memberDao.setDataSource(ds);

			//sc.setAttribute("memberDao", memberDao);
			
			//페이지 컨트롤러 객체를 생성하고 나서 MemberDao가 필요한 객체에 대해서는 setter메서드를 호출하여 주입해 준다.
			//ServletContext에 저장(키는 서블릿 요청 url)
			sc.setAttribute("/auth/login.do", new LogInController().setMemberDao(memberDao));
			sc.setAttribute("/auth/logout.do", new LogOutController());
			sc.setAttribute("/member/list.do", new MemberListController().setMemberDao(memberDao));
			sc.setAttribute("/member/add.do", new MemberAddController().setMemberDao(memberDao));
			sc.setAttribute("/member/update.do", new MemberUpdateController().setMemberDao(memberDao));
			sc.setAttribute("/member/delete.do", new MemberDeleteController().setMemberDao(memberDao));

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// context.xml에 이미 closeMethod 속성이 있어서 톰캣 서버가 종료되면 close를 알아서 해준다.
		/*
		 * try { if (ds != null) //커넥션 객체 닫는다. ds.close(); } catch (SQLException
		 * e) { }
		 */
	}
}
