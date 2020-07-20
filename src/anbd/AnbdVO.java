package anbd;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.http.HttpServletResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AnbdVO {
	/* ******************************************************
	 * User
	 ****************************************************** */
	private int     userNo;      
	private String  id 	  = "";
	private String  pw 	  = "";
	private String  name  = "";
	private String  email = "";
	private int loginUserNo;
	
	/* ******************************************************
	 * Board
	 ****************************************************** */
	private   int    	 no;
	private   String 	 title 	 = "";
	private   String 	 content = "";
	private   String 	 photo 	 = "";
	private   String 	 wdate 	 = "";
	private   String 	 menu 	 = "";
	private   String 	 status	 = ""; 
	protected String	 key	 = "";
	
	/* ******************************************************
	 * File
	 ****************************************************** */
	private   int				fileNo;
	protected int 				size = 10 * 1024 * 1024;//10MB
	private   ArrayList<String> fileList 	   = null; 	//글보기 파일리스트
	private   ArrayList<String> modifyFileList = null;  //글수정 파일리스트
	protected ArrayList<String> SaveFileName   = null;  //글쓰기 파일리스트
	protected String 			fileName	   = "";
	protected String 			uploadPath 	   = "";
	protected String 			tagName		   = "";
	protected String 			saveName	   = "";
	
	/* ******************************************************
	 * Comment
	 ****************************************************** */
	private int 			  coNo;
	private int 			  cWriterNo;
	private String 			  cContent 	  = "";
	private String 			  cWdate 	  = "";
	private ArrayList<String> commentList = null;

	/* ******************************************************
	 * 그 외 변수
	 ****************************************************** */
	private   String strDate = "";
	protected String sigun   = "";
	protected String sido    = "";
	protected String juso    = "";
	protected int    jusoNo  = 0;
	
	/* ******************************************************
	 * 정규화 [시작]
	 * jsp에서 메소드를 활용하면 어떤 역할을 하는지 한 눈에 알아볼 수 있음
	 * protected로만 접근할 수 있던 변수를 get을 이용해 바꿔 쓸 수 있다
	 ****************************************************** */
	public AnbdVO() 
	{
		fileList = new <String>ArrayList();
		commentList = new <String>ArrayList(); 
		SaveFileName = new <String>ArrayList();   //글쓰기 파일리스트 생성
		modifyFileList = new <String>ArrayList(); //글수정 파일리스트 생성
		//날짜 생성
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy/MM/dd");
		strDate = sDate.format(Calendar.getInstance().getTime());
	}

	/* ******************************************************
	 * 글보기 파일명 리스트에 넣기/갯수/얻기
	 ****************************************************** */
	//첨부파일 이름, 첨부파일 이름을 리스트 길이만큼 가져옴
	public int GetFileSize() 
	{
		return SaveFileName.size();
	}
	
	//key, 키워드
	public String getKey() 
	{
		return tagName;
	}
	public String setKey(String key) 
	{
		return this.key = key;
	}
	
	//파일 이름
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}
	
	//Size, 파일 크기
	public int getSize() 
	{
		return size;
	}
	public void setSize(int size) 
	{
		this.size = size;
	}
	//배열에 넣기
	public void AddFile(String FileName)
	{
		fileList.add(FileName); 
	}
	//배열 갯수
	public int GetFileCount()
	{
		return fileList.size(); 
	}
	//jsp에서 for문으로 파일명 가져오기
	public String GetFile(int i)
	{
		return (String)fileList.get(i); 
	}
	public ArrayList GetFileList()
	{
		return fileList;
	}
	
	// 글보기 댓글 리스트에 넣기/갯수/얻기
	public void AddCo(String co)
	{
		commentList.add(co); 
	}
	public int GetCoCount()
	{
		return commentList.size(); 
	}
	public String GetCo(int i)
	{
		return (String)commentList.get(i);
	}
	public ArrayList<String> getCommentList() 
	{
		return commentList;
	}
	// 글쓰기 파일리스트
	public int getFileSize()
	{
		return SaveFileName.size();
	}
	//tagName - input file의 name값
	//정도희 사용 - jsp의 name 이름
	public String getTagName() 
	{ 
		return tagName;
	}
	public String setTagName(String tagName)
	{
		return this.tagName = tagName;
	}
	//saveName - 실제 저장된 파일명
	public String getSaveName(int i) 
	{ 
		return (String)SaveFileName.get(i); 
		//return saveName;
	}
	public String setSaveName(String saveName)
	{
		return this.saveName = saveName;
	}
	
	/* ******************************************************
	 * 글수정 파일리스트
	 ****************************************************** */
	public void addModifyFile(String modifyFile)
	{
		modifyFileList.add(modifyFile); 
	}
	public int getModifyFileCount()
	{
		return fileList.size(); 
	}
	public String getModifyFile(int i)
	{
		return (String)modifyFileList.get(i); //jsp에서 for문으로 파일명 가져오기
	}
	public ArrayList getModifyFileList()
	{
		return modifyFileList;
	}
	// User Get
	public int getUserNo()
	{
		return userNo;
	}
	public String getId() 
	{
		return id;
	}
	public String getPw()
	{
		return pw;
	}
	public String getName() 
	{
		return name;
	}
	public String getEmail() 
	{
		return email;
	}
	
	//글쓰기 - 실제 파일 위치
	public String getUploadPath() 
	{
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) 
	{
		this.uploadPath = uploadPath;
	}
	
	/* ******************************************************
	 * Board의 정보 [시작]
	 ****************************************************** */
	// Board Get
	public int getNo()
	{
		return no;
	}
	public int getLoginUserNo()
	{
		return loginUserNo;
	}
	public String getTitle() 
	{
		return title;
	}
	public String getContent() 
	{
		return content;
	}
	public String getPhoto() 
	{
		return photo;
	}
	public String getWdate() 
	{
		return wdate;
	}
	public String getMenu() 
	{
		return menu;
	}
	public String getStatus() 
	{
		return status;
	}
	
	// Board Set
	public void setNo(int no) 
	{
		this.no = no;
	}
	public void setLoginUserNo(int loginUserNo) 
	{
		this.loginUserNo = loginUserNo;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	public void setPhoto(String photo)
	{
		this.photo = photo;
	}
	public void setWdate(String wdate) 
	{
		this.wdate = wdate;
	}
	public void setMenu(String menu) 
	{
		this.menu = menu;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}
	/* ******************************************************
	 * Board의 정보 [끝]
	 ****************************************************** */
	// File Get
	public int getFileNo() 
	{
		return fileNo;
	}
	// Comment Get
	public int getCoNo() 
	{
		return coNo;
	}
	public int getCWriterNo() 
	{
		return cWriterNo;
	}
	public String getcContent() 
	{
		return cContent;
	}
	public String getcWdate() 
	{
		return strDate;
	}
	// User Set
	public void setUserNo(int userNo) 
	{
		this.userNo = userNo;
	}
	public void setId(String id) 
	{
		this.id = id;
	}
	public void setPw(String pw) 
	{
		this.pw = pw;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}
	// File Set
	public void setFileNo(int fileNo) 
	{
		this.fileNo = fileNo;
	}
	// Comment Set
	public void setCoNo(int coNo) 
	{
		this.coNo = coNo;
	}
	public void setCWriterNo(int cWriterNo) 
	{
		this.cWriterNo = cWriterNo;
	}
	public void setcContent(String cContent)
	{
		this.cContent = cContent;
	}
	public void setcWdate(String strDate)
	{
		this.cWdate = strDate;
		System.out.println("cWdate: "+cWdate);
	}

	//시/군/구 sigun
	public String getSigun() 
	{
		return sigun;
	}
	public void setSigun(String sigun) 
	{
		this.sigun = sigun;
	}

	//시/도 sido
	public String getSido() 
	{
		return sido;
	}
	public void setSido(String sido) 
	{
		this.sido = sido;
	}

	//주소 juso
	public String getJuso() 
	{
		return juso;
	}
	public void setJuso(String juso) 
	{
		this.juso = juso;
	}
	//주소 번호
	public int getJusoNo() {
		return jusoNo;
	}
	public void setJusoNo(int jusoNo) {
		this.jusoNo = jusoNo;
	}
}
