package anbd;
import java.sql.Connection;    
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DbInfo {

//	String url 			     = "jdbc:mysql://127.0.0.1:3306/anbd?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC&autoReconnection=true";
//	String url 			     = "jdbc:mysql://127.0.0.1/anbd?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
	String 			  url 	 = "jdbc:mysql://192.168.0.77/anbd?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
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
		System.out.println("===DB ����===");
		}
		catch(SQLException se)
		{   
			se.printStackTrace();
			System.out.println("DB ������ ����: "+se.getMessage() );
		}
		catch(Exception e)
		{
			System.out.println("getConnection() ����: "+e.getMessage() );
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
			System.out.println("prepareStatement() ����: "+e.getMessage() );
		}
	}//prepareStatement METHOD
 	
 	public void createStatement() 
 	{ 
		try 
		{
			state = con.createStatement();
		} catch (SQLException e) 
		{
			System.out.println("createStatement() ����: "+e.getMessage() );
		}
	}//createStatement METHOD
 	
	//Insert, Update, Delete ����
	public void executeUpdate() 
	{ 
		try {
			pstate.executeUpdate();
		} catch (SQLException e) {
			System.out.println("executeUpdate() ����: "+e.getMessage() );
		}
	}//executeUpdate METHOD
	
	//Select ����
	public void executeQuery() {
		try {
			rs = pstate.executeQuery(); //������ �����ϰ� ����� ResultSet ��ü�� ��´�.
		} catch (SQLException e) {
			System.out.println("executeQuery() ����: "+e.getMessage() );
		}
	}//executeQuery METHOD
	
	//�ݱ�
	public void stateClose() {
		try {
			if (state != null) {
				state.close();
			}
		} catch (SQLException e) {
			System.out.println("stateClose() ����: "+e.getMessage() );
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
			System.out.println("pstateClose() ����: "+e.getMessage() );
		}
	}//pstateClose METHOD
	
	public void conClose() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println("conClose() ����: "+e.getMessage() );
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
			System.out.println("rsClose() ����: "+e.getMessage() );
		}
	}//rsClose METHOD
	
}//DbInfo CLASS
