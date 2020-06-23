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
	private   ArrayList<String> fileList 	   = null; 	//�ۺ��� ���ϸ���Ʈ
	private   ArrayList<String> modifyFileList = null;  //�ۼ��� ���ϸ���Ʈ
	protected ArrayList<String> SaveFileName   = null;  //�۾��� ���ϸ���Ʈ
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
	 * �� �� ����
	 ****************************************************** */
	private String strDate = "";
	
	/* ******************************************************
	 * ����ȭ [����]
	 * jsp���� �޼ҵ带 Ȱ���ϸ� � ������ �ϴ��� �� ���� �˾ƺ� �� ����
	 * protected�θ� ������ �� �ִ� ������ get�� �̿��� �ٲ� �� �� �ִ�
	 ****************************************************** */
	public AnbdVO() 
	{
		fileList = new <String>ArrayList();
		commentList = new <String>ArrayList(); 
		SaveFileName = new <String>ArrayList();   //�۾��� ���ϸ���Ʈ ����
		modifyFileList = new <String>ArrayList(); //�ۼ��� ���ϸ���Ʈ ����
		//��¥ ����
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy/MM/dd");
		strDate = sDate.format(Calendar.getInstance().getTime());
	}

	/* ******************************************************
	 * �ۺ��� ���ϸ� ����Ʈ�� �ֱ�/����/���
	 ****************************************************** */
	//÷������ �̸�, ÷������ �̸��� ����Ʈ ���̸�ŭ ������
	public int GetFileSize() 
	{
		return SaveFileName.size();
	}
	
	//key, Ű����
	public String getKey() 
	{
		return tagName;
	}
	public String setKey(String key) 
	{
		return this.key = key;
	}
	
	//���� �̸�
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}
	
	//Size, ���� ũ��
	public int getSize() 
	{
		return size;
	}
	public void setSize(int size) 
	{
		this.size = size;
	}
	//�迭�� �ֱ�
	public void AddFile(String FileName)
	{
		fileList.add(FileName); 
	}
	//�迭 ����
	public int GetFileCount()
	{
		return fileList.size(); 
	}
	//jsp���� for������ ���ϸ� ��������
	public String GetFile(int i)
	{
		return (String)fileList.get(i); 
	}
	public ArrayList GetFileList()
	{
		return fileList;
	}
	
	// �ۺ��� ��� ����Ʈ�� �ֱ�/����/���
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
	// �۾��� ���ϸ���Ʈ
	public int getFileSize()
	{
		return SaveFileName.size();
	}
	//tagName - input file�� name��
	//������ ��� - jsp�� name �̸�
	public String getTagName() 
	{ 
		return tagName;
	}
	public String setTagName(String tagName)
	{
		return this.tagName = tagName;
	}
	//saveName - ���� ����� ���ϸ�
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
	 * �ۼ��� ���ϸ���Ʈ
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
		return (String)modifyFileList.get(i); //jsp���� for������ ���ϸ� ��������
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
	
	//�۾��� - ���� ���� ��ġ
	public String getUploadPath() 
	{
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) 
	{
		this.uploadPath = uploadPath;
	}
	
	/* ******************************************************
	 * Board�� ���� [����]
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
	 * Board�� ���� [��]
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
}
