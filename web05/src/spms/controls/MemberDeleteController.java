package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;

@Component("/member/delete.do")
public class MemberDeleteController implements Controller, DataBinding {
	MySqlMemberDao memberDao;
	
	public Object[] getDataBinders() {
		return new Object[]{
				"no", Integer.class
		};
	}
	
	//MemberDao를 주입 받기 위한 인스턴스 변수와 setter 메서드를 추가함.
	public MemberDeleteController setMemberDao(MySqlMemberDao memberDao){
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//MemberDao memberDao = (MemberDao)model.get("memberDao");
		//Member member = (Member)model.get("member");
		
		memberDao.delete((Integer)model.get("no"));
		
		return "redirect:list.do";
	}
	

}
