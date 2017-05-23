package spms.temp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// doGet일 때는 로그인 폼으로 가도록
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInForm.jsp");
		rd.forward(request, response);*/
		request.setAttribute("viewUrl", "/auth/LogInForm.jsp");
	}

	// doPost일 때는 계정 검증
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			MySqlMemberDao memberDao = (MySqlMemberDao) sc.getAttribute("memberDao");

			Member member = memberDao.exist(request.getParameter("email"), request.getParameter("password"));
			if (member != null) {
				HttpSession session = request.getSession(); // 계정 확인이 되었다면
															// HttpSession 객체를
															// 세팅한다.
				session.setAttribute("member", member); // session에 member를
														// 세팅한다.

				request.setAttribute("viewUrl", "redirect:../member/list.do");
			} else {
				// 디비에 계정이 없다면, 로그인 실패 페이지를 안내한다.
				/*RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInFail.jsp");
				rd.forward(request, response);*/
				request.setAttribute("viewUrl", "/auth/LogInFail.jsp");
			}

		} catch (Exception e) {
			throw new ServletException(e);

		}
	}
}
