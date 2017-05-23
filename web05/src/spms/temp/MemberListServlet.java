package spms.temp;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MySqlMemberDao;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			MySqlMemberDao memberDao = (MySqlMemberDao)sc.getAttribute("memberDao");
			
			request.setAttribute("members", memberDao.selectList());
			request.setAttribute("viewUrl", "/member/MemberList.jsp"); //JSP URL 정보를 프런트 컨트롤러에게 알려주고자 ServletRequest 보관소에 저장
			
			/*response.setContentType("text/html; charset=UTF-8"); // 응답 데이터의 문자 집합은 프런트 컨트롤러에서 이미 설정
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp"); //화면 출력을 위해 JSP로 실행을 위임하는 것도 프런트 컨트롤러가 처리
			rd.include(request,  response);*/
			
		} catch (Exception e) {
			throw new ServletException(e); //service() 메서드는 ServletException을 던지도록 선언되어 있기 때문에 기존의 예외 객체를 그냥 던질 수 없다. 그래서 ServletException 객체를 생성한다.
			/*e.printStackTrace(); //오류가 발생했을 때 오류 처리 페이지로 실행을 위임하는 작업도 프런트 컨트롤러가 한다.
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);*/
		}
	}
}
