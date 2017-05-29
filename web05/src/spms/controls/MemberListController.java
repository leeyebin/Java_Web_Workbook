package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.dao.MySqlMemberDao;

@Component("/member/list.do")
public class MemberListController implements Controller{
	MySqlMemberDao memberDao;
	//MemberDao를 주입 받기 위한 인스턴스 변수와 setter 메서드를 추가함.
	public MemberListController setMemberDao(MySqlMemberDao memberDao){
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//외부에서 MemberDao 객체를 주입해 줄 것이기 때문에 이제 더 이상 Map 객체에서 MemberDao를 꺼낼 필요가 없다.
		//MemberDao memberDao = (MemberDao)model.get("memberDao");
		
		//페이지 컨트롤러가 작업한 결과물을 Map에 담기
		model.put("members",  memberDao.selectList());
		//뷰 URL 반환
		return "/member/MemberList.jsp";
	}
}
