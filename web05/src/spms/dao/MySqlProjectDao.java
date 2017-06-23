package spms.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import spms.annotation.Component;
import spms.vo.Project;



@Component("projectDao")
public class MySqlProjectDao implements ProjectDao {
	//mybatis를 사용하면서 DataSource는 필요가 없어졌다.
	//SQL을 실행할 때 사용할 도구를 만들어주는 SqlSessionFactory를 만든다.
	SqlSessionFactory sqlSessionFactory;
	
	//SqlSession을 저장할 인스턴스 변수와 setter 메서드를 선언한다.
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}

	/*DataSource ds;

	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}*/

	public List<Project> selectList(HashMap<String, Object> paramMap) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			//sql SQl을 실행하는 도구이다.
			//SQLID는 SQL 맵퍼의 네임스페이스 이름 + SQL문 ID
			//ex) "spms.dao.ProjectDao" + "selectList"
			return sqlSession.selectList("spms.dao.ProjectDao.selectList", paramMap);
		}finally{
			//SqlSession 객체 또한 DB커넥션처럼 사용을 완료한 후에는,
			//닫아야 한다.
			sqlSession.close();
		}
		/*Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt
					.executeQuery("SELECT PNO,PNAME,STA_DATE,END_DATE,STATE" + " FROM PROJECTS" + " ORDER BY PNO DESC");

			ArrayList<Project> projects = new ArrayList<Project>();

			while (rs.next()) {
				projects.add(new Project().setNo(rs.getInt("PNO")).setTitle(rs.getString("PNAME"))
						.setStartDate(rs.getDate("STA_DATE")).setEndDate(rs.getDate("END_DATE"))
						.setState(rs.getInt("STATE")));
			}

			return projects;

		} catch (Exception e) {
			throw e;

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}
		}*/
	}

	public int insert(Project project) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			int count = sqlSession.insert("spms.dao.ProjectDao.insert", project);
			sqlSession.commit();
			return count;
		}finally{
			sqlSession.close();
		}
		/*Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("INSERT INTO PROJECTS"
					+ "(PNAME,CONTENT,STA_DATE,END_DATE,STATE,CRE_DATE,TAGS)" + " VALUES (?,?,?,?,0,NOW(),?)");
			stmt.setString(1, project.getTitle());
			stmt.setString(2, project.getContent());
			stmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
			stmt.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
			stmt.setString(5, project.getTags());

			return stmt.executeUpdate();

		} catch (Exception e) {
			throw e;

		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}
		}*/
	}

	public Project selectOne(int no) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			return sqlSession.selectOne("spms.dao.ProjectDao.selectOne", no);
		}finally{
			sqlSession.close();
		}
		/*Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT PNO,PNAME,CONTENT,STA_DATE,END_DATE,STATE,CRE_DATE,TAGS"
					+ " FROM PROJECTS WHERE PNO=" + no);
			if (rs.next()) {
				return new Project().setNo(rs.getInt("PNO")).setTitle(rs.getString("PNAME"))
						.setContent(rs.getString("CONTENT")).setStartDate(rs.getDate("STA_DATE"))
						.setEndDate(rs.getDate("END_DATE")).setState(rs.getInt("STATE"))
						.setCreatedDate(rs.getDate("CRE_DATE")).setTags(rs.getString("TAGS"));

			} else {
				throw new Exception("해당 번호의 프로젝트를 찾을 수 없습니다.");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}
		}*/
	}

	public int update(Project project) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
	    	Project original = sqlSession.selectOne(
	    			"spms.dao.ProjectDao.selectOne", project.getNo());
	    	
	    	Hashtable<String,Object> paramMap = new Hashtable<String,Object>();
	    	if (!project.getTitle().equals(original.getTitle())) {
	    		paramMap.put("title", project.getTitle());
	    	}
	    	if (!project.getContent().equals(original.getContent())) {
	    		paramMap.put("content", project.getContent());
	    	}
	    	if (project.getStartDate().compareTo(original.getStartDate()) != 0) {
	    		paramMap.put("startDate", project.getStartDate());
	    	}
	    	if (project.getEndDate().compareTo(original.getEndDate()) != 0) {
	    		paramMap.put("endDate", project.getEndDate());
	    	}
	    	if (project.getState() != original.getState()) {
	    		paramMap.put("state", project.getState());
	    	}
	    	if (!project.getTags().equals(original.getTags())) {
	    		paramMap.put("tags", project.getTags());
	    	}
	    	
	    	if (paramMap.size() > 0) {
	    		paramMap.put("no", project.getNo());
	    		int count = sqlSession.update("spms.dao.ProjectDao.update", paramMap);
	    		sqlSession.commit();
	    		return count;
	    	} else {
	    		return 0;
	    	}
			//int count = sqlSession.update("spms.dao.ProjectDao.update", project);
			//sqlSession.commit();
			//return count;
		}finally{
			sqlSession.close();
		}
		
	}

	public int delete(int no) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			int count = sqlSession.delete("spms.dao.ProjectDao.delete", no);
			sqlSession.commit();
			return count;
		}finally{
			sqlSession.close();
		}
		/*Connection connection = null;
		Statement stmt = null;

		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			return stmt.executeUpdate("DELETE FROM PROJECTS WHERE PNO=" + no);

		} catch (Exception e) {
			throw e;

		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}
		}*/
	}
}
