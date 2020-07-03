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
	protected ArrayList<AnbdVO> boardList;
	public ArrayList<AnbdVO> getBoardList()
	{
		return boardList;
	}
	
	//========회원가입, 로그인 메소드========
	//회원가입한 회원정보 저장 (insert)
    public int inJoin (AnbdVO db2) {    //가져올 get,set, db에 새로운 변수명
    	getConnection();
    	//SQL문에서 변수가 들어갈 자리는 ' ? ' 로 표시
    	String SQL= "INSERT INTO user (id,pw,name,email) VALUES(?, ?, ?, ?) ";
        try {
        	pstate = con.prepareStatement(SQL);
        	pstate.setString(1,db2.getId()); //지정된 매개 변수를 지정된 문자열 값으로 설정
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
	
	public boolean selIdCheck(String id) { //ID 중복검사
		//boolean result = false;
		getConnection();
		String SQL= "select id from user where id = '"+id+"' ";
		prepareStatement(SQL);    //쿼리를 미리 준비해서 메모리에 저장시키는 거
		executeQuery(); 		//쿼리 실행
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
		  
	public boolean selLogin(String id , String pw) { //로그인 ID 검사, 비밀번호 검사
		//boolean result = false;
		getConnection();
		String SQL= "select id from user where id = '"+id+"' and pw= '"+pw+"' ";
			
		prepareStatement(SQL);    //쿼리를 미리 준비해서 메모리에 저장시키는 거
		executeQuery(); 		//쿼리 실행
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
	
	public int selLogin2(String id , String pw) { //로그인 - id가 틀렸는지, pw가 틀렸는지
		getConnection();
		String SQL= "select pw from user where id = ?";
		try {
			prepareStatement(SQL);   
			pstate.setString(1, id);
			rs = pstate.executeQuery();
			if(rs.next()) {
				if( rs.getString("pw").equals(pw) ) {
					return 1; 	//로그인 성공
				}else return 0; //비밀번호 다름
			}return -1; 		//id 틀림
		} catch (Exception e) {
			System.out.println("selLogin2() 에러: "+e.getMessage());
			e.printStackTrace();
		} 
		rsClose();
		pstateClose();
	    conClose();
		return -2;	//DB 에러
	}
	
	//데이터 베이스에 데이터를  insert 하는 method 
	public void dbtest2insert (String id, String pw, String name, String email) {
	   //dbtest2 db = new dbtest2( 1,  id, pw,  name,  email);	 
//	   //데이터베이스 접속하기
//	   try {
//		    getConnection();
//	 	    if (con != null) {System.out.println("성공");}   //접속 결과 출력
//			else {System.out.println("실패");}
//	   String sql = "insert into user "
//	   		+ "(id, pw, name, email)"    //컬럼명
//	   		+ " values (?, ?, ?, ?)";	 //값
//	   pstate = con.prepareStatement(sql);
//	   pstate.setString(1, id);
//	   pstate.setString(2, pw);
//	   pstate.setString(3, name);
//	   pstate.setString(4, email);
//	   pstate.executeUpdate();      //executeUpdatd는 insert, update, delete문에서 사용하는거
//	   pstate.close();
//	   con.close();
//	   }catch (SQLException e) {
//		   System.out.println("SQL Exception : " + e.getMessage());
//	   }
	 }
	
	//========글보기/수정/삭제, 댓글 보기/쓰기/수정/삭제, 나눔완료/취소 메소드========
	public boolean selLoginUserNo(AnbdVO vo, String id) { //세션 id로 회원번호 가져오기
		try {
			String SQL  = "SELECT userNo from user where id='"+id+"'";
			System.out.println("selLoginUserNo: "+SQL);
			getConnection();
			prepareStatement(SQL);
			executeQuery();
			if(rs.next()) { 
				vo.setLoginUserNo(rs.getInt("userNo"));
				System.out.println("회원번호: "+vo.getLoginUserNo());
			}
		}catch (Exception e) {
			System.out.println("selLoginUserNo() 에러: "+e.getMessage());
			return false;
		}
   	   	rsClose();
		pstateClose();
		conClose();
		return true;
	}
	
	public void selViewBoard(AnbdVO vo, int no) { //글보기
		String SQL  = "";
		   	   SQL += "SELECT ";
			   SQL += "	b.no, b.menu, b.status, b.photo, u.id, u.userNo, u.email, b.title, b.content ";
		       SQL += "FROM ";
		       SQL += "	board AS b LEFT JOIN user AS u ON b.userNo=u.userNo ";
		       SQL += "WHERE ";
		       SQL += "	b.no=" + no;
		
		//======================== 게시물의 컨텐츠를 얻는 블럭
		try {
			getConnection();
			prepareStatement(SQL);
			executeQuery();
			
			if(rs.next()) { //next() 커서가 다음으로 옮기며 처리된다
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
			System.out.println("viewBoard 게시물 rs.next() 에러: "+e.getMessage()); 
		}
		
		//======================== 게시물의 첨부파일을 얻는 블럭		
		SQL  = "";
		SQL += "SELECT saveFileName ";
		SQL += "FROM file  ";
		SQL += "where no="+no;
		prepareStatement(SQL);
		executeQuery();
		try {
			while(rs.next()) {   
				String filename = rs.getString("saveFileName");
				vo.AddFile(filename); //파일 array에 담기
			}
		}catch (SQLException e) {
			System.out.println("viewBoard 첨부파일 rs.next() 에러: "+e.getMessage()); 
		}	
		rsClose();
		pstateClose();
		conClose();
	}

	public ArrayList<AnbdVO> selViewComment(int no) { //댓글보기 v2
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
				//coList = new ArrayList<AnbdVO>(); //댓글이 있어야 array생성, 아니면 null
				AnbdVO vo = new AnbdVO();
				vo.setCoNo(rs.getInt("coNo"));
				vo.setId(rs.getString("id"));
					System.out.println("댓글 작성자: "+vo.getId());
				vo.setcContent(rs.getString("content"));
					System.out.println("댓글: "+vo.getcContent());
				vo.setWdate(rs.getString("wdate"));
				
				vo.setCWriterNo(rs.getInt("userNo"));
					System.out.println("댓글 작성자 번호: "+vo.getCWriterNo());
				coList.add(vo);
			}
		}catch (SQLException e) {
			System.out.println("viewComment v2 에러: "+e.getMessage()); 
		}
		rsClose();
		pstateClose();
		conClose();
		System.out.println("coList 사이즈:"+ coList.size());
		return coList;
	}
	
	public void selViewComment_v1(AnbdVO vo, int no) { //댓글보기 v1
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
				//ArrayList에 저장
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
			System.out.println("viewComment rs.next() 에러: "+e.getMessage()); 
		}
		rsClose();
		pstateClose();
		conClose();
	}
	
	public boolean upModifyBoard(AnbdVO vo, HttpServletRequest request) { 
		try{
			String savePath = request.getSession().getServletContext().getRealPath("/upload");
				System.out.println("저장 경로: "+savePath);
			int size = 1024*1024*10;
			//MultipartRequest객체 생성시 업로드 실행
			MultipartRequest multi = new MultipartRequest(request, savePath, size, "utf-8", new DefaultFileRenamePolicy());
			
			int pNo = Integer.parseInt(request.getParameter("no"));
			String pMenu = multi.getParameter("menu");
			String pTitle = multi.getParameter("title");
			String pContent = multi.getParameter("content");
			String photo = multi.getParameter("photo");
			
			getConnection();
			
			//파일 수정 - 기존 업로도된 파일 中 삭제 클릭한 파일있으면 삭제 후 insert
			int preFileCount = Integer.parseInt(multi.getParameter("fileCount")); //기존 파일 갯수
			int delCount = 0;
			//기존 파일의 파일명 불러오기
			for(int i=1; i<=preFileCount; i++) {
				String preFile = multi.getParameter("filename"+i);//이전 파일을 val을 name값을 통해 가져오기
					System.out.println("기존 filename "+i+"번재 파일명 : "+preFile);
				//기존 파일을 삭제해서 파일명(val)이 공백이면
				if(preFile==null || preFile.equals("")){ //수정페이지에서 삭제를 눌러서 val값이 없으면 해당파일 삭제
					String hiddenfilename = multi.getParameter("hiddenfilename"+i);//input hidden의 해당 i번째 val(파일명)을 가져와서 삭제
					String SQL2  = "delete from file where saveFileName='"+hiddenfilename+"'";
						System.out.println(i+"번재 기존 파일 삭제: "+SQL2);
					prepareStatement(SQL2);
					executeUpdate(); 
					delCount++; //삭제파일갯수 증가
				}else {}
			}
			int remainFileCount = preFileCount - delCount; //남은 파일 =기존 파일 갯수-삭제 파일 갯수
				System.out.println("preFileCount: "+preFileCount);
				System.out.println("delCount: "+delCount);
				System.out.println("remainFileCount: "+remainFileCount);
			if(remainFileCount == 0) { //기존파일 다 삭제시 photo N
				photo = "N";
			}
			//추가한 파일 insert
			Enumeration inputFileNames = multi.getFileNames();  //input file태그의  name 속성값을 모두 가져옴
			while(inputFileNames.hasMoreElements()) { //inputFileNames의 요소가 있으면 true, 아니면 false 반환
				String inputFileName = (String)inputFileNames.nextElement(); //name들 중에 name 한개
					//System.out.println("name='"+inputFileName+ "' : 파일있음");
				String serverSaveName;
					serverSaveName = (String)multi.getFilesystemName(inputFileName);
					//vo.addModifyFile(serverSaveName); //필요없는듯..
					if( serverSaveName != null || remainFileCount>0 ) { //추가된 파일 있거나 or 남은 파일 있으면
						photo = "Y";
						String SQL3  = "INSERT INTO file (saveFileName, no) ";
						SQL3 += "VALUES ('" +serverSaveName+ "', "+pNo+") ";
						System.out.println("파일 수정 insert: "+SQL3);
						prepareStatement(SQL3);
						executeUpdate(); 
					}else {
						photo = "N"; 
					}
			}
			//글 수정
			String SQL  = "UPDATE board SET title='"+pTitle+"', ";
				   SQL += "content='"+pContent+"', ";
				   SQL += "menu='"+pMenu+"', ";
				   SQL += "photo='"+photo+"' ";
				   SQL += "where no="+pNo;
			prepareStatement(SQL);
			executeUpdate(); 
		}catch(Exception e) {
			System.out.println("글수정 메소드 에러: "+e.getMessage());
			return false;
		}
		pstateClose(); 
		conClose(); 
		return true;
	}

	public void delDelBoard(int no) { //글삭제
		getConnection();
		
		//이 해당 게시글의 첨부파일 먼저 삭제
		String SQL  = "delete from file where no="+no;
		prepareStatement(SQL);
		executeUpdate();
		
		//이 해당 게시글의 댓글 삭제
		String SQL2  = "delete from comment where no="+no;
		prepareStatement(SQL2);
		executeUpdate();

		String SQL3  = "delete from board where no="+no;
		prepareStatement(SQL3);
		executeUpdate();
		
		pstateClose();
		conClose();
	}

	public void inSaveComment(AnbdVO vo, int no, int userNo, String content) { //댓글쓰기
		String SQL  = "INSERT INTO comment (no, userNo, content, wdate) ";
			   SQL += "values (?, ?, ?, ?);"; 
		getConnection();
		prepareStatement(SQL);
		try {
			pstate.setInt(1, no);  
			pstate.setInt(2, userNo);
			pstate.setString(3, content);
			pstate.setString(4, vo.getcWdate()); 
			System.out.println("댓글쓰기 SQL: "+SQL);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("saveComment setString()에러: "+e.getMessage());
		}
		executeUpdate();		
		pstateClose();		
		conClose();		
	}

	public void upModifyComment(int coNo, String content) { //댓글수정
		getConnection();
		String SQL  = "UPDATE comment SET content='"+content+"' where coNo="+coNo;
		prepareStatement(SQL);
		executeUpdate(); 
		pstateClose(); 
		conClose(); 
	}

	public void delDelComment(int coNo) { //댓글삭제
		getConnection();
		String SQL  = "delete from comment where coNo="+coNo;
		prepareStatement(SQL);
		executeUpdate(); 
		pstateClose(); 
		conClose(); 
	}
	
	public void upStatusDone(int no) { //거래완료
		getConnection();
		String SQL  = " UPDATE board SET status='done' where no="+no;
		prepareStatement(SQL);
		executeUpdate(); 
		pstateClose(); 
		conClose(); 
	}	

	public void upStatusCancel(int no) { //거래완료취소
		getConnection();
		String SQL  = " UPDATE board SET status='cancel' where no="+no;
		prepareStatement(SQL);
		executeUpdate(); 
		pstateClose(); 
		conClose(); 	
	}
	
	
	/* ******************************************************
	 * 
	 * [메인] 목록 불러오기
	 * 
	 ****************************************************** */
	public boolean selBoardList(ArrayList<AnbdVO> blist) 
	//BoardParam의 정보를 ArrayList 형식으로 담겠다
	//리스트는 어떤 타입이 올지 모른다 - int를 가져와도 좋고, String을 가져와도 좋다
	//담고 싶은 내용을 <> 사이에 표현한다
	//제네릭, 템플릿을 알아보자
	//boolean으로 한 이유는 try한 결과가 true라면 값을 반환하는 의미
	//= 검색한 결과를 모두 봐야 하고, 하나의 레코드만 있는 것이 아님 = 
	//= 반복해야 함 = for 구문 사용하면 좋음 = list를 for 구문으로 받을 수 있음
	
	{	
		try 
		{
			db.getConnection();
			db.createStatement();
			//photo 아직 안 끝남, userNo는 왜 넣었더라...>06.10userNo 지웠음
			
			String selectSql = "";
			selectSql += "SELECT no, menu, title, photo, wdate, status, content ";
			selectSql += "FROM board ";
			selectSql += "LEFT JOIN juso j ";
			selectSql += "ON b.jusoNo = j.jusoNo ";
					selectSql += "ORDER BY no desc ";
			System.out.println(selectSql);

			db.rs = db.state.executeQuery(selectSql);
			if(db.rs.next()) 
			{
				do
				{
					//BoardParam를 새로 1개만 만들겠어
					AnbdVO vo = new AnbdVO();
					
					//BoardParam boardList = blist.get(i);
					vo.setNo(db.rs.getInt("no"));
					vo.setMenu(db.rs.getString("menu"));
					vo.setTitle(db.rs.getString("title"));
					vo.setPhoto(db.rs.getString("photo"));
					vo.setWdate(db.rs.getString("wdate"));
					vo.setStatus(db.rs.getString("status"));
					vo.setContent(db.rs.getString("content"));
					vo.setSido(rs.getString("sido"));
					vo.setSigun(rs.getString("sigun"));
					
					//세팅한 no, menu 등의 정보를 blist에 담겠다
					blist.add(vo);
						
				}//do FLOW
				while(db.rs.next());
				//do를 실행한 후 while에서 조건을 확인
				//다음 결과가 있으면 true, do를 다시 실행
				//다음 결과가 없으면 false, 멈춤
			}//====if FLOW
			db.rsClose();
			db.stateClose();
			db.conClose();
		} //=======try FLOW
		catch (SQLException e) 
		{
			System.out.println("목록 select 쿼리 실행 불가");
			e.printStackTrace();
			return false;
		}
		return true;
		//boolean형이니까 오류가 있으면 false를 반환
	}//============selectBoard METHOD
	
	/* ******************************************************
	 * 
	 * [메인] 글 검색하기
	 * 
	 ****************************************************** */
	public boolean selSearch(ArrayList<AnbdVO> searchList, HttpServletRequest request) 
	{	
		String option = request.getParameter("option");
		String key = request.getParameter("key");
		try 
		{
			getConnection();
			state = con.createStatement();
			
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
				   
		   pstate = con.prepareStatement(selectSearchSql);
		   if(!key.equals(""))
		   {
			   pstate.setString(1, "%"+key+"%");
		   }
		   System.out.println("키워드 : "+key);
				   
		   System.out.println(selectSearchSql);
			
		   rs = pstate.executeQuery();
			if(rs.next()) 
			{
				do
				{
					AnbdVO param = new AnbdVO();
					param.setMenu(rs.getString("menu"));
					param.setTitle(rs.getString("title"));
					param.setPhoto(rs.getString("photo"));
					param.setWdate(rs.getString("wdate"));
					param.setStatus(rs.getString("status"));
					
					searchList.add(param);
					System.out.println("성공적인 검색");
				}//do FLOW
				while(rs.next());
			}//====if FLOW
			rsClose();
			pstateClose();
			stateClose();
			conClose();
		} //=======try FLOW
		catch (SQLException e) 
		{
			System.out.println("검색 select 쿼리 실행 불가");
			e.printStackTrace();
			return false;
		}
		return true;
	}//============selectBoard METHOD
	
	/* ******************************************************
	 * 
	 * [글쓰기] 파일 업로드, 글쓰기, 파일 저장
	 * 
	 ****************************************************** */
	public boolean inWrite(AnbdVO vo, HttpServletRequest request, int userNo) 
	{
		MultipartRequest multi;
		vo.SaveFileName = new ArrayList<>();
		System.out.println("uploadPath: " +vo.uploadPath);
		//파일 경로 없을 때
		if(vo.uploadPath == null || vo.uploadPath == "")
		{
			System.out.println("파일 경로를 찾을 수 없음");
			return false;
		}//if FLOW
		
		try
		{
			multi = new MultipartRequest(request,vo.uploadPath,vo.size,"UTF-8",new DefaultFileRenamePolicy());
			
			//name에 담긴 값을 enumeration 집합에 집어넣음
			Enumeration contents = multi.getFileNames();
			
			//contents에 요소가 있는지 검사
			while(contents.hasMoreElements()) 
			{
				//태그의 name 값을 가져옴
				vo.tagName  = (String)contents.nextElement();
				vo.saveName = (String) multi.getFilesystemName(vo.tagName);
				if(vo.saveName != null) 
				{
					//태그의 name값이 ~인 것에 담긴 value값을 가져옴
					vo.SaveFileName.add(vo.saveName);
				}//if FLOW
			}//====while FLOW
		} //=======try FLOW
		catch (IOException e1) 
		{
			e1.printStackTrace();
			System.out.println("첨부파일 에러" +e1.getMessage());
			return false;
		}//========catch FLOW
		
		//쿼리 구문
		try 
		{
			db.getConnection();
			//==글 insert 시작
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
			
			//==글 insert 종료
			
			//==글 번호 구하기 시작
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
			//==글 번호 구하기 종료
			
			//==파일 insert 시작
			
			//if(vo.SaveFileName.isEmpty())
			if(vo.SaveFileName != null || !vo.SaveFileName.equals("") || vo.SaveFileName.size() != 1)
			{
				System.out.println("===========파일을 첨부하지 않았음, null===========");
			}
			if(!vo.SaveFileName.isEmpty() )
			{
				System.out.println("===========파일을 첨부했음, not null===========");
				
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
			}//====if FLOW == 파일 insert 종료
			db.pstateClose();
			db.conClose();
		}//====try FLOW
		catch (SQLException e) 
		{
			System.out.println("insert 쿼리 실행 불가");
			e.printStackTrace();
			return false;
		}//===catch FLOW
		return true;
	}//====insertWriteQuery METHOD
	
	/* ******************************************************
	 * 
	 * 로그인 메소드 [시작]
	 * 
	 ****************************************************** */
	public boolean selLogin() 
	{	
		try 
		{
			getConnection();
			state = con.createStatement();
			
			String selectLoginSql = "";
				   selectLoginSql += "SELECT userNo, id, pw \n";
				   selectLoginSql += "FROM user \n";
				   selectLoginSql += "WHERE id = ?";
				   
		   pstate = db.con.prepareStatement(selectLoginSql);
		   pstate.setString(1, vo.getId());

		   //콘솔 테스트
		   System.out.println("키워드 : "+vo.getId());
		   System.out.println(selectLoginSql);
			
		   rs = pstate.executeQuery();
			if(rs.next()) 
			{
				AnbdVO vo = new AnbdVO();
				rs.getString(vo.getPw());
			}//====if FLOW
			db.rsClose();
			db.pstateClose();
			db.stateClose();
			db.conClose();
		} //=======try FLOW
		catch (SQLException e) 
		{
			System.out.println("로그인 select 쿼리 실행 불가");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/* ******************************************************
	 * 
	 * 로그인 메소드 [종료]
	 * 
	 ****************************************************** */
	
	/* ******************************************************
	 * 
	 * 메뉴 이동 전체 글 개수 [시작]
	 * 
	 ****************************************************** */
	public boolean selCountMenu(String menu, HttpServletRequest request)
	{
		String option = request.getParameter("option");
		String key = request.getParameter("key");
		try 
		{
			getConnection();
			String selCountMenuSql = "";
				   selCountMenuSql += "SELECT count(no) AS count ";
				   selCountMenuSql += "FROM board ";
				   if(menu.equals("share") || menu=="share")
					{
					   selCountMenuSql += "WHERE menu = '아나' ";
					}
				   if(menu.equals("reuse") || menu=="reuse")
					{
					   selCountMenuSql += "WHERE menu = '바다' ";
					}

				   pstate = con.prepareStatement(selCountMenuSql);
				   System.out.println(selCountMenuSql);
					
				   executeQuery();
				   rs.next();
				   int count = Integer.parseInt(rs.getString("count"));
				   System.out.println("count 개수 : "+count);
				   System.out.println("selCountMenu를 성공적으로 수행함");
		}//try FLOW
		catch(Exception e)
		{
			System.out.println("selCountMenu를 실행할 수 없음");
			e.printStackTrace();
		}
		return true;
	}
	
	
	/* ******************************************************
	 * 
	 * [메인] 메뉴 이동
	 * 
	 ****************************************************** */
	public boolean selMenu(String menu, HttpServletRequest request, int startRow, int pageSize) 
	{	
//		selCountMenu(menu, request);
//		String key = request.getParameter("key");
//		String option = request.getParameter("option");
		boardList  = new ArrayList<AnbdVO>();
		try 
		{
			getConnection();
			createStatement();
			
			String selectSql = "";
				   selectSql += "SELECT \n";
				   selectSql += "	b.no, b.menu, b.title, b.photo, b.wdate, b.status, b.content, j.sido, j.sigun \n";
				   selectSql += "FROM board b \n";
				   selectSql += "LEFT JOIN juso j \n";
				   selectSql += "ON b.jusoNo = j.jusoNo \n";
			if(menu.equals("share") || menu=="share")
			{
				   selectSql += "WHERE menu = '아나' \n";
			}
			else if(menu.equals("reuse") || menu=="reuse")
			{
				   selectSql += "WHERE menu = '바다' \n";
			}
//				   selectSql += "AND "+option+" LIKE ? ";
				   selectSql += "ORDER BY no desc \n";
				   selectSql += "LIMIT ?, ? \n";

			prepareStatement(selectSql);
//			if(!key.equals(""))
//			   {
//				   pstate.setString(1, "%"+key+"%");
//			   }
//			if(key.equals("")||key == ""||key == null)
//			   {
//				   pstate.setString(1, "%%");
//			   }
			pstate.setInt(1, startRow);
			pstate.setInt(2, pageSize);
			System.out.println(selectSql);
			executeQuery();
			if(rs.next()) 
			{
				do
				{
					AnbdVO vo = new AnbdVO();
					
					vo.setNo(rs.getInt("no"));
					vo.setMenu(rs.getString("menu"));
					vo.setTitle(rs.getString("title"));
					vo.setPhoto(rs.getString("photo"));
					vo.setWdate(rs.getString("wdate"));
					vo.setStatus(rs.getString("status"));
					vo.setContent(rs.getString("content"));
					boardList.add(vo);
						
				}//do FLOW
				while(rs.next());
			}//====if FLOW
			
			System.out.println("selMenu를 성공적으로 수행함");
		} //=======try FLOW
		catch (SQLException e) 
		{
			System.out.println("메뉴 select 쿼리 실행 불가");
			e.printStackTrace();
			return false;
		}
		finally 
		{
			rsClose();
			stateClose();
			conClose();
		}
		return true;
	}
	
	/* ******************************************************
	 * 
	 * @author 정도희
	 * @brif   글쓰기 페이지에서 주소 컬럼을 가져온다
	 * @date   2020-06-29 작성
	 * 글쓰기 주소 가져오기 [시작]
	 * 
s	 ****************************************************** */
	public boolean selJuso() 
	{
		boardList = new ArrayList<AnbdVO>();
		try 
		{
			getConnection();
			String selSidoSql =  "";
				   selSidoSql += "SELECT distinct sido \n";
				   selSidoSql += "FROM juso \n";
				   selSidoSql += "ORDER BY jusoNo asc \n";
			prepareStatement(selSidoSql);
			System.out.println(selSidoSql);
			executeQuery();
			if(rs.next())
			{
				do 
				{
					AnbdVO vo = new AnbdVO();
					
					vo.setSido(rs.getString("sido"));
					
					boardList.add(vo);
				}
				while(rs.next());
			}//if FLOW
			System.out.println("=============selJuso 실행 완료=============");
		}//====try FLOW
		catch(Exception e)
		{
			System.out.println("=============selJuso를 실행할 수 없음=============");
			e.printStackTrace();
		}
		finally 
		{
			rsClose();
			pstateClose();
			conClose();
		}
		return true;
	}
	
	/* ******************************************************
	 * 
	 * @author 정도희
	 * @brif   글쓰기 페이지에서 시/군/구 컬럼을 가져온다
	 * @date   2020-06-29 작성
	 * 글쓰기 주소 > 시/군/구 가져오기 [시작]
	 * 
s	 ****************************************************** */
	
	public boolean selSigun(String sido) 
	{
		boardList = new ArrayList<AnbdVO>();
		try 
		{
			getConnection();
			String selSigunSql =  "";
				   selSigunSql += "SELECT sido, sigun \n";
				   selSigunSql += "FROM juso \n";
				   selSigunSql += "WHERE sido = ? \n";
				   selSigunSql += "ORDER BY jusoNo asc \n";
			prepareStatement(selSigunSql);
			pstate.setString(1, sido);
			System.out.println(selSigunSql);
			executeQuery();
			if(rs.next())
			{
				do 
				{
					AnbdVO vo = new AnbdVO();
					
					vo.setSigun(rs.getString("sigun"));
					vo.setSido(rs.getString("sido"));
					
					boardList.add(vo);
				}
				while(rs.next());
			}//if FLOW
			System.out.println("=============selSigun 실행 완료=============");
		}//====try FLOW
		catch(Exception e)
		{
			System.out.println("=============selSigun 실행 불가=============");
			e.printStackTrace();
		}
		finally 
		{
			rsClose();
			pstateClose();
			conClose();
		}
		return true;
	}
	
	
}