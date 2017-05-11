package spms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBConnectionPool {
  String url;
  String username;
  String password;
  
  //Connection 객체를 보관할 ArrayList를 준비한다.
  ArrayList<Connection> connList = new ArrayList<Connection>(); 
  
  //생성자에서는 DB커넥션 생성에 필요한 값을 매개변수로 받는다.
  public DBConnectionPool(String driver, String url, 
      String username, String password) throws Exception {
    this.url = url;
    this.username = username;
    this.password = password;
    
    Class.forName(driver);
  }
  
  //DB 커넥션을 달라고 요청받으면, ArrayList에 들어 있는 것을 꺼내 준다.(DB 커넥션 객체도 일정 시간 후면 연결이 끊어지기 때문에 유효성체크)
  public Connection getConnection() throws Exception {
    if (connList.size() > 0) {
      Connection conn = connList.remove(0);
      if (conn.isValid(10)) {
        return conn;
      }
    }
    //ArrayList에 보관된 객체가 없다면, DriverManager를 통해 새로 만들어 반환한다.
    return DriverManager.getConnection(url, username, password);
  }
  
  //커넥션 객체를 쓰고 난 다음에는 이 메서드를 호출하여 커넥션 풀에 반환한다.
  public void returnConnection(Connection conn) throws Exception {
    connList.add(conn);
  }
  
  //이 메서드를 호출하여 데이터베이스와 연결된 것을 모두 끊어야 한다.
  public void closeAll() {
    for(Connection conn : connList) {
      try{conn.close();} catch (Exception e) {}
    }
  }
}
