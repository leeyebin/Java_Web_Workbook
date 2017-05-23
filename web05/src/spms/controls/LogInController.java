package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.dao.MemberDao;
import spms.vo.Member;

public class LogInController implements Controller {
	MemberDao memberDao;

	// MemberDao를 주입 받기 위한 인스턴스 변수와 setter 메서드를 추가함.
	public LogInController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("loginInfo") == null) { // 입력폼을 요청할 때
			return "/auth/LogInForm.jsp";

		} else { // 회원 등록을 요청할 때
			//MemberDao memberDao = (MemberDao) model.get("memberDao");
			Member loginInfo = (Member) model.get("loginInfo");

			Member member = memberDao.exist(loginInfo.getEmail(), loginInfo.getPassword());

			if (member != null) {
				// HttpSession session = null;
				HttpSession session = (HttpSession) model.get("session");
				session.setAttribute("member", member);
				return "redirect:../member/list.do";
			} else {
				return "/auth/LogInFail.jsp";
			}
		}
	}
}
