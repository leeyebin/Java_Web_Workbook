package spms.servlets;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class AppInitServlet extends HttpServlet {

	//클라이언트에서 호출할 서블릿이 아니므로 init()만 오버라이딩
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("AppInitServlet 준비…");
		super.init(config); //슈퍼클래스 HttpServlet으로부터 상속받은 init()의 기능을 그대로 수행하겠다.
		try {
			ServletContext sc = this.getServletContext(); //ServletContext 객체
			Class.forName(sc.getInitParameter("driver"));
			Connection conn = DriverManager.getConnection(
						sc.getInitParameter("url"),
						sc.getInitParameter("username"),
						sc.getInitParameter("password"));
			
			sc.setAttribute("conn", conn);//ServletContext객체에 세팅
		} catch(Throwable e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	public void destroy() {
		System.out.println("AppInitServlet 마무리...");
		super.destroy();
		Connection conn = 
				(Connection)this.getServletContext().getAttribute("conn"); 
		try {
			if (conn != null && conn.isClosed() == false) {
				conn.close();
			}
		} catch (Exception e) {}
		
	}
}
