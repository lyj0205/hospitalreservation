package com.teamB.hospitalreservation.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamB.hospitalreservation.dto.HospitalResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HospitalApiClient {

    //https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList?serviceKey=pELcwY0hLrcCmA%2BqJUSOy0k2I8SnYZ4Mvp0V8fof5CdZTXTKV6ca1UGZMYgCclsTcWcvHfAZnxIIYfrsws0Byw==&pageNo=1&numOfRows=100&sidoCd=110000&sgguCd=110023&_type=json

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${api.key}")
    private String API_KEY;

    private static final String BASE_URL = "https://apis.data.go.kr/B551182/hospInfoServicev2";
    private static final String API_PATH = "/getHospBasisList";

    public HospitalApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<HospitalResponseDto> callApi(String sidoCd, String sgguCd, String subject) {
        try {
            String encodedApiKey = API_KEY.replace("+", "%2B");

            String url = String.format("%s%s?serviceKey=%s&pageNo=1&numOfRows=100&sidoCd=%s&sgguCd=%s&_type=json",
                    BASE_URL, API_PATH, encodedApiKey, sidoCd, sgguCd);

            if (subject != null && !subject.isBlank()) {
                url += "&yadmNm=" + URLEncoder.encode(subject, StandardCharsets.UTF_8);
            }

            log.info("Requesting hospital data from URL: {}", url);

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String jsonString = response.getBody();

            log.info("Received API response for {}/{}: {}", sidoCd, sgguCd, jsonString);


            if (jsonString != null && jsonString.trim().startsWith("<")) {
                handleXmlError(jsonString);
                return Collections.emptyList();
            }

            return parseJson(jsonString);

        } catch (Exception e) {
            log.error("API 호출 또는 파싱 중 오류 발생 (지역: {}-{}): {}", sidoCd, sgguCd, e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private void handleXmlError(String xmlResponse) {
        log.error("API가 XML 형식의 오류를 반환했습니다: {}", xmlResponse);
        if (xmlResponse.contains("SERVICE_KEY_IS_NOT_REGISTERED_ERROR")) {
            log.error("❌ API 키 오류: 서비스 키가 등록되지 않았거나, 인코딩이 잘못되었을 수 있습니다. 공공데이터포털의 IP 등록 정보를 확인하세요.");
        }
    }

    private List<HospitalResponseDto> parseJson(String jsonString) throws Exception {
        Map<String, Object> map = objectMapper.readValue(jsonString, new TypeReference<>() {});
        Map<String, Object> response = (Map<String, Object>) map.get("response");
        if (response == null) return Collections.emptyList();
        Map<String, Object> body = (Map<String, Object>) response.get("body");
        if (body == null) return Collections.emptyList();
        Object totalCountObj = body.get("totalCount");
        if (totalCountObj != null && Integer.parseInt(totalCountObj.toString()) == 0) {
            return Collections.emptyList();
        }
        Map<String, Object> itemsMap = (Map<String, Object>) body.get("items");
        if (itemsMap == null || itemsMap.get("item") == null) {
            return Collections.emptyList();
        }
        Object itemObject = itemsMap.get("item");
        List<Map<String, Object>> itemList;
        if (itemObject instanceof List) {
            itemList = (List<Map<String, Object>>) itemObject;
        } else if (itemObject instanceof Map) {
            itemList = Collections.singletonList((Map<String, Object>) itemObject);
        } else {
            return Collections.emptyList();
        }
        return itemList.stream()
                .map(item -> new HospitalResponseDto(
                        (String) item.get("yadmNm"),   // 병원명
                        (String) item.get("addr"),     // 주소
                        (String) item.get("telno"),    // 전화번호
                        (String) item.get("hpid"),     // API 고유 ID
                        (String) item.get("dgidIdName") // 진료과목명
                ))
                .collect(Collectors.toList());
    }
}