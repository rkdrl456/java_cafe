import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBmanager {

	Connection conn = null;  	//connection
	Statement stmt = null; 		//���� �� SQL����
	
	String url 		= "jdbc:oracle:thin:@localhost:1521:XE";
	String user		= "madang";	//db id
	String password	= "madang";	//db pw
	
	public DBmanager()
	{
		try {
			//1.JDBC ����̹��� �ε��Ѵ�
			//System.out.println("db start. . . ");
			Class.forName("oracle.jdbc.driver.OracleDriver"); 	//db url read
			
			//2. Connection ����
			//System.out.println("connection. . . ");
			conn = DriverManager.getConnection(url,user,password); //db connect 
			
			//System.out.println("���� ���� �Ϸ�. . . ");
			
			stmt = conn.createStatement();
			//System.out.println("stmt ���� �Ϸ�. . . ");
			
		} catch (ClassNotFoundException e) {
			System.out.println("����̹��� �ε����� �ʾҽ��ϴ�");
		} catch (SQLException e) {
			System.out.println("������ ������ �� �����ϴ�");
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
	}//DBmanager ������ 
}


