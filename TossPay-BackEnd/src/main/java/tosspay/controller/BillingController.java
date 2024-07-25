package tosspay.controller;

import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/billing")
public class BillingController {

	// value 이용 apiSecretKey 가져오기
	@Value("${apiSecretKey}")
	private String apiSecretKey;

	// RestTemplate ◀ headers & body 최종 작성 공간 생성
	private final RestTemplate restTemplate = new RestTemplate(); 
	
	private final Map<String, String> billingMap = new ConcurrentHashMap<>();
	
	private String encodeSecretKey(String secretKey) {
		return "Basic" + new String(Base64.getEncoder().encode((secretKey + ":").getBytes()));
	}
	
	@PostMapping("/confirm-billing")
	public ResponseEntity<?> confirmBilling(@RequestBody Map<String, String> requestBody) {
		String billingKey = billingMap.get(requestBody.get("customerKey"));
		String url = "https://api.tosspayments.com/v1/billing/" + billingKey;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", encodeSecretKey(apiSecretKey));
		headers.set("Content-Type", "application/json");
		
		// key-value 타입의 Map 을 이용하여, String, String ◀ 모두 문자열로 가져오겠다.
		HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(requestBody, headers);
		
		ResponseEntity<Map> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
		
		// requestBody == 본문(사용자가 작성한 key 값이 들어있다.)
		// billingKey == 정기 결제에 관련된 key 값이 들어있다.
		billingMap.put(requestBody.get("customerKey"), res.getBody().get("billingKey").toString());
		
		return new ResponseEntity<>(res.getBody(), res.getStatusCode());
		
	}
}
