package spms.controls;

import java.util.HashMap;
import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlProjectDao;

@Component("/project/list.do")
public class ProjectListController implements Controller, DataBinding{
	MySqlProjectDao projectDao;
	
	public ProjectListController setProjectDao(MySqlProjectDao projectDao){
		this.projectDao = projectDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception{
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderCond", model.get("orderCond"));
		
		model.put("projects",  projectDao.selectList(paramMap));
		return "/project/ProjectList.jsp";
	}
	//@Override
	//public String execute(Map<String, Object> model) throws Exception {
	//	model.put("projects",  projectDao.selectList());
	//	return "/project/ProjectList.jsp";
	//}
	
	//클라이언트가 보낸 orderCond값을 받기위한 것.
	@Override
	public Object[] getDataBinders(){
		return new Object[]{
				"orderCond", String.class
		};
	}

}
