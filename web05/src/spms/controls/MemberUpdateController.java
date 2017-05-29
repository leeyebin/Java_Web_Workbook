package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;
import spms.vo.Member;

@Component("/member/update.do")
public class MemberUpdateController implements Controller, DataBinding {
	MySqlMemberDao memberDao;
	
	public Object[] getDataBinders() {
		return new Object[]{
				"no", Integer.class,
				"member", spms.vo.Member.class
		};
	}

	// MemberDao를 주입 받기 위한 인스턴스 변수와 setter 메서드를 추가함.
	public MemberUpdateController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member) model.get("member");
		
		if (member.getEmail() == null) {
			model.put("member", memberDao.selectOne((Integer) model.get("no")));
			return "/member/MemberUpdateForm.jsp";
		} else {
			memberDao.update(member);

			return "redirect:list.do";
		}
	}

}
