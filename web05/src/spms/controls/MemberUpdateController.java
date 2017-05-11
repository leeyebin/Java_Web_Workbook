package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberUpdateController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		MemberDao memberDao = (MemberDao)model.get("memberDao");
		
		if(model.get("member")==null){
			model.put("member",  memberDao.selectOne(
					(Integer)model.get("no")));
			
			return "/member/MemberUpdateForm.jsp";
		}else{
			
			Member member = (Member)model.get("member");
			
			memberDao.insert(member);
			
			return "redirect:list.do";
		}
	}

}
