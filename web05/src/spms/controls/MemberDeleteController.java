package spms.controls;

import java.util.Map;

import spms.dao.MySqlMemberDao;

public class MemberDeleteController implements Controller {
	MySqlMemberDao memberDao;
	//MemberDao를 주입 받기 위한 인스턴스 변수와 setter 메서드를 추가함.
	public MemberDeleteController setMemberDao(MySqlMemberDao memberDao){
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//MemberDao memberDao = (MemberDao)model.get("memberDao");
		
		memberDao.delete((Integer)model.get("no"));
		
		return "redirect:list.do";
	}
	

}
