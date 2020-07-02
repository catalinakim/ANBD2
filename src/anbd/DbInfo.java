package anbd;
import java.sql.*;

public class DbInfo {

//	String url 			     = "jdbc:mysql://127.0.0.1:3306/anbd?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC&autoReconnection=true";
	String url 			     = "jdbc:mysql://127.0.0.1/anbd?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
//	String 			  url 	 = "jdbc:mysql://192.168.0.77/anbd?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
	String 			  driver = "com.mysql.cj.jdbc.Driver";
	String 			  id  	 = "kanu";
	String 			  pw  	 = "1234";
	Statement 		  state  = null; 
	ResultSet 		  rs	 = null; 
	Connection 		  con 	 = null; 
	PreparedStatement pstate = null;


	public Connection getConnection()
	{
		try
		{
		Class.forName(driver);
		con = DriverManager.getConnection(url, id, pw);
		System.out.println("===DB 연결===");
		}
		catch(SQLException se)
		{   
			se.printStackTrace();
			System.out.println("DB 엑세스 에러: "+se.getMessage() );
		}
		catch(Exception e)
		{
			System.out.println("getConnection() 에러: "+e.getMessage() );
		}
		return con;
	}//getConnection METHOD
	
 	public void prepareStatement(String sql) 
 	{ 
		try 
		{
			pstate = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} 
		catch (SQLException e) 
		{
			System.out.println("prepareStatement() 에러: "+e.getMessage() );
		}
	}//prepareStatement METHOD
 	
 	public void createStatement() 
 	{ 
		try 
		{
			state = con.createStatement();
		} catch (SQLException e) 
		{
			System.out.println("createStatement() 에러: "+e.getMessage() );
		}
	}//createStatement METHOD
 	
	//Insert, Update, Delete 쿼리
	public void executeUpdate() 
	{ 
		try {
			pstate.executeUpdate();
		} catch (SQLException e) {
			System.out.println("executeUpdate() 에러: "+e.getMessage() );
		}
	}//executeUpdate METHOD
	
	//Select 쿼리
	public void executeQuery() {
		try {
			rs = pstate.executeQuery(); //쿼리를 실행하고 결과를 ResultSet 객체에 담는다.
		} catch (SQLException e) {
			System.out.println("executeQuery() 에러: "+e.getMessage() );
		}
	}//executeQuery METHOD
	
	//닫기
	public void stateClose() {
		try {
			if (state != null) {
				state.close();
			}
		} catch (SQLException e) {
			System.out.println("stateClose() 에러: "+e.getMessage() );
		}
	}//stateClose METHOD
	
	public void pstateClose() {
		try 
		{
			if(pstate!=null||!pstate.isClosed())
			{
				pstate.close();
			}
		} catch (SQLException e) {
			System.out.println("pstateClose() 에러: "+e.getMessage() );
		}
	}//pstateClose METHOD
	
	public void conClose() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println("conClose() 에러: "+e.getMessage() );
		}
	}//conClose METHOD
	
	public void rsClose() 
	{
		try 
		{
			if(rs!=null||!rs.isClosed())
			{
				rs.close();
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("rsClose() 에러: "+e.getMessage() );
		}
		
	}//rsClose METHOD
	
}//DbInfo CLASS
