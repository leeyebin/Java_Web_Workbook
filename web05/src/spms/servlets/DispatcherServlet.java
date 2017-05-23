package spms.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.controls.Controller;
import spms.controls.LogInController;
import spms.controls.LogOutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("*.do") //클라이언트 요청 중에서 서블릿 경로 이름이 .do로 끝나는 경우는 DispatcherServlet이 처리하겠다는 의미
public class DispatcherServlet extends HttpServlet {
	@Override
	protected void service(// service를 오버라이딩 한 이유는 GET, POST뿐만 아니라 다양한 요청 방식에도
							// 대응하기 위해
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath(); // 서블릿의 경로를 알아내기 위해 사용
		try {
			//Map객체를 준비한다.
			ServletContext sc = this.getServletContext();
			HashMap<String, Object> model = new HashMap<String, Object>();
			//model.put("memberDao", sc.getAttribute("memberDao"));
			model.put("session", request.getSession());
			
			//페이지 컨트롤러는 ServletContext 보관소에 저장되어 있다. 그래서 이 보관소에서 페이지 컨트롤러를 꺼낼 때 서블릿 url을 사용한다.
			Controller pageController = (Controller)sc.getAttribute(servletPath);
			
			//Controller pageController = null;

			if ("/member/add.do".equals(servletPath)) {
				//pageController = new MemberAddController();
				if(request.getParameter("email")!=null){
					model.put("member", new Member()
							.setEmail(request.getParameter("email"))
							.setPassword(request.getParameter("password"))
							.setName(request.getParameter("name")));
				}
			} else if ("/member/update.do".equals(servletPath)) {
				//pageController = new MemberUpdateController();
		        if (request.getParameter("email") != null) {
		          model.put("member", new Member()
		            .setNo(Integer.parseInt(request.getParameter("no")))
		            .setEmail(request.getParameter("email"))
		            .setName(request.getParameter("name")));
		        }else {
		              model.put("no", new Integer(request.getParameter("no")));
		        }
			} else if ("/member/delete.do".equals(servletPath)) {
				//pageController = new MemberDeleteController();
				model.put("no", new Integer(request.getParameter("no")));
			} else if ("/auth/login.do".equals(servletPath)) {
				//pageController = new LogInController();
		        if (request.getParameter("email") != null) {
		        	model.put("loginInfo", new Member()
		                    .setEmail(request.getParameter("email"))
		                    .setPassword(request.getParameter("password")));
		        }
			} else if ("/auth/logout.do".equals(servletPath)) {
				//pageController = new LogOutController();
			}

			String viewUrl = pageController.execute(model);
			
			//Map 객체에 저장된 값을 ServletRequest에 복사
			for(String key : model.keySet()){
				request.setAttribute(key,  model.get(key));
			}
			
			if (viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			} else {
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}
