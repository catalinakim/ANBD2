package anbd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import anbd.AnbdVO;
import anbd.DbInfo;

public class AnbdDAO extends DbInfo{
	
	DbInfo db = new DbInfo();
	AnbdVO vo = new AnbdVO();
	
	//========ȸ������, �α��� �޼ҵ�========
	//ȸ�������� ȸ������ ���� (insert)
    public int inJoin (AnbdVO db2) {    //������ get,set, db�� ���ο� ������
    	getConnection();
    	//SQL������ ������ �� �ڸ��� ' ? ' �� ǥ��
    	String SQL= "INSERT INTO user (id,pw,name,email) VALUES(?, ?, ?, ?) ";
        try {
        	pstate = con.prepareStatement(SQL);
        	pstate.setString(1,db2.getId()); //������ �Ű� ������ ������ ���ڿ� ������ ����
        	pstate.setString(2,db2.getPw());
        	pstate.setString(3,db2.getName());
        	pstate.setString(4,db2.getEmail());
            return pstate.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        pstateClose();
        conClose();
        return 1;
	}
	
	public boolean selIdCheck(String id) { //ID �ߺ��˻�
		//boolean result = false;
		getConnection();
		String SQL= "select id from user where id = '"+id+"' ";
		prepareStatement(SQL);    //������ �̸� �غ��ؼ� �޸𸮿� �����Ű�� ��
		executeQuery(); 		//���� ����
		try {
			while(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		rsClose();
		pstateClose();
		conClose();
		return false;
	}
		  
	public boolean selLogin(String id , String pw) { //�α��� ID �˻�, ��й�ȣ �˻�
		//boolean result = false;
		getConnection();
		String SQL= "select id from user where id = '"+id+"' and pw= '"+pw+"' ";
			
		prepareStatement(SQL);    //������ �̸� �غ��ؼ� �޸𸮿� �����Ű�� ��
		executeQuery(); 		//���� ����
		try {
			while(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		rsClose();
		pstateClose();
	    conClose();
		return false;
	}
	
	//������ ���̽��� �����͸�  insert �ϴ� method 
	public void dbtest2insert (String id, String pw, String name, String email) {
	   //dbtest2 db = new dbtest2( 1,  id, pw,  name,  email);	 
//	   //�����ͺ��̽� �����ϱ�
//	   try {
//		    getConnection();
//	 	    if (con != null) {System.out.println("����");}   //���� ��� ���
//			else {System.out.println("����");}
//	   String sql = "insert into user "
//	   		+ "(id, pw, name, email)"    //�÷���
//	   		+ " values (?, ?, ?, ?)";	 //��
//	   pstate = con.prepareStatement(sql);
//	   pstate.setString(1, id);
//	   pstate.setString(2, pw);
//	   pstate.setString(3, name);
//	   pstate.setString(4, email);
//	   pstate.executeUpdate();      //executeUpdatd�� insert, update, delete������ ����ϴ°�
//	   pstate.close();
//	   con.close();
//	   }catch (SQLException e) {
//		   System.out.println("SQL Exception : " + e.getMessage());
//	   }
	 }
	
	//========�ۺ���/����/����, ��� ����/����/����/����, �����Ϸ�/��� �޼ҵ�========
	public boolean selLoginUserNo(AnbdVO vo, String id) { //���� id�� ȸ����ȣ ��������
		try {
			String SQL  = "SELECT userNo from user where id='"+id+"'";
			System.out.println("selLoginUserNo: "+SQL);
			getConnection();
			prepareStatement(SQL);
			executeQuery();
			if(rs.next()) { 
				vo.setLoginUserNo(rs.getInt("userNo"));
				System.out.println("ȸ����ȣ: "+vo.getLoginUserNo());
			}
		}catch (Exception e) {
			System.out.println("selLoginUserNo() ����: "+e.getMessage());
			return false;
		}
   	   	rsClose();
		pstateClose();
		conClose();
		return true;
	}
	
	public void selViewBoard(AnbdVO vo, int no) { //�ۺ���
		String SQL  = "";
		   	   SQL += "SELECT ";
			   SQL += "	b.no, b.menu, b.status, b.photo, u.id, u.userNo, u.email, b.title, b.content ";
		       SQL += "FROM ";
		       SQL += "	board AS b LEFT JOIN user AS u ON b.userNo=u.userNo ";
		       SQL += "WHERE ";
		       SQL += "	b.no=" + no;
		
		//======================== �Խù��� �������� ��� ��
		try {
			getConnection();
			prepareStatement(SQL);
			executeQuery();
			
			if(rs.next()) { //next() Ŀ���� �������� �ű�� ó���ȴ�
				vo.setNo(rs.getInt("no")); 
				vo.setStatus(rs.getString("status")); 
				vo.setMenu(rs.getString("menu")); 
				vo.setPhoto(rs.getString("photo")); 
				vo.setId(rs.getString("id"));
				vo.setUserNo(rs.getInt("userNo"));
				vo.setEmail(rs.getString("email"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
			}
		}catch (Exception e) {
			System.out.println("viewBoard �Խù� rs.next() ����: "+e.getMessage()); 
		}
		
		//======================== �Խù��� ÷�������� ��� ��		
		SQL  = "";
		SQL += "SELECT saveFileName ";
		SQL += "FROM file  ";
		SQL += "where no="+no;
		prepareStatement(SQL);
		executeQuery();
		try {
			while(rs.next()) {   
				String filename = rs.getString("saveFileName");
				vo.AddFile(filename); //���� array�� ���
			}
		}catch (SQLException e) {
			System.out.println("viewBoard ÷������ rs.next() ����: "+e.getMessage()); 
		}	
		rsClose();
		pstateClose();
		conClose();
	}

	public ArrayList<AnbdVO> selViewComment(int no) { //��ۺ��� v2
		ArrayList<AnbdVO> coList = new ArrayList<AnbdVO>();
		String SQL  = "";
			   SQL += "SELECT ";
			   SQL += "	c.no, c.coNo, c.content, c.wdate, u.userNo, u.id ";
			   SQL += "FROM ";
			   SQL += "	comment AS c LEFT JOIN user AS u ON c.userNo=u.userNo ";
			   SQL += "WHERE ";
			   SQL += "	c.no=" + no;
	    
		getConnection();
		prepareStatement(SQL);
		executeQuery();
		try {
			while(rs.next()) {   
				//coList = new ArrayList<AnbdVO>(); //����� �־�� array����, �ƴϸ� null
				AnbdVO vo = new AnbdVO();
				vo.setCoNo(rs.getInt("coNo"));
				vo.setId(rs.getString("id"));
					System.out.println("��� �ۼ���: "+vo.getId());
				vo.setcContent(rs.getString("content"));
					System.out.println("���: "+vo.getcContent());
				vo.setWdate(rs.getString("wdate"));
				
				vo.setCWriterNo(rs.getInt("userNo"));
					System.out.println("��� �ۼ��� ��ȣ: "+vo.getCWriterNo());
				coList.add(vo);
			}
		}catch (SQLException e) {
			System.out.println("viewComment v2 ����: "+e.getMessage()); 
		}
		rsClose();
		pstateClose();
		conClose();
		System.out.println("coList ������:"+ coList.size());
		return coList;
	}
	
	public void selViewComment_v1(AnbdVO vo, int no) { //��ۺ��� v1
		String SQL  = "";
			   SQL += "SELECT ";
			   SQL += "	c.no, c.coNo, c.content, c.wdate, u.userNo, u.id ";
			   SQL += "FROM ";
			   SQL += "	comment AS c LEFT JOIN user AS u ON c.userNo=u.userNo ";
			   SQL += "WHERE ";
			   SQL += "	c.no=" + no;
	    
		getConnection();
		prepareStatement(SQL);
		executeQuery();
		try {
			while(rs.next()) {   
				//ArrayList�� ����
				String coNo = rs.getString("coNo");
				vo.AddCo(coNo);
				String id = rs.getString("id");
				vo.AddCo(id);
				String content = rs.getString("content");
				vo.AddCo(content);
				String wdate = rs.getString("wdate");
				vo.AddCo(wdate);
				String userNo = rs.getString("userNo");
				vo.AddCo(userNo);
			}
		}catch (SQLException e) {
			System.out.println("viewComment rs.next() ����: "+e.getMessage()); 
		}
		rsClose();
		pstateClose();
		conClose();
	}
	
	public boolean upModifyBoard(AnbdVO vo, HttpServletRequest request) { 
		try{
			String savePath = request.getSession().getServletContext().getRealPath("/upload");
				System.out.println("���� ���: "+savePath);
			int size = 1024*1024*10;
			//MultipartRequest��ü ������ ���ε� ����
			MultipartRequest multi = new MultipartRequest(request, savePath, size, "utf-8", new DefaultFileRenamePolicy());
			
			int pNo = Integer.parseInt(request.getParameter("no"));
			String pMenu = multi.getParameter("menu");
			String pTitle = multi.getParameter("title");
			String pContent = multi.getParameter("content");
			String photo = multi.getParameter("photo");
			
			getConnection();
			
			//���� ���� - ���� ���ε��� ���� �� ���� Ŭ���� ���������� ���� �� insert
			int preFileCount = Integer.parseInt(multi.getParameter("fileCount")); //���� ���� ����
			int delCount = 0;
			//���� ������ ���ϸ� �ҷ�����
			for(int i=1; i<=preFileCount; i++) {
				String preFile = multi.getParameter("filename"+i);//���� ������ val�� name���� ���� ��������
					System.out.println("���� filename "+i+"���� ���ϸ� : "+preFile);
				//���� ������ �����ؼ� ���ϸ�(val)�� �����̸�
				if(preFile==null || preFile.equals("")){ //�������������� ������ ������ val���� ������ �ش����� ����
					String hiddenfilename = multi.getParameter("hiddenfilename"+i);//input hidden�� �ش� i��° val(���ϸ�)�� �����ͼ� ����
					String SQL2  = "delete from file where saveFileName='"+hiddenfilename+"'";
						System.out.println(i+"���� ���� ���� ����: "+SQL2);
					prepareStatement(SQL2);
					executeUpdate(); 
					delCount++; //�������ϰ��� ����
				}else {}
			}
			int remainFileCount = preFileCount - delCount; //���� ���� =���� ���� ����-���� ���� ����
				System.out.println("preFileCount: "+preFileCount);
				System.out.println("delCount: "+delCount);
				System.out.println("remainFileCount: "+remainFileCount);
			if(remainFileCount == 0) { //�������� �� ������ photo N
				photo = "N";
			}
			//�߰��� ���� insert
			Enumeration inputFileNames = multi.getFileNames();  //input file�±���  name �Ӽ����� ��� ������
			while(inputFileNames.hasMoreElements()) { //inputFileNames�� ��Ұ� ������ true, �ƴϸ� false ��ȯ
				String inputFileName = (String)inputFileNames.nextElement(); //name�� �߿� name �Ѱ�
					//System.out.println("name='"+inputFileName+ "' : ��������");
				String serverSaveName;
					serverSaveName = (String)multi.getFilesystemName(inputFileName);
					//vo.addModifyFile(serverSaveName); //�ʿ���µ�..
					if( serverSaveName != null || remainFileCount>0 ) { //�߰��� ���� �ְų� or ���� ���� ������
						photo = "Y";
						String SQL3  = "INSERT INTO file (saveFileName, no) ";
						SQL3 += "VALUES ('" +serverSaveName+ "', "+pNo+") ";
						System.out.println("���� ���� insert: "+SQL3);
						prepareStatement(SQL3);
						executeUpdate(); 
					}else {
						photo = "N"; 
					}
			}
			//�� ����
			String SQL  = "UPDATE board SET title='"+pTitle+"', ";
				   SQL += "content='"+pContent+"', ";
				   SQL += "menu='"+pMenu+"', ";
				   SQL += "photo='"+photo+"' ";
				   SQL += "where no="+pNo;
			prepareStatement(SQL);
			executeUpdate(); 
		}catch(Exception e) {
			System.out.println("�ۼ��� �޼ҵ� ����: "+e.getMessage());
			return false;
		}
		pstateClose(); 
		conClose(); 
		return true;
	}

	public void delDelBoard(int no) { //�ۻ���
		getConnection();
		
		//�� �ش� �Խñ��� ÷������ ���� ����
		String SQL  = "delete from file where no="+no;
		prepareStatement(SQL);
		executeUpdate();
		
		//�� �ش� �Խñ��� ��� ����
		String SQL2  = "delete from comment where no="+no;
		prepareStatement(SQL2);
		executeUpdate();

		String SQL3  = "delete from board where no="+no;
		prepareStatement(SQL3);
		executeUpdate();
		
		pstateClose();
		conClose();
	}

	public void inSaveComment(AnbdVO vo, int no, int userNo, String content) { //��۾���
		String SQL  = "INSERT INTO comment (no, userNo, content, wdate) ";
			   SQL += "values (?, ?, ?, ?);"; 
		getConnection();
		prepareStatement(SQL);
		try {
			pstate.setInt(1, no);  
			pstate.setInt(2, userNo);
			pstate.setString(3, content);
			pstate.setString(4, vo.getcWdate()); 
			System.out.println("��۾��� SQL: "+SQL);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("saveComment setString()����: "+e.getMessage());
		}
		executeUpdate();		
		pstateClose();		
		conClose();		
	}

	public void upModifyComment(int coNo, String content) { //��ۼ���
		getConnection();
		String SQL  = "UPDATE comment SET content='"+content+"' where coNo="+coNo;
		prepareStatement(SQL);
		executeUpdate(); 
		pstateClose(); 
		conClose(); 
	}

	public void delDelComment(int coNo) { //��ۻ���
		getConnection();
		String SQL  = "delete from comment where coNo="+coNo;
		prepareStatement(SQL);
		executeUpdate(); 
		pstateClose(); 
		conClose(); 
	}
	
	public void upStatusDone(int no) { //�ŷ��Ϸ�
		getConnection();
		String SQL  = " UPDATE board SET status='done' where no="+no;
		prepareStatement(SQL);
		executeUpdate(); 
		pstateClose(); 
		conClose(); 
	}	

	public void upStatusCancel(int no) { //�ŷ��Ϸ����
		getConnection();
		String SQL  = " UPDATE board SET status='cancel' where no="+no;
		prepareStatement(SQL);
		executeUpdate(); 
		pstateClose(); 
		conClose(); 	
	}
	
	
	/* ******************************************************
	 * 
	 * [����] ��� �ҷ�����
	 * 
	 ****************************************************** */
	public boolean selBoardList(ArrayList<AnbdVO> blist) 
	//BoardParam�� ������ ArrayList �������� ��ڴ�
	//����Ʈ�� � Ÿ���� ���� �𸥴� - int�� �����͵� ����, String�� �����͵� ����
	//��� ���� ������ <> ���̿� ǥ���Ѵ�
	//���׸�, ���ø��� �˾ƺ���
	//boolean���� �� ������ try�� ����� true��� ���� ��ȯ�ϴ� �ǹ�
	//= �˻��� ����� ��� ���� �ϰ�, �ϳ��� ���ڵ常 �ִ� ���� �ƴ� = 
	//= �ݺ��ؾ� �� = for ���� ����ϸ� ���� = list�� for �������� ���� �� ����
	
	{	
		try 
		{
			db.getConnection();
			db.createStatement();
			//photo ���� �� ����, userNo�� �� �־�����...>06.10userNo ������
			
			String selectSql = "";
			selectSql += "SELECT no, menu, title, photo, wdate, status, content ";
			selectSql += "FROM board ";
			selectSql += "ORDER BY no desc ";
			System.out.println(selectSql);

			db.rs = db.state.executeQuery(selectSql);
			if(db.rs.next()) 
			{
				do
				{
					//BoardParam�� ���� 1���� ����ھ�
					AnbdVO vo = new AnbdVO();
					
					//BoardParam boardList = blist.get(i);
					vo.setNo(db.rs.getInt("no"));
					vo.setMenu(db.rs.getString("menu"));
					vo.setTitle(db.rs.getString("title"));
					vo.setPhoto(db.rs.getString("photo"));
					vo.setWdate(db.rs.getString("wdate"));
					vo.setStatus(db.rs.getString("status"));
					vo.setContent(db.rs.getString("content"));
					
					//������ no, menu ���� ������ blist�� ��ڴ�
					blist.add(vo);
						
				}//do FLOW
				while(db.rs.next());
				//do�� ������ �� while���� ������ Ȯ��
				//���� ����� ������ true, do�� �ٽ� ����
				//���� ����� ������ false, ����
			}//====if FLOW
			db.rsClose();
			db.stateClose();
			db.conClose();
		} //=======try FLOW
		catch (SQLException e) 
		{
			System.out.println("��� select ���� ���� �Ұ�");
			e.printStackTrace();
			return false;
		}
		return true;
		//boolean���̴ϱ� ������ ������ false�� ��ȯ
	}//============selectBoard METHOD
	
	/* ******************************************************
	 * 
	 * [����] �� �˻��ϱ�
	 * 
	 ****************************************************** */
	public boolean selSearch(ArrayList<AnbdVO> searchList, HttpServletRequest request) 
	{	
		String option = request.getParameter("option");
		String key = request.getParameter("key");
		try 
		{
			db.getConnection();
			db.state = db.con.createStatement();
			
			String selectSearchSql = "";
				   selectSearchSql += "SELECT \n";
				   selectSearchSql += "u.id as id, u.email as email, b.title as title, \n";
				   selectSearchSql += "b.no, b.menu, b.photo, b.wdate, b.status \n";
				   selectSearchSql += "FROM board b \n";
				   selectSearchSql += "LEFT JOIN user u \n";
				   selectSearchSql += "ON b.userNo = u.userNo \n";
				   if(!key.equals("")) 
				   {
				   selectSearchSql += "WHERE "+option+" LIKE ?  \n";
				   }
				   selectSearchSql += "ORDER BY no desc \n";
				   
		   db.pstate = db.con.prepareStatement(selectSearchSql);
		   if(!key.equals(""))
		   {
			   db.pstate.setString(1, "%"+key+"%");
		   }
		   System.out.println("Ű���� : "+key);
				   
		   System.out.println(selectSearchSql);
			
		   db.rs = db.pstate.executeQuery();
			if(db.rs.next()) 
			{
				do
				{
					AnbdVO param = new AnbdVO();
					param.setMenu(db.rs.getString("menu"));
					param.setTitle(db.rs.getString("title"));
					param.setPhoto(db.rs.getString("photo"));
					param.setWdate(db.rs.getString("wdate"));
					param.setStatus(db.rs.getString("status"));
					
					searchList.add(param);
					System.out.println("�������� �˻�");
				}//do FLOW
				while(db.rs.next());
			}//====if FLOW
			db.rsClose();
			db.pstateClose();
			db.stateClose();
			db.conClose();
		} //=======try FLOW
		catch (SQLException e) 
		{
			System.out.println("�˻� select ���� ���� �Ұ�");
			e.printStackTrace();
			return false;
		}
		return true;
	}//============selectBoard METHOD
	
	/* ******************************************************
	 * 
	 * [�۾���] ���� ���ε�, �۾���, ���� ����
	 * 
	 ****************************************************** */
	public boolean inWrite(AnbdVO vo, HttpServletRequest request, int userNo) 
	{
		MultipartRequest multi;
		vo.SaveFileName = new ArrayList<>();
		System.out.println("uploadPath: " +vo.uploadPath);
		//���� ��� ���� ��
		if(vo.uploadPath == null || vo.uploadPath == "")
		{
			System.out.println("���� ��θ� ã�� �� ����");
			return false;
		}//if FLOW
		
		try
		{
			multi = new MultipartRequest(request,vo.uploadPath,vo.size,"UTF-8",new DefaultFileRenamePolicy());
			
			//name�� ��� ���� enumeration ���տ� �������
			Enumeration contents = multi.getFileNames();
			
			//contents�� ��Ұ� �ִ��� �˻�
			while(contents.hasMoreElements()) 
			{
				//�±��� name ���� ������
				vo.tagName  = (String)contents.nextElement();
				vo.saveName = (String) multi.getFilesystemName(vo.tagName);
				if(vo.saveName != null) 
				{
					//�±��� name���� ~�� �Ϳ� ��� value���� ������
					vo.SaveFileName.add(vo.saveName);
				}//if FLOW
			}//====while FLOW
		} //=======try FLOW
		catch (IOException e1) 
		{
			e1.printStackTrace();
			System.out.println("÷������ ����" +e1.getMessage());
			return false;
		}//========catch FLOW
		
		//���� ����
		try 
		{
			db.getConnection();
			//==�� insert ����
			String menu   = multi.getParameter("menu");
			String title  = multi.getParameter("title");
			String content= multi.getParameter("content");
			
			vo.setMenu(menu);
			vo.setTitle(title);
			vo.setContent(content);
			
			if(vo.saveName == null)
			{
				vo.setPhoto("N");
			}
			else 
			{
				vo.setPhoto("Y");
			}
			String insertBoardSql  = "INSERT INTO board ";
				   insertBoardSql += "(menu, title, content, userNo, wdate, photo) ";
				   insertBoardSql += "VALUES (?, ?, ?, ?, curdate(), ?)";
			
			db.pstate = db.con.prepareStatement(insertBoardSql);
			
			db.pstate.setString(1, menu);
			db.pstate.setString(2, title);
			db.pstate.setString(3, content);
			db.pstate.setInt(4, userNo);
			db.pstate.setString(5, vo.getPhoto());

			System.out.println(insertBoardSql);
			System.out.println("saveName = "+vo.saveName);
			System.out.println("SaveFileName = "+vo.SaveFileName);
			System.out.println("vo.SaveFileName.size() = "+vo.SaveFileName.size());
			
			db.pstate.executeUpdate();
			
			//==�� insert ����
			
			//==�� ��ȣ ���ϱ� ����
			int a;
			String selectBoardNoSql = "SELECT LAST_INSERT_ID() as insertNo ";
			db.state = db.con.createStatement();
			
			db.rs = db.state.executeQuery(selectBoardNoSql);
			while(db.rs.next()) 
			{
				a = db.rs.getInt("insertNo");
				vo.setNo(a);
			}//====while FLOW
			db.rsClose();
			//==�� ��ȣ ���ϱ� ����
			
			//==���� insert ����
			
			//if(vo.SaveFileName.isEmpty())
			if(vo.SaveFileName != null || !vo.SaveFileName.equals("") || vo.SaveFileName.size() != 1)
			{
				System.out.println("===========������ ÷������ �ʾ���, null===========");
			}
			if(!vo.SaveFileName.isEmpty() )
			{
				System.out.println("===========������ ÷������, not null===========");
				
				for(int i=0; i<vo.SaveFileName.size(); i++)
				{
					String insertFileSql  = "INSERT INTO file ";
					insertFileSql += "(saveFileName, no) ";
					insertFileSql += "VALUES (?, ?)"; 
					db.pstate = db.con.prepareStatement(insertFileSql);
					db.pstate.setString(1, vo.getSaveName(i));
					db.pstate.setInt(2, vo.getNo());
					db.pstate.executeUpdate();
					System.out.println(insertFileSql);
				}//for FLOW
			}//====if FLOW == ���� insert ����
			db.pstateClose();
			db.conClose();
		}//====try FLOW
		catch (SQLException e) 
		{
			System.out.println("insert ���� ���� �Ұ�");
			e.printStackTrace();
			return false;
		}//===catch FLOW
		return true;
	}//====insertWriteQuery METHOD
	
}