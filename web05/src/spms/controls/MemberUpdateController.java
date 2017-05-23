package spms.controls;

import java.util.Map;

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

public class MemberUpdateController implements Controller {
	MySqlMemberDao memberDao;

	// MemberDao를 주입 받기 위한 인스턴스 변수와 setter 메서드를 추가함.
	public MemberUpdateController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//MemberDao memberDao = (MemberDao) model.get("memberDao");

		if (model.get("member") == null) {
			model.put("member", memberDao.selectOne((Integer) model.get("no")));

			return "/member/MemberUpdateForm.jsp";
		} else {

			Member member = (Member) model.get("member");

			memberDao.update(member);

			return "redirect:list.do";
		}
	}

}
