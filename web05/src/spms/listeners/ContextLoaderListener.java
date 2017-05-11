package spms.listeners;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import spms.dao.MemberDao;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	//BasicDataSource ds;
	

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext sc = event.getServletContext();
			
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource)initialContext.lookup("java:comp/env/jdbc/studydb");

//			ds = new BasicDataSource();
//			ds.setDriverClassName(sc.getInitParameter("driver"));
//			ds.setUrl(sc.getInitParameter("url"));
//			ds.setUsername(sc.getInitParameter("username"));
//			ds.setPassword(sc.getInitParameter("password"));

			MemberDao memberDao = new MemberDao();
			//DataSource를 주입
			memberDao.setDataSource(ds);

			sc.setAttribute("memberDao", memberDao);

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//context.xml에 이미 closeMethod 속성이 있어서 톰캣 서버가 종료되면 close를 알아서 해준다.
		/*try {
			if (ds != null)
				//커넥션 객체 닫는다.
				ds.close();
		} catch (SQLException e) {
		}*/
	}
}
