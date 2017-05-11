package spms.controls;

import java.util.Map;


import spms.dao.MemberDao;

public class MemberListController implements Controller{
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//페이지 컨트롤러에서 사용할 객체를 Map에서 꺼내기
		MemberDao memberDao = (MemberDao)model.get("memberDao");
		//페이지 컨트롤러가 작업한 결과물을 Map에 담기
		model.put("members",  memberDao.selectList());
		//뷰 URL 반환
		return "/member/MemberList.jsp";
	}
}
