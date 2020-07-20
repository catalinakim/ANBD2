// https://developers.naver.com/docs/utils/captcha/examples/#java 여기 참고 함!! 
//capcha 키 발급 요청 하는 페이지 
package anbd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//네이버 캡차  API 발급 
public class capchaKey {

	public static void main(String[] args) {
		String clientId 	  = "e6ezWz2XAm1quQyj9gvq"; //애플리케이션 클라이언트 아이디
		String  clientSecret  =	"5c_rp0xBez";			//애플리케이션 클라이언트 시크릿

		String code = "0"; 	//키 발급시 0, 캡차 이미지 비교시 1로 세팅
		String apiURL = "https://openapi.naver.com/v1/capcha/nkey?code=" + code;
		
		Map<String, String> requestHeaders = new HashMap <> ();
		requestHeaders.put("X-Naver-Client-id" , clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		String responseBody = get(apiURL, requestHeaders);
		
		System.out.println(responseBody);
	}

	private static String get(String apiURL, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiURL);
		try {
			con.setRequestMethod("GET");
			for(Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}
			int responseCode = con.getResponseCode();
					if(responseCode == HttpURLConnection.HTTP_OK) {  //정상 호출
						return readBody(con.getInputStream());
					}else {   //에러 발생
						return readBody(con.getErrorStream());
					}
			}catch (IOException e) {
				throw new RuntimeException("API 요청과 응답 실패", e);
			}finally {
				con.disconnect();
			}
		}

	private static HttpURLConnection connect(String apiURL) {
		try {
            URL url = new URL(apiURL);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiURL, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiURL, e);
        }
    }

	private static String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);
		
		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();
			
			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}
		
				return responseBody.toString();
			}catch (IOException e) {
				throw new RuntimeException("API 응답을 읽는데 실패했습니다." , e);
			}
		}
	}	
	
	
	
	
	
	
	
	
	
	
	
	
