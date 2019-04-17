import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBmanager {

	Connection conn = null;  	//connection
	Statement stmt = null; 		//연결 후 SQL질의
	
	String url 		= "jdbc:oracle:thin:@localhost:1521:XE";
	String user		= "madang";	//db id
	String password	= "madang";	//db pw
	
	public DBmanager()
	{
		try {
			//1.JDBC 드라이버를 로딩한다
			//System.out.println("db start. . . ");
			Class.forName("oracle.jdbc.driver.OracleDriver"); 	//db url read
			
			//2. Connection 연결
			//System.out.println("connection. . . ");
			conn = DriverManager.getConnection(url,user,password); //db connect 
			
			//System.out.println("정상 접속 완료. . . ");
			
			stmt = conn.createStatement();
			//System.out.println("stmt 접속 완료. . . ");
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버가 로딩되지 않았습니다");
		} catch (SQLException e) {
			System.out.println("서버에 연결할 수 없습니다");
			e.printStackTrace();
		}  	/*finally {
			if(stmt!=null) 
			{
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} //
			}//if
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} //
			}//if  
		}//finally*/
	}//DBmanager 생성자 
}


