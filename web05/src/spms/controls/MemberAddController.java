package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;
import spms.vo.Member;
@Component("/member/add.do")
public class MemberAddController implements Controller, DataBinding {
	MySqlMemberDao memberDao;

	//클라이언트가 보낸 매개변수 값을 Member 인스턴스에 담아서 "member"라는 이름으로 Map객체에 저장해라라는 뜻.
	//프런트 컨트롤러는 Object 배열에 지정된 대로 Member 인스턴스를 준비하여 Map 객체에 저장하고, execute()를 호출할 때 매개변수로 이 Map 객체를 넘긴다.
	public Object[] getDataBinders() {
		return new Object[]{
				"member", spms.vo.Member.class
		};
	}

	public MemberAddController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//getDataBinders()에서 지정한 대로 프런트 컨트롤러가 VO객체를 무조건 생성할 것이기 때문에 Member가 있는지 여부로 판단해서는 안 된다. 그래서 email이 null인지 체크한다.
		Member member = (Member)model.get("member");
		if (member.getEmail() == null) {
			return "/member/MemberAdd.jsp";
		} else {
			memberDao.insert(member);
			return "redirect:list.do";
		}
	}
}
