package anbd;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

public class WebUtil 
{
	protected HttpServletRequest mRequest = null;
	
	public void Init(HttpServletRequest request)
	{
		mRequest = request;
	}
	
	public String _S(String pParam,String pDefault)
	{
		String mValue = mRequest.getParameter(pParam);
		
		if(mValue == null)
		{
			mValue = pDefault;
		}
		return mValue;
	}
	
	public String _E(String pParam,String pDefault)
	{
		String mValue = _S(pParam,pDefault);
		
		//url에 검색어 한글을 %로 바꿔줌(인코딩)
		try
		{
			mValue = URLEncoder.encode(mValue, "UTF-8");
		}catch(Exception e)
		{
			
		}
		
		return mValue;
	}
	
	public int _I(String pParam,String pDefault)
	{
		String mValue = mRequest.getParameter(pParam);
		int    mInt = 0;
		
		if(mValue == null)
		{
			mValue = pDefault;
		}
		try
		{
			mInt = Integer.parseInt(mValue);
		}catch(Exception e)
		{
			mInt = 0;
		}
		return mInt;
	}	

}
