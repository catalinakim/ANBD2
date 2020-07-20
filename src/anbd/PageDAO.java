package anbd;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public class PageDAO extends DbInfo{
	
	public int count = 0;  //전체 게시물 갯수
	//public String sido = "";
	
	public String selSidoByJusoNo(int jusoNo) {
		String sido = "";
		String SQL  = "select distinct sido from juso where jusoNo= "+jusoNo;
		try {
			getConnection();
			prepareStatement(SQL);
			executeQuery();
			rs.next();
			sido = rs.getString("sido");
		} catch (Exception e) {
			System.out.println("selSidoByJusoNo() 에러: "+e.getMessage());
		}
		rsClose();
		pstateClose();
		conClose();
		return sido;
	}
	
	public String selSigunByJusoNo(int jusoNo) {
		String sigun = "";
		String SQL  = "select sigun from juso where jusoNo= "+jusoNo;
		try {
			getConnection();
			prepareStatement(SQL);
			executeQuery();
			rs.next();
			sigun = rs.getString("sigun");
		} catch (Exception e) {
			System.out.println("selSigunByJusoNo() 에러: "+e.getMessage());
		}
		rsClose();
		pstateClose();
		conClose();
		return sigun;
	}
	
	public void selMainListJuDone( ArrayList<AnbdVO> mainList, int startRow, int pageSize, HttpServletRequest request, 
			boolean juso, boolean noDone) {
		String menu = request.getParameter("menu");
		String pMenu =""; //검색용
		if(menu==null || menu.equals("") ){   //메인은 menu가 null
			pMenu="";
		}else if(menu.equals("share")){
			pMenu="아나";
		}else if(menu.equals("reuse")){
			pMenu="바다";
		}
		
		String option = request.getParameter("option");
		if( option!=null ) {
			if(option.equals("emailOption")) {
				option ="email";
			}
		}
		String mKey = request.getParameter("key");
//		if(mKey==null){ //그냥 검색안하면 null
//			mKey=""; }
		String jusoNo = request.getParameter("jusoNo");
		
		String SQL  = "select count(*) as count from board b ";
		SQL += "LEFT JOIN user u ";
		SQL += "ON b.userNo = u.userNo ";
		SQL += "LEFT JOIN juso j ";
		SQL += "ON b.jusoNo = j.jusoNo ";
		
		//검색 + 메뉴
		if(juso==true && noDone==true) {     //시도 및 시군구 선택후  확인 클릭하여 나온 '결과 페이지'에서, 거래완료 안보기 클릭시
			if(mKey!=null && option!=null ){ //검색 o	 mKey!=null						
				if(  pMenu.equals("아나") || pMenu.equals("바다") ){ //검색 o, 메뉴  o
					SQL += "where "+ option +" like '%" + mKey +"%' and menu = '"+pMenu+"' and j.jusoNo ='"+jusoNo+"' and status not in('done')";
				}else { 										  //검색 o, 메뉴  x
					SQL += "where "+ option +" like '%" + mKey +"%' and j.jusoNo ='"+jusoNo+"' and status not in('done')";
				}
			}else { //검색x
				if( pMenu.equals("아나") || pMenu.equals("바다") ){  //검색 x, 메뉴  o
					SQL += "where menu= '"+pMenu+"' and j.jusoNo ='"+jusoNo+"' and status not in('done')";
				}else {											  //검색 x, 메뉴  x
					SQL += "where j.jusoNo ='"+jusoNo+"' and status not in('done')";
				}
			}
		}else if(juso==true) {
			if(mKey!=null && option!=null ){ //검색 o	 mKey!=null						
				if(  pMenu.equals("아나") || pMenu.equals("바다") ){ //검색 o, 메뉴  o
					SQL += "where "+ option +" like '%" + mKey +"%' and menu = '"+pMenu+"' and j.jusoNo ='"+jusoNo+"' ";
				}else { 										  //검색 o, 메뉴  x
					SQL += "where "+ option +" like '%" + mKey +"%' and j.jusoNo ='"+jusoNo+"' ";
				}
			}else { //검색x
				if( pMenu.equals("아나") || pMenu.equals("바다") ){  //검색 x, 메뉴  o
					SQL += "where menu= '"+pMenu+"' and j.jusoNo ='"+jusoNo+"' ";
				}else {											  //검색 x, 메뉴  x
					SQL += "where j.jusoNo ='"+jusoNo+"' ";
				}
			}
		}else if(noDone==true) {
			if(mKey!=null && option!=null){ //검색 o							
				if(  pMenu.equals("아나") || pMenu.equals("바다") ){ //검색 o, 메뉴  o
					SQL += "where "+ option +" like '%" + mKey +"%' and menu = '"+pMenu+"' and status not in('done')";
				}else { 										  //검색 o, 메뉴  x
					SQL += "where "+ option +" like '%" + mKey +"%' and status not in('done')";
				}
			}else { //검색x
				if( pMenu.equals("아나") || pMenu.equals("바다") ){  //검색 x, 메뉴  o
					SQL += "where menu= '"+pMenu+"' and status not in('done')";
				}else {											  //검색 x, 메뉴  x
					SQL += "where status not in('done')";
				}
			}
		}else {
			if(mKey!=null && option!=null ){ //검색 o	 mKey!=null						
				if(  pMenu.equals("아나") || pMenu.equals("바다") ){ //검색 o, 메뉴  o
					SQL += "where "+ option +" like '%" + mKey +"%' and menu = '"+pMenu+"' ";
				}else { 										  //검색 o, 메뉴  x
					SQL += "where "+ option +" like '%" + mKey +"%' ";
				}
			}else { //검색x
				if( pMenu.equals("아나") || pMenu.equals("바다") ){  //검색 x, 메뉴  o
					SQL += "where menu= '"+pMenu+"' ";
				}else {											  //검색 x, 메뉴  x
				}
			}
		}
		
		try {
			getConnection();
			prepareStatement(SQL);
			System.out.println("SQL: "+SQL);
			executeQuery();
			rs.next();
			count = Integer.parseInt(rs.getString("count"));
			System.out.println("총 게시글 수: "+count);
		} catch (Exception e) {
			System.out.println("select count(*) 에러: "+e.getMessage());
			e.printStackTrace();
		}
		rsClose();
		pstateClose();
		
		SQL  = "select b.no, u.id, u.email, b.menu, b.status, b.title, b.photo, b.wdate, j.sido, j.sigun \n";
		SQL += "FROM board b ";
		SQL += "LEFT JOIN user u ";
		SQL += "ON b.userNo = u.userNo ";
		SQL += "LEFT JOIN juso j ";
		SQL += "ON b.jusoNo = j.jusoNo \n";
		
		//검색 + 메뉴
		if(juso==true && noDone==true) {     //시도 및 시군구 선택후  확인 클릭하여 나온 '결과 페이지'에서, 거래완료 안보기 클릭시
			if(mKey!=null && option!=null ){ //검색 o	 mKey!=null						
				if(  pMenu.equals("아나") || pMenu.equals("바다") ){ //검색 o, 메뉴  o
					SQL += "where "+ option +" like '%" + mKey +"%' and menu = '"+pMenu+"' and j.jusoNo ='"+jusoNo+"' and status not in('done')";
				}else { 										  //검색 o, 메뉴  x
					SQL += "where "+ option +" like '%" + mKey +"%' and j.jusoNo ='"+jusoNo+"' and status not in('done')";
				}
			}else { //검색x
				if( pMenu.equals("아나") || pMenu.equals("바다") ){  //검색 x, 메뉴  o
					SQL += "where menu= '"+pMenu+"' and j.jusoNo ='"+jusoNo+"' and status not in('done')";
				}else {											  //검색 x, 메뉴  x
					SQL += "where j.jusoNo ='"+jusoNo+"' and status not in('done')";
				}
			}
		}else if(juso==true) {
			if(mKey!=null && option!=null ){ //검색 o	 mKey!=null						
				if(  pMenu.equals("아나") || pMenu.equals("바다") ){ //검색 o, 메뉴  o
					SQL += "where "+ option +" like '%" + mKey +"%' and menu = '"+pMenu+"' and j.jusoNo ='"+jusoNo+"' ";
				}else { 										  //검색 o, 메뉴  x
					SQL += "where "+ option +" like '%" + mKey +"%' and j.jusoNo ='"+jusoNo+"' ";
				}
			}else { //검색x
				if( pMenu.equals("아나") || pMenu.equals("바다") ){  //검색 x, 메뉴  o
					SQL += "where menu= '"+pMenu+"' and j.jusoNo ='"+jusoNo+"' ";
				}else {											  //검색 x, 메뉴  x
					SQL += "where j.jusoNo ='"+jusoNo+"' ";
				}
			}
		}else if(noDone==true) {
			if(mKey!=null && option!=null){ //검색 o							
				if(  pMenu.equals("아나") || pMenu.equals("바다") ){ //검색 o, 메뉴  o
					SQL += "where "+ option +" like '%" + mKey +"%' and menu = '"+pMenu+"' and status not in('done')";
				}else { 										  //검색 o, 메뉴  x
					SQL += "where "+ option +" like '%" + mKey +"%' and status not in('done')";
				}
			}else { //검색x
				if( pMenu.equals("아나") || pMenu.equals("바다") ){  //검색 x, 메뉴  o
					SQL += "where menu= '"+pMenu+"' and status not in('done')";
				}else {											  //검색 x, 메뉴  x
					SQL += "where status not in('done')";
				}
			}
		}else {
			if(mKey!=null && option!=null ){ //검색 o	 mKey!=null						
				if(  pMenu.equals("아나") || pMenu.equals("바다") ){ //검색 o, 메뉴  o
					SQL += "where "+ option +" like '%" + mKey +"%' and menu = '"+pMenu+"' ";
				}else { 										  //검색 o, 메뉴  x
					SQL += "where "+ option +" like '%" + mKey +"%' ";
				}
			}else { //검색x
				if( pMenu.equals("아나") || pMenu.equals("바다") ){  //검색 x, 메뉴  o
					SQL += "where menu= '"+pMenu+"' ";
				}else {											  //검색 x, 메뉴  x
				}
			}
		}
		
		SQL += "order by no desc limit " + startRow + "," + pageSize + "  ";
		System.out.println("=== 19개씩 목록 select : "+SQL);
		try {
			prepareStatement(SQL);
			executeQuery();
			while(rs.next()) { 
				AnbdVO vo = new AnbdVO();
				vo.setNo(rs.getInt("no")); 
				vo.setMenu(rs.getString("menu")); 
				vo.setStatus(rs.getString("status")); 
				vo.setTitle(rs.getString("title"));
				vo.setPhoto(rs.getString("photo")); 
				vo.setWdate(rs.getString("wdate"));
				vo.setSido(rs.getString("sido"));
				vo.setSigun(rs.getString("sigun"));
				mainList.add(vo);
			}
		}catch (Exception e) {
			System.out.println("selMainList 에러: "+e.getMessage()); 
		}
		rsClose();
		pstateClose();
		conClose();
	}
	public void selMainList2( ArrayList<AnbdVO> mainList, int startRow, int pageSize, HttpServletRequest request) {
		String menu = request.getParameter("menu");
		String pMenu =""; //검색용
		if(menu==null || menu.equals("") ){   //메인은 menu가 null
			pMenu="";
		}else if(menu.equals("share")){
			pMenu="아나";
		}else if(menu.equals("reuse")){
			pMenu="바다";
		}
		System.out.println("메뉴: "+pMenu);
		
		String option = request.getParameter("option");
		if( option!=null ) {
			if(option.equals("emailOption")) {
				option ="email";
			}
		}
		String mKey = request.getParameter("key");
//		if(mKey==null){ //그냥 검색안하면 null
//			mKey=""; }
		System.out.println("mKey: "+mKey);
		
		String SQL  = "select count(*) as count from board b ";
			   SQL += "LEFT JOIN user u ";
			   SQL += "ON b.userNo = u.userNo ";
		
		//검색만 했을때(메뉴 테스트 전)
		//{{if(mKey!=null ){ 								//mKey==null아니면
			/*if( mKey!=null || !mKey.trim().equals("")){ //검색어가 있으면(위랑 조건이 같은가?..조금 다르긴 함..)
				SQL += "where "+ option +" like '%" + mKey +"%' ";
			}
		}*//*else if( mKey.trim().length()==0 ){			 //위에서 if(mKey==null){}해줘야 nullpoint에러 안생김..
			System.out.println("key is 0"); 			 //mKey=""
		}*///}}
				
		//검색 + 메뉴
		if(mKey!=null && option!=null ){ //검색 o	 mKey!=null						
			if(  pMenu.equals("아나") || pMenu.equals("바다") ){ //검색 o, 메뉴  o
				SQL += "where "+ option +" like '%" + mKey +"%' and menu = '"+pMenu+"' ";
			}else { 										  //검색 o, 메뉴  x
				SQL += "where "+ option +" like '%" + mKey +"%' ";
			}
		}else { //검색x
			if( pMenu.equals("아나") || pMenu.equals("바다") ){  //검색 x, 메뉴  o
				SQL += "where menu= '"+pMenu+"' ";
			}else {											  //검색 x, 메뉴  x
			}
		}
		try {
			getConnection();
			prepareStatement(SQL);
			System.out.println("SQL: "+SQL);
			executeQuery();
			rs.next();
			count = Integer.parseInt(rs.getString("count"));
			System.out.println("총 게시글 수: "+count);
		} catch (Exception e) {
			System.out.println("select count(*) 에러: "+e.getMessage());
			e.printStackTrace();
		}
		rsClose();
		pstateClose();
		
		SQL  = "select b.no, u.id, u.email, b.menu, b.status, b.title, b.photo, b.wdate, j.sido, j.sigun ";
		SQL += "FROM board b ";
		SQL += "LEFT JOIN user u ";
		SQL += "ON b.userNo = u.userNo ";
		SQL += "LEFT JOIN juso j ";
		SQL += "ON b.jusoNo = j.jusoNo ";
//		if(mKey==null) { 						   //검색만
//		}else if( mKey!=null || !mKey.equals("")){ //검색어가 있으면
//			SQL += "where "+option+" like '%" + mKey +"%' ";
//		}
		
		//검색 + 메뉴
		if(mKey!=null && option!=null){ //검색 o							
			if(  pMenu.equals("아나") || pMenu.equals("바다") ){ //검색 o, 메뉴  o
				SQL += "where "+ option +" like '%" + mKey +"%' and menu = '"+pMenu+"' ";
			}else { 										  //검색 o, 메뉴  x
				SQL += "where "+ option +" like '%" + mKey +"%' ";
			}
		}else { //검색x
			if( pMenu.equals("아나") || pMenu.equals("바다") ){  //검색 x, 메뉴  o
				SQL += "where menu= '"+pMenu+"' ";
			}else {											  //검색 x, 메뉴  x
			}
		}
		
	   	SQL += "order by no desc limit " + startRow + "," + pageSize + "  ";
	   	System.out.println("10개씩 목록 select 구문  : "+SQL);
		try {
			prepareStatement(SQL);
			executeQuery();
			while(rs.next()) { 
				AnbdVO vo = new AnbdVO();
				vo.setNo(rs.getInt("no")); 
				vo.setMenu(rs.getString("menu")); 
				vo.setStatus(rs.getString("status")); 
				vo.setTitle(rs.getString("title"));
				vo.setPhoto(rs.getString("photo")); 
				vo.setWdate(rs.getString("wdate"));
				vo.setSido(rs.getString("sido"));
				vo.setSigun(rs.getString("sigun"));
				mainList.add(vo);
			}
		}catch (Exception e) {
			System.out.println("selMainList 에러: "+e.getMessage()); 
		}
		rsClose();
		pstateClose();
		conClose();
	}
	
	public void selMainList( ArrayList<AnbdVO> mainList, int startRow, int pageSize, String mKey) {
		String SQL  = "select count(*) as count from board ";
		System.out.println("mKey: "+mKey);
		if( !mKey.equals("") || mKey!=null){ //검색어가 있으면
			SQL += "where title like '%" + mKey +"%' ";
			System.out.println("mKey: "+mKey);
		}
		try {
			getConnection();
			prepareStatement(SQL);
			executeQuery();
			rs.next();
			count = Integer.parseInt(rs.getString("count"));
			System.out.println("총 게시글 수: "+count);
		} catch (Exception e) {
			System.out.println("select count(*) 에러: "+e.getMessage());
			e.printStackTrace();
		}
		rsClose();
		pstateClose();
		
		SQL  = "select no, menu, status, title, photo, wdate from board ";
		if( !mKey.equals("")){ //검색어가 있으면
			SQL += "where title like '%" + mKey +"%' ";
		}
	   	SQL += "order by no desc limit " + startRow + "," + pageSize + "  ";
	   	System.out.println("10개씩 목록 select 구문  : "+SQL);
		try {
			prepareStatement(SQL);
			executeQuery();
			while(rs.next()) { 
				AnbdVO vo = new AnbdVO();
				vo.setNo(rs.getInt("no")); 
				vo.setMenu(rs.getString("menu")); 
				vo.setStatus(rs.getString("status")); 
				vo.setTitle(rs.getString("title"));
				vo.setPhoto(rs.getString("photo")); 
				vo.setWdate(rs.getString("wdate"));
				mainList.add(vo);
				
				
			}
		}catch (Exception e) {
			System.out.println("selMainList 에러: "+e.getMessage()); 
		}
		rsClose();
		pstateClose();
		conClose();
	}
	
	//글 목록만(검색어 적용 안한거)
	public void selMainList_v1( ArrayList<AnbdVO> mainList, int startRow, int pageSize ) {
		String SQL  = "select count(*) as count from board ";
		try {
			getConnection();
			prepareStatement(SQL);
			executeQuery();
			rs.next();
			count = Integer.parseInt(rs.getString("count"));
			System.out.println("총 게시글 수: "+count);
		} catch (Exception e) {
			System.out.println("select count(*) 에러: "+e.getMessage());
			e.printStackTrace();
		}
		rsClose();
		pstateClose();
		
		SQL  = "select no, menu, status, title, photo, wdate from board ";
	   	SQL += "order by no desc limit " + startRow + "," + pageSize + "  ";
	   	System.out.println("10개씩 목록 select 구문  : "+SQL);
		try {
			prepareStatement(SQL);
			executeQuery();
			while(rs.next()) { 
				AnbdVO vo = new AnbdVO();
				vo.setNo(rs.getInt("no")); 
				vo.setMenu(rs.getString("menu")); 
				vo.setStatus(rs.getString("status")); 
				vo.setTitle(rs.getString("title"));
				vo.setPhoto(rs.getString("photo")); 
				vo.setWdate(rs.getString("wdate"));
				mainList.add(vo);
			}
		}catch (Exception e) {
			System.out.println("selMainList 에러: "+e.getMessage()); 
		}
		rsClose();
		pstateClose();
		conClose();
	}


}
