package com.ssafy.jhtrip.chatbot.service;

import com.ssafy.jhtrip.chatbot.dto.*;
import com.ssafy.jhtrip.chatbot.mapper.AttractionMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ssafy.jhtrip.config.ChatGptConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatGptService {

    private AttractionMapper attractionMapper;

    public ChatGptService(AttractionMapper attractionMapper) {
        this.attractionMapper = attractionMapper;
    }

    private static RestTemplate restTemplate = new RestTemplate();

    public HttpEntity<ChatGptRequestDto> buildHttpEntity(ChatGptRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
        return new HttpEntity<>(requestDto, headers);
    }

    public ChatGptResponseDto getResponse(HttpEntity<ChatGptRequestDto> chatGptRequestDtoHttpEntity) {
        ResponseEntity<ChatGptResponseDto> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.URL,
                chatGptRequestDtoHttpEntity,
                ChatGptResponseDto.class);

        return responseEntity.getBody();
    }

    public ChatGptResponseDto askQuestion(QuestionRequestDto requestDto) {
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequestDto(
                                ChatGptConfig.MODEL,
                                requestDto.getQuestion(),
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.TOP_P
                        )
                )
        );
    }

    @Transactional
    public ChatGptResponseDto getAttraction(Qustionkeyword qustionkeyword) {
        List<AttractionList> list = attractionMapper.getAttractionListBySido(qustionkeyword);
        Map<Integer, String> categoriesMap = new HashMap<>();

        categoriesMap.put(12, "관광지");
        categoriesMap.put(14, "문화시설");
        categoriesMap.put(15, "축제공연행사");
        categoriesMap.put(25, "여행코스");
        categoriesMap.put(28, "레포츠");
        categoriesMap.put(32, "숙박");
        categoriesMap.put(38, "쇼핑");
        categoriesMap.put(39, "음식점");

        String categories = "";
        for (int contentId : qustionkeyword.getContentIds()) {
            categories += categoriesMap.get(contentId) + ", ";
        }
        String city = attractionMapper.findBycode(qustionkeyword.getSidoCode())
                .orElseThrow(() -> new RuntimeException("Fail to find city"));
        String question = "다음은 "+ city +"의 관광지 리스트입니다. 리스트는 title(content_id) 형식입니다:\n";
        for (AttractionList attractionList : list) {
            question += "- " + attractionList.getTitle() + "(" + attractionList.getContentId() + ")\n";
        }
        question += "위 리스트에서 4곳을 무작위로 선택하여 JSON 형식으로 여행 코스를 추천해주세요. 마지막 요소는 content_id가 0이어야 하며, 전체 여행 코스 설명을 포함해야 합니다. 코스 설명은 JSON의 시작부터 순서대로 설명을 해야합니다. 또한 여행의 카테고리는 "+ categories + "이고 카테고리를 적절히 조합하여 최적의 여행코스를 설계해주세요. 예시 형식은 다음과 같습니다:\n"
                + "[\n" +
                "  { \"content_id\": 999999, \"title\": \"관광지\" },\n" +
                "  ...,\n" +
                "  { \"content_id\": 0, \"title\": \"부산 여행 코스로 먼저 해운대 해수욕장을 방문하여 여름에는 해변에서 즐거운 시간을 보내며, 백사장을 걸으며 해풍을 맞으며 여유를 즐길 수 있습니다. 이어서 태종대로 이동하여 멋진 해안 풍경을 감상하고 해가 질 때의 아름다운 야경을 즐길 수 있습니다. 부산 국립 해양박물관에서는 해양 문화와 자연에 대한 흥미로운 정보를 얻을 수 있으며, 마지막으로 범어사를 방문하여 조용한 분위기와 아름다운 사찰을 경험하며 명상과 여유를 즐길 수 있습니다.\" }\n" +
                "]";
//        System.out.println(question);
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequestDto(
                                ChatGptConfig.MODEL,
                                question,
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.TOP_P
                        )
                )
        );
    }
}
