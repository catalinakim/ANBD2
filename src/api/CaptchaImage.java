package api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// 네이버 캡차 API 예제 - 캡차 이미지 수신
public class CaptchaImage {
	
	//kgh 추가 [시작]
	protected static String myPath = "D:\\WorkSpace\\anbd2\\WebContent\\img\\captchar"; //집에서는 이거만 됨
	//protected static String rootPath = System.getProperty("user.dir"); //현재 프로젝트 경로 - 집에서는 경로가 D:eclipse로 뜸..
	//protected static String myPath = rootPath + "\\WebContent\\img\\captchar";
	public static String imgFileName = "";
	//kgh 추가 [종료]
	
	//kgh 추가 [시작]
	public static void imageMain(String path) { //v2 for ajax - getCaptchar.jsp beans용
		String clientId = "zpOWGZC9IrUNCe67BAgt"; //애플리케이션 클라이언트 아이디값";
		String clientSecret = "Zt7IWpw7Gd"; //애플리케이션 클라이언트 시크릿값";
		//String key = "CAPTCHA_KEY"; 	    // https://openapi.naver.com/v1/captcha/nkey 호출로 받은 키값
		
		CaptchaKey getKey = new CaptchaKey();
		getKey.main();
		String key = getKey.key;
		System.out.println("CaptchaKey클래스에서 넘어온 key: "+key);
		
		String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;
		
		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		String responseBody = get(path, apiURL,requestHeaders);
		System.out.println(responseBody);
	}//kgh 추가 [종료]
	
    public static void main(String[] args) {
        String clientId = "zpOWGZC9IrUNCe67BAgt"; //애플리케이션 클라이언트 아이디값";
        String clientSecret = "Zt7IWpw7Gd"; //애플리케이션 클라이언트 시크릿값";

        //String key = "CAPTCHA_KEY"; // https://openapi.naver.com/v1/captcha/nkey 호출로 받은 키값
        
        //kgh 추가 [시작]
        CaptchaKey getKey = new CaptchaKey();
        getKey.main();
        String key = getKey.key;
        System.out.println("CaptchaKey클래스에서 넘어온 key: "+key);
        //kgh 추가 [종료]
        
        String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        //String responseBody = get(myPath, apiURL,requestHeaders);
        //System.out.println(responseBody);
    }

    private static String get(String path, String apiUrl, Map<String, String> requestHeaders){ //v2 ajax
    //private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return getImage(path, con.getInputStream());
            } else { // 에러 발생
                return error(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String getImage(String myPath, InputStream is){ //v2 for ajax
    //private static String getImage(InputStream is){
        int read;
        byte[] bytes = new byte[1024];
        // 랜덤한 이름으로  파일 생성
        String filename = Long.valueOf(new Date().getTime()).toString();
        //File f = new File(filename + ".jpg");
       
        //kgh 추가 [시작] - 경로 지정: img/captchar폴더
        File f = new File(myPath, filename + ".jpg");
        imgFileName = filename + ".jpg";
        //kgh 추가 [종료]
        
        
        try(OutputStream outputStream = new FileOutputStream(f)){
            f.createNewFile();
            while ((read = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            return "이미지 캡차가 생성되었습니다.";
        } catch (IOException e) {
            throw new RuntimeException("이미지 캡차 파일 생성에 실패 했습니다.",e);
        }
    }

    private static String error(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}