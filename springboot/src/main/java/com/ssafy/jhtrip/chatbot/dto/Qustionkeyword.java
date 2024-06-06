package com.ssafy.jhtrip.chatbot.dto;

import lombok.Data;

import java.util.List;

@Data
public class Qustionkeyword {
    private int sidoCode;
    private List<Integer> contentIds;
}
