package com.kh.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class APISService {

	@Value("${api.base-url}")
	private String apiBaseUrl;
	
	@Value("${api.key}")
	private String apiKey;
	
	@Value("${api.content-type}")
	private String apiContentType;
	
	public String getAirAPIData() throws Exception {
		
		// 주소 값 설정
		String url = apiBaseUrl;
		url += "?serviceKey=" + URLEncoder.encode(apiKey, "UTF-8");
		url += "&sidoName=" + URLEncoder.encode("서울", "UTF-8");
		url += "&returnType=xml"; // service key 와 서울 지역 데이터를 가져올 때 xml 파일로 가져온다.
		// xml ◀ 예전에 주로 쓰임 / json ◀ 최근에 주로 쓰임
		
		System.out.println("url: " + url);
		
		// 위에서 설정된 주소를 가지고 데이터 가져오기
		URL requestURL = new URL(url);
		
		System.out.println("requestURL: " + requestURL);
		
		// requestURL 은 URL 주소 값 형식
		// HttpURLConnection ◀ Java 에서 특정 주소에 연결함과 동시에, HttpMethod 요청을 보낼 수 있다.
		// HttpMethod ◀ GET POST PUT DELETE
		HttpURLConnection huc = (HttpURLConnection) requestURL.openConnection();
		huc.setRequestMethod("GET");
		huc.setRequestProperty("Content-Type", apiContentType);
		
		System.out.println("huc: " + huc);
		
		// 남의 주소에서 남이 지정한 형식으로 가져와야 하기 때문에, 한줄씩 읽어서 모두 실시간으로 가져온 것
		BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
		StringBuilder response = new StringBuilder();
		String line;
		
		System.out.println("br: " + br);
		
		// 데이터를 한줄씩 가져오기
		while ((line = br.readLine()) != null) {
			response.append(line);
			
			// System.out.println("line: " + line);
			// System.out.println("response: " + response);
		}
		
		br.close(); // 데이터를 다 가져왔다면 닫기
		huc.disconnect(); // URL 연결 끊기
		
		// 가져온 데이터를 텍스트 값으로 보여주기 위해 전달하는 것
		return response.toString();
	}
}
