package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberAddController implements Controller {
	MemberDao memberDao;

	// MemberDao를 주입 받기 위한 인스턴스 변수와 setter 메서드를 추가함.
	public MemberAddController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("member") == null) {
			return "/member/MemberAdd.jsp";
		} else {
			//MemberDao memberDao = (MemberDao) model.get("memberDao");

			Member member = (Member) model.get("member");

			memberDao.insert(member);

			return "redirect:list.do";
		}
	}

}
